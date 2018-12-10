package com.xcm.business;

import com.xcm.dao.OfflineMessageDao;
import com.xcm.socket.MessageCenter;
import com.xcm.socket.body.model.MessageAckModel;
import com.xcm.socket.body.model.PrivateMessage;
import com.xcm.socket.core.model.SocketRequest;
import com.xcm.socket.core.model.SocketResponse;
import com.xcm.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by 薛岑明 on 2017/4/3.
 */
@Service
public class OfflineMessageBusiness {
    @Autowired
    private OfflineMessageDao offlineMessageDao;
    @Autowired
    private MessageCenter messageCenter;

    /**
     * 拉取离线消息
     *
     * @param toUserId
     * @param fromUserId
     * @return
     */
    public void getOfflineMsgByUserId(long toUserId, long fromUserId) {
        Set<String> set = offlineMessageDao.getOfflineMessageByUserId(toUserId, fromUserId);
        List<SocketResponse> list = new ArrayList<>();
        for (String str : set) {
            SocketResponse response = JsonUtil.StringToObject(str, SocketResponse.class);
            list.add(response);
        }
        for (SocketResponse response : list) {
            messageCenter.sendMessage(toUserId, response);
        }
    }

    public void delOfflineMsg(SocketRequest socketRequest) {
        MessageAckModel messageAckModel = JsonUtil.linkedHashMapToObject
                ((LinkedHashMap<Object, Object>) socketRequest.getBody(), MessageAckModel.class);
        String messageId = messageAckModel.getMessageId();
        long fromUserId = messageAckModel.getFromUserId();
        long toUserId = socketRequest.getUserId();
        offlineMessageDao.delOfflineMsgBymessageIdAndUserId(fromUserId, toUserId, messageId);
    }
}
