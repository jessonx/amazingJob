package com.xcm.business;

import com.xcm.socket.core.model.SocketRequest;
import com.xcm.util.SessionMap;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.lang.ref.WeakReference;

/**
 * Created by 薛岑明 on 2017/3/20.
 */
@Service
public class LoginBusiness {

    public void login(long userId, WebSocketSession session) {
        session.getHandshakeAttributes().put("userId", userId);
        SessionMap.sessionMap.put(userId, new WeakReference<>(session));
    }
}
