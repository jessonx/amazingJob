package com.xcm.webSocket;

import com.xcm.business.PrivateMessageBusiness;
import com.xcm.model.anotation.CommandId;
import com.xcm.socket.body.model.PrivateMessage;
import com.xcm.socket.core.model.RequestHeader;
import com.xcm.socket.core.model.ResponseHeader;
import com.xcm.socket.core.model.SocketRequest;
import com.xcm.socket.core.model.SocketResponse;
import com.xcm.webSocket.commad.OfflineMsgType;
import com.xcm.webSocket.commad.SocketId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(SocketId.PRIVATE_MESSAGE_SID + "")
public class PrivateMessageService {
    @Autowired
    private PrivateMessageBusiness privateMessageBusiness;

    @CommandId(SocketId.PRIVATE_MESSAGE_SEND_CID)
    public SocketResponse sendPrivateMessage(SocketRequest socketRequest) {
        long userId = socketRequest.getUserId();
        if (userId > 0) {
            privateMessageBusiness.sendPrivateMessage(socketRequest);
        }
        return new SocketResponse();
    }

}
