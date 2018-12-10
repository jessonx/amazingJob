package com.xcm.util;

import org.springframework.web.socket.WebSocketSession;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SessionMap {
    public static Map<Long, WeakReference<WebSocketSession>> sessionMap
            = new ConcurrentHashMap<>();

    public static WebSocketSession getWebSocketSession(Long userId) {
        WebSocketSession webSocketSession = null;
        WeakReference<WebSocketSession> webSocketSessionWeakReference = sessionMap.get(userId);
        if (webSocketSessionWeakReference != null) {
            webSocketSession = webSocketSessionWeakReference.get();
            if (null == webSocketSession) {
                sessionMap.remove(userId);
            }
        }
        return webSocketSession;
    }
}
