package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/4/12.
 */
public class RecentContactsRes {
    private long userId;
    private String description;
    private String nickName;
    private String headerThumb;
    private int gender;
    private long offset;
    private long messageCount;

    public RecentContactsRes(long userId, String description, String nickName,
                             String headerThumb, int gender, long offset) {
        this.userId = userId;
        this.description = description;
        this.nickName = nickName;
        this.headerThumb = headerThumb;
        this.gender = gender;
        this.offset = offset;
    }

    public RecentContactsRes() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeaderThumb() {
        return headerThumb;
    }

    public void setHeaderThumb(String headerThumb) {
        this.headerThumb = headerThumb;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(long messageCount) {
        this.messageCount = messageCount;
    }
}
