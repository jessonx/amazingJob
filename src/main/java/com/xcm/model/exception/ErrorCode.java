package com.xcm.model.exception;

public class ErrorCode {


    // 成功.
    public static final int SUCCESS = 0;

    // 全局错误代码
    public static final int SYSTEM_ERROR = 10000;// 系统错误
    public static final int PARAMETER_ERROR = 11000;// 请求参数错误
    public static final int NO_PERMISSION = 12000;// 权限错误
    public static final int INCOMPLETE_INFO = 13000;//请填写完整的信息


    //用户模块
    public static final int USER_IS_NOT_EXIST = 20001;//用户不存在
    public static final int PASSWORD_CAN_NOT_BE_NULL = 20002;//密码不可为空
    public static final int PASSWORD_ERROR = 20003;//密码错误
    public static final int EMAIL_HAS_BEEN_USED = 20004;//该邮箱已经被注册
    public static final int NOT_LOGIN = 20005;//没有登录
    public static final int FILE_CANNOT_BE_NULL = 20006;//文件不能为空
    public static final int FILE_NAME_CANNOT_BE_NULL = 20007;//文件名不能为空
    public static final int EMAIL_FORM_ERROR = 20008;//邮箱格式不正确
    public static final int LABEL_NAME_REPEATED = 20009;//请勿使用重复标签
    public static final int REPEATED_USER_PRAISE = 20010;//请勿重复点赞
    public static final int NO_RESUME = 20011;//请先在设置中完善您的简历

    //企业模块
    public static final int ENTERPRISE_IS_NOT_EXIST = 30001;//企业信息不存在
    public static final int TEL_NUM_FORM_ERROR = 30002;//电话格式不正确
    public static final int REPEATED_ENTERPRISE_FOCUS = 30003;//请勿重复关注
    public static final int USER_JOB_POST_RECORD_IS_NOT_EXIT = 30004;//该条记录不存在
    public static final int SALARY_FORMAT_ERROR = 30005;//请输入正确的薪资格式


    //好友模块
    public static final int REPEATED_FRIEND_REQUEST = 40001;//请勿重复发送好友请求
    public static final int FRIEND_REQUEST_NOT_EXIST = 40002;//您已处理该好友请求
    public static final int CAN_NOT_ADD_FRIEND_SELF = 40003;//不能添加自己为好友

    //职业模块
    public static final int REPEATED_JOB_FOCUS = 50001;//请勿重复关注
    public static final int REPEATED_JOB_POST = 50002;//请勿重复投递
    public static final int JOB_IS_NOT_EXIST = 50003;//职位不存在
    public static final int ENTERPRISE_USER_CAN_NOT_POST = 50004;//企业用户不可投递
}
