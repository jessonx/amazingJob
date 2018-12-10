package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/3/16.
 */
public class FocusRes {
    private long recentContactsCount;
    private long publishJobsCount;
    private long receiveResumesCount;
    private long userId;
    private int userType;
    private long focusJobsCount;
    private long postJobsCount;
    private long focusEnterPrisesCount;
    private long friendsCount;

    public long getRecentContactsCount() {
        return recentContactsCount;
    }

    public void setRecentContactsCount(long recentContactsCount) {
        this.recentContactsCount = recentContactsCount;
    }

    public long getPublishJobsCount() {
        return publishJobsCount;
    }

    public void setPublishJobsCount(long publishJobsCount) {
        this.publishJobsCount = publishJobsCount;
    }

    public long getReceiveResumesCount() {
        return receiveResumesCount;
    }

    public void setReceiveResumesCount(long receiveResumesCount) {
        this.receiveResumesCount = receiveResumesCount;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public long getUserId() {
        return userId;
    }

    public FocusRes() {
    }

    public FocusRes(long recentContactsCount, long publishJobsCount, long receiveResumesCount, long userId, int userType, long focusJobsCount, long postJobsCount, long focusEnterPrisesCount, long friendsCount) {
        this.recentContactsCount = recentContactsCount;
        this.publishJobsCount = publishJobsCount;
        this.receiveResumesCount = receiveResumesCount;
        this.userId = userId;
        this.userType = userType;
        this.focusJobsCount = focusJobsCount;
        this.postJobsCount = postJobsCount;
        this.focusEnterPrisesCount = focusEnterPrisesCount;
        this.friendsCount = friendsCount;
    }

    public long getFocusJobsCount() {
        return focusJobsCount;
    }

    public void setFocusJobsCount(long focusJobsCount) {
        this.focusJobsCount = focusJobsCount;
    }

    public long getPostJobsCount() {
        return postJobsCount;
    }

    public void setPostJobsCount(long postJobsCount) {
        this.postJobsCount = postJobsCount;
    }

    public long getFocusEnterPrisesCount() {
        return focusEnterPrisesCount;
    }

    public void setFocusEnterPrisesCount(long focusEnterPrisesCount) {
        this.focusEnterPrisesCount = focusEnterPrisesCount;
    }

    public long getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(long friendsCount) {
        this.friendsCount = friendsCount;
    }
}
