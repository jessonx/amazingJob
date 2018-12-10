package com.xcm.util;

import com.xcm.dao.hibernate.model.Users;
import com.xcm.model.resource.UserBasicRes;

/**
 * Created by 薛岑明 on 2017/2/15.
 */
public class ResourceUtil {
    public static UserBasicRes changeUserToUserBasicRes(Users users) {
        UserBasicRes res = new UserBasicRes();
        if (users != null) {
            res.setNickName(users.getNickName());
            res.setHeaderThumb(users.getHeaderThumb());
            res.setUserId(users.getUserId());
            res.setUserType(users.getUserType());
            res.setMessageCount(0);
            res.setNotificationCount(1);
        }
        return res;
    }
}
