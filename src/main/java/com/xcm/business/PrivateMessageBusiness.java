package com.xcm.business;

import com.xcm.dao.FriendDao;
import com.xcm.dao.PrivateMessageDao;

import com.xcm.dao.hibernate.model.PrivateMessageRecord;
import com.xcm.socket.MessageCenter;
import com.xcm.socket.body.model.PrivateMessage;
import com.xcm.socket.core.model.SocketRequest;
import com.xcm.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Created by 薛岑明 on 2017/3/20.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class PrivateMessageBusiness {
    @Autowired
    private PrivateMessageDao privateMessageDao;
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private MessageCenter messageCenter;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void sendPrivateMessage(SocketRequest socketRequest) {
        PrivateMessage privateMessage = JsonUtil.linkedHashMapToObject
                ((LinkedHashMap<Object, Object>) socketRequest.getBody(), PrivateMessage.class);
        long fromUserId = privateMessage.getFromUserId();
        long toUserId = privateMessage.getToUserId();
//        UUID uuid = UUID.randomUUID();
//        //唯一messageID
//        String messageId = uuid.toString();
        String messageId = String.valueOf(System.currentTimeMillis());
        Date date = new Date();
        PrivateMessageRecord privateMessageRecord = new PrivateMessageRecord(privateMessage.getFromUserId(),
                privateMessage.getToUserId(), messageId, date, privateMessage.getContent());
        privateMessageDao.savePrivateMessageRecord(privateMessageRecord);
        socketRequest.setBody(privateMessage);
        if (fromUserId != toUserId) {
            friendDao.addRecentContact(fromUserId, toUserId);
            //发送信息
            messageCenter.sendPrivateMessage(socketRequest, messageId);
        }
    }
}
