package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/3/18.
 */
public class FriendRequestRes {
    private long friendRequestId;
    private long userId;
    private String time;
    private String headerThumb;
    private String nickName;
    private long offset;

    public FriendRequestRes(long friendRequestId, long userId, String time, String headerThumb, String nickName, long offset) {
        this.friendRequestId = friendRequestId;
        this.headerThumb = headerThumb;
        this.nickName = nickName;
        this.offset = offset;
        this.time = time;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(long friendRequestId) {
        this.friendRequestId = friendRequestId;
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

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
