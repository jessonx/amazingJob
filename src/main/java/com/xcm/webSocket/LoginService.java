package com.xcm.webSocket;

import com.xcm.business.LoginBusiness;
import com.xcm.model.anotation.CommandId;
import com.xcm.socket.core.model.SocketRequest;
import com.xcm.socket.core.model.SocketResponse;
import com.xcm.webSocket.commad.SocketId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;


@Service(SocketId.LOGIN_SID + "")
public class LoginService {
    @Autowired
    private LoginBusiness loginBusiness;

    @CommandId(SocketId.LOGIN_CID)
    public SocketResponse Login(SocketRequest socketRequest, WebSocketSession session) {
        long userId = socketRequest.getUserId();
        if (userId > 0) {
            loginBusiness.login(userId, session);
        }
        return new SocketResponse();
    }
}
