package com.xcm.model.key;

/**
 * Created by 薛岑明 on 2017/4/3.
 */
public class OfflineMessageRedisKey {
    /**
     * 离线消息存放含有离线消息的人
     */
    public static final String OFFLINE_MESSAGE = "off:m:%s";


    /**
     * 离线消息
     */
    public static final String OFFLINE_MESSAGE_FROM_TO = "off:m:f:%s:t:%s";
}
