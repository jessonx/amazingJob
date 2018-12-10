package com.xcm.socket;

import com.xcm.model.anotation.CommandId;
import com.xcm.socket.body.model.PrivateMessage;
import com.xcm.socket.core.model.RequestHeader;
import com.xcm.socket.core.model.SocketRequest;
import com.xcm.socket.core.model.SocketResponse;
import com.xcm.util.JsonUtil;
import com.xcm.util.LoggerManage;
import com.xcm.util.SessionMap;
import com.xcm.webSocket.commad.SocketId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.lang.reflect.Method;

@Repository
public class WebSocketHandler extends TextWebSocketHandler implements ApplicationContextAware {
    Logger logger = LoggerManage.getLogger();

    private ApplicationContext applicationContext;

    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {
        String string = message.getPayload();

        SocketRequest request = JsonUtil.StringToObject(string, SocketRequest.class);
        RequestHeader requestHeader = request.getRequestHeader();
        int sid = requestHeader.getSid();
        int cid = requestHeader.getCid();
        long userId = request.getUserId();

        if (cid == 1000 && sid == 1000) {
            //心跳包
//            logger.info("heart package from user" + userId);

            return;
        }
        //打印日志
        logger.info("request from user :" + userId + "\n" + JsonUtil.ObjectToJson(requestHeader));
        Object proxy = applicationContext.getBean(String.valueOf(sid));
        Object object = proxy;
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            try {
                object = ((Advised) object).getTargetSource().getTarget();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        //得到Class
        Class cl = object.getClass();
        //反射得到方法
        Method[] methods = cl.getMethods();
        for (Method method : methods) {
            //反射得到cid注解
            CommandId commandId = method.getAnnotation(CommandId.class);
            if (commandId != null) {
                if (cid == commandId.value()) {
                    SocketResponse response = null;
                    //登录
                    if (sid == SocketId.LOGIN_SID && cid == SocketId.LOGIN_CID) {
                        response = (SocketResponse) method.invoke(object, request, session);
                    } else {
                        response = (SocketResponse) method.invoke(object, request);
                    }
                    String strObj = JsonUtil.ObjectToJson(response);
                    TextMessage returnMsg = new TextMessage(strObj);
                    synchronized (session) {
                        session.sendMessage(returnMsg);
                    }
                }
            }
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        logger.info("handleBinaryMessage");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (session.getHandshakeHeaders().containsKey("userId")) {
            long userId = (Long) session.getHandshakeAttributes().get("userId");
            SessionMap.sessionMap.remove(userId);
            logger.info("afterConnectionClosed ---用户：" + userId + "连接中断");
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
