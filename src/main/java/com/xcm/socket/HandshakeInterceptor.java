package com.xcm.socket;

import com.xcm.util.LoggerManage;
import org.slf4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;


public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    private Logger logger = LoggerManage.getLogger();

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
//        logger.info("-------------Before Handshake----------");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
//        logger.info("-------------After Handshake----------");
        super.afterHandshake(request, response, wsHandler, ex);
    }

}
