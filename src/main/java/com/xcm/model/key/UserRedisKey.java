package com.xcm.model.key;

/**
 * Created by 薛岑明 on 2017/2/15.
 */
public class UserRedisKey {
    /**
     * 用户标签数目
     */
    public final static String USER_LABEL_COUNT = "u:%s:lc";

    /**
     * 用户收到通知数量
     */
    public final static String USER_NOTIFICATION_COUNT = "u:%s:nc";



    /**
     * 用户关注的职位数量
     */
    public final static String USER_FOCUS_JOBS = "u:%s:fj";


    /**
     * 用户关注的企业
     */
    public final static String USER_FOCUS_ENTERPRISES = "u:%s:fe";

    /**
     * 企业被关注的用户
     */
    public final static String ENTERPRISES_BE_FOCUSED = "e:%s:bf";
    /**
     * 用户被点赞数
     */
    public final static String USER_BE_PRAISED = "u:%s:bp";


}
