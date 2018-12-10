package com.xcm.webSocket;

import com.xcm.business.OfflineMessageBusiness;
import com.xcm.model.anotation.CommandId;
import com.xcm.socket.body.model.PrivateMessage;
import com.xcm.socket.core.model.SocketRequest;
import com.xcm.socket.core.model.SocketResponse;
import com.xcm.webSocket.commad.SocketId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static com.xcm.webSocket.commad.SocketId.OFFLINE_MESSAGE_ACK;
import static com.xcm.webSocket.commad.SocketId.OFFLINE_MESSAGE_GET_CID;
import static com.xcm.webSocket.commad.SocketId.OFFLINE_MESSAGE_SID;


@Service(OFFLINE_MESSAGE_SID + "")
public class OfflineMessageService {
    @Autowired
    private OfflineMessageBusiness offlineMessageBusiness;

    @CommandId(OFFLINE_MESSAGE_GET_CID)
    public SocketResponse getOfflineMsg(SocketRequest socketRequest) {
        long toUserId = socketRequest.getUserId();
        long fromUserId = Long.parseLong((String) socketRequest.getBody());
        if (toUserId > 0) {
            offlineMessageBusiness.getOfflineMsgByUserId(toUserId, fromUserId);
        }
        return new SocketResponse();
    }

    @CommandId(OFFLINE_MESSAGE_ACK)
    public SocketResponse delOfflineMsg(SocketRequest socketRequest) {
        long toUserId = socketRequest.getUserId();
        if (toUserId > 0) {
            offlineMessageBusiness.delOfflineMsg(socketRequest);
        }
        return new SocketResponse();
    }
}
