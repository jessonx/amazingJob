package com.xcm.model.key;

/**
 * Created by 薛岑明 on 2017/3/17.
 */
public class FriendRedisKey {
    /**
     * 最近访客
     */
    public static final String USER_RECENT_VISITOR = "f:u:%s:rv";
    /**
     * 好友
     */
    public static final String USER_FRIEND = "f:u:%s:f";
    /**
     * 最近联系人
     */
    public static final String USER_RECENT_CONTACT="f:u:%s:rc";
}
