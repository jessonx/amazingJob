package com.xcm.business;

import com.xcm.dao.UserDao;
import com.xcm.dao.hibernate.model.UserAuthorization;
import com.xcm.dao.hibernate.model.UserLoginHistory;
import com.xcm.dao.hibernate.model.Users;
import com.xcm.model.Enum.GenderEnum;
import com.xcm.model.Enum.TokenTypeEnum;
import com.xcm.model.Enum.UserStatusEnum;
import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.UserBasicRes;
import com.xcm.util.ResourceUtil;
import com.xcm.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Locale;

/**
 * Created by 薛岑明 on 2017/2/15.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class SecurityBusiness {
    @Autowired
    private UserDao userDao;

    /**
     * 登陆
     *
     * @param token
     * @param password
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public UserBasicRes login(String token, String password, int tokenType,
                              String deviceId, String ip, Locale locale) {
        //核对密码
        UserAuthorization userAuthorization =
                userDao.getUserAuthorizationByTokenAndTokenType(token, tokenType);
        if (userAuthorization == null) {
            throw new BusinessException(ErrorCode.USER_IS_NOT_EXIST, locale);
        }
        if (StringUtil.isNullOrEmpty(password)) {
            throw new BusinessException(ErrorCode.PASSWORD_CAN_NOT_BE_NULL, locale);
        }
        String userPsd = userAuthorization.getUserIdentity();
        if (!password.equals(userPsd)) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR, locale);
        }
        //保存用户登陆记录
        long userId = userAuthorization.getUserId();
        UserLoginHistory userLoginHistory = userDao.getUserLoginHistoryByUserId(userId);
        if (userLoginHistory != null) {
            userLoginHistory.setDeviceId(deviceId);
            userLoginHistory.setIp(ip);
            userLoginHistory.setCreatedOn(new Date());
        } else {
            userLoginHistory = new UserLoginHistory(userId, 1, "", "", "", ip, "", new Date());
        }
        userDao.saveUserLoginHistory(userLoginHistory);
        //得到用户类型（企业或个人）
        Users user = userDao.getUserById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_IS_NOT_EXIST, locale);
        }
        return ResourceUtil.changeUserToUserBasicRes(user);
    }

    /**
     * @param nickName
     * @param token     最先只有email
     * @param password  前端md5加密
     * @param userType  用户类型（企业或者个人）
     * @param tokenType 账号类型
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public long register(String nickName, String token, String password,
                         int userType, String ip, Locale locale, int tokenType) {
        if (tokenType == TokenTypeEnum.MAIL.value()) {
            //如果是邮箱检测邮箱的格式
            if (!StringUtil.isMailStr(token)) {
                throw new BusinessException(ErrorCode.EMAIL_FORM_ERROR, locale);
            }
        }
        Date date = new Date();
        UserAuthorization userAuthorization =
                userDao.getUserAuthorizationByTokenAndTokenType(token, TokenTypeEnum.MAIL.value());
        if (userAuthorization != null) {
            throw new BusinessException(ErrorCode.EMAIL_HAS_BEEN_USED, locale);
        }
        Users users = new Users(nickName, GenderEnum.MALE.value()
                , "这个人很懒,什么也没留下", "", "", "http://onxm1cexj.bkt.clouddn.com/default.png?imageView2/5/w/200/h/200/format/jpg/q/75|imageslim",
                UserStatusEnum.NORMAL.value(), false, userType, date, 0, date);
        userDao.saveUser(users);
        long userId = users.getUserId();
        userAuthorization = new UserAuthorization(userId, password, TokenTypeEnum.MAIL.
                value(), token, userId, date);
        userDao.saveUserAuthorization(userAuthorization);

        UserLoginHistory userLoginHistory =
                new UserLoginHistory(userId, 1, "1.0", "web", "web", ip, "1.0测试版本", date);
        userDao.saveUserLoginHistory(userLoginHistory);
        return userId;
    }

    /**
     * 解锁
     *
     * @param userId
     * @param password
     * @param tokenType
     * @param locale
     */
    public void unlock(long userId, String password, int tokenType, Locale locale) {
        UserAuthorization userAuthorization = userDao.getUserAuthorizationByUserId(userId);
        if (userAuthorization == null) {
            throw new BusinessException(ErrorCode.USER_IS_NOT_EXIST, locale);
        }
        if (!userAuthorization.getUserIdentity().equals(password)) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR, locale);
        }
    }
}
