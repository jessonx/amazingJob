package com.xcm.webSocket.commad;

/**
 * Created by 薛岑明 on 2017/3/20.
 */
public class SocketId {
    //登录
    public static final int LOGIN_SID = 100;
    public static final int LOGIN_CID = 101;

    //私信
    public static final int PRIVATE_MESSAGE_SID = 200;
    public static final int PRIVATE_MESSAGE_SEND_CID = 101;
    public static final int PRIVATE_MESSAGE_RECEIVE_CID = 102;

    //离线消息模块
    public static final int OFFLINE_MESSAGE_SID = 300;
    public static final int OFFLINE_MESSAGE_GET_CID = 100;//拉取
    public static final int OFFLINE_MESSAGE_ACK = 200;//删除

    //通知消息
    public static final int NOTIFICATION_SID = 400;
    public static final int NOTIFICATION_SEND_CID = 100;
}
