package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/1/5.
 */
public class UserBasicRes {
    private long userId;
    private String headerThumb;
    private String nickName;
    private int userType;
    private long notificationCount;
    private long messageCount;
    private boolean profileInfoVerified;
    private boolean enterpriseInfoVerified;

    public UserBasicRes() {
    }

    public UserBasicRes(long userId, String headerThumb, String nickName, int userType,
                        int notificationCount, int messageCount,
                        boolean profileInfoVerified, boolean enterpriseInfoVerified) {
        this.userId = userId;
        this.headerThumb = headerThumb;
        this.nickName = nickName;
        this.userType = userType;
        this.notificationCount = notificationCount;
        this.messageCount = messageCount;
        this.profileInfoVerified = profileInfoVerified;
        this.enterpriseInfoVerified = enterpriseInfoVerified;
    }

    public boolean isEnterpriseInfoVerified() {
        return enterpriseInfoVerified;
    }

    public boolean isProfileInfoVerified() {
        return profileInfoVerified;
    }

    public void setEnterpriseInfoVerified(boolean enterpriseInfoVerified) {
        this.enterpriseInfoVerified = enterpriseInfoVerified;
    }

    public void setProfileInfoVerified(boolean profileInfoVerified) {
        this.profileInfoVerified = profileInfoVerified;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHeaderThumb() {
        return headerThumb;
    }

    public void setHeaderThumb(String headerThumb) {
        this.headerThumb = headerThumb;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public long getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(long notificationCount) {
        this.notificationCount = notificationCount;
    }

    public long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(long messageCount) {
        this.messageCount = messageCount;
    }
}
