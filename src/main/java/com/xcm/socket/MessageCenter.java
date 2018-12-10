package com.xcm.socket;

import com.xcm.dao.OfflineMessageDao;
import com.xcm.dao.UserDao;
import com.xcm.socket.body.model.NotificationModel;
import com.xcm.socket.body.model.PrivateMessage;
import com.xcm.socket.core.model.ResponseHeader;
import com.xcm.socket.core.model.SocketRequest;
import com.xcm.socket.core.model.SocketResponse;
import com.xcm.util.CommonUtil;
import com.xcm.util.ExecutorServiceUtil;
import com.xcm.util.JsonUtil;
import com.xcm.util.LoggerManage;
import com.xcm.util.SessionMap;
import com.xcm.util.StringUtil;
import com.xcm.webSocket.commad.OfflineMsgType;
import com.xcm.webSocket.commad.SocketId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 薛岑明 on 2017/3/20.
 */
@Repository
public class MessageCenter {
    private Logger logger = LoggerManage.getLogger();
    @Autowired
    private OfflineMessageDao offlineMessageDao;
    @Autowired
    private UserDao userDao;

    /**
     * 发送消息
     *
     * @param userId
     * @param notificationModel
     */
    public void senNotification(long userId, NotificationModel notificationModel) {

        //保存通知
        userDao.saveNotificationByUserId(userId, notificationModel);
        sendMessage(userId, 0, SocketId.NOTIFICATION_SID,
                SocketId.NOTIFICATION_SEND_CID, OfflineMsgType.NOTIFICATION_MESSAGE, notificationModel, "");
    }

    /**
     * 发送私信
     *
     * @param socketRequest
     * @param messageId
     */
    public void sendPrivateMessage(SocketRequest<PrivateMessage> socketRequest, String messageId) {
        PrivateMessage privateMessage = socketRequest.getBody();
        Date date = new Date();
        privateMessage.setOffset(date.getTime());
        privateMessage.setTime(CommonUtil.getDateStrByTime(date.getTime()));
        long toUserId = privateMessage.getToUserId();
        long fromUserId = privateMessage.getFromUserId();
        int offlineMsgType = OfflineMsgType.PRIVATE_MESSAGE;
        sendMessage(fromUserId, fromUserId, SocketId.PRIVATE_MESSAGE_SID,
                SocketId.PRIVATE_MESSAGE_RECEIVE_CID,
                offlineMsgType, privateMessage, messageId);
        sendMessage(toUserId, fromUserId, SocketId.PRIVATE_MESSAGE_SID,
                SocketId.PRIVATE_MESSAGE_RECEIVE_CID,
                offlineMsgType, privateMessage, messageId);

    }

    public <T> void sendMessage(long toUserId, long fromUserId, int sid,
                                int cid, int offlineMsgType, T body, String messageId) {
        long cseqNo = 0;
        long sseqNo = 0;
        int code = 0;
        //唯一messageID
        if (StringUtil.isNullOrEmpty(messageId)) {
            UUID uuid = UUID.randomUUID();
            messageId = uuid.toString();
        }
        ResponseHeader header = new ResponseHeader(sid, cid, cseqNo,
                sseqNo, new Date().getTime(), code, offlineMsgType, messageId);
        SocketResponse<T> response = new SocketResponse<>(header, body);
        //根据消息类型保存
        if (fromUserId != toUserId) {
            if (offlineMsgType == OfflineMsgType.PRIVATE_MESSAGE) {
                offlineMessageDao.saveOffLineMessage(fromUserId, toUserId,
                        (SocketResponse<PrivateMessage>) response,
                        messageId);
            }
        }
        sendMessage(toUserId, response);
    }


    /**
     * 发送私信
     *
     * @param toUserId
     * @param response
     * @param <T>
     */
    public <T> void sendMessage(long toUserId, SocketResponse<T> response) {
        WebSocketSession session = SessionMap.getWebSocketSession(toUserId);

        if (session != null) {
            ExecutorService service = ExecutorServiceUtil.
                    getExecutorServiceByThreadCount(ExecutorServiceUtil.DEFAULT);
            //异步发送消息
            service.execute(() -> {
                //多线程发消息要加锁
                synchronized (session) {
                    String strObj = JsonUtil.ObjectToJson(response);
                    if (strObj == null) {
                        logger.error("message is null");
                    } else {
                        TextMessage returnMsg = new TextMessage(strObj);
                        try {
                            session.sendMessage(returnMsg);
                        } catch (IOException e) {
                            logger.error("send message error " + e.getMessage());

                        }

                    }
                }
            });
        } else {
            logger.info("WebSocketSession is null");
        }
    }

}
