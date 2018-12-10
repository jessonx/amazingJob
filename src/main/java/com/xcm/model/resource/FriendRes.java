package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/3/18.
 */
public class FriendRes {
    private long userId;
    private String description;
    private String nickName;
    private String headerThumb;
    private int gender;
    private long offset;

    public FriendRes() {
    }

    public FriendRes(long userId, String description,
                     String nickName, String headerThumb, int gender, long offset) {
        this.userId = userId;
        this.description = description;
        this.nickName = nickName;
        this.headerThumb = headerThumb;
        this.gender = gender;
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
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
}

