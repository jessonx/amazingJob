package com.xcm.socket.body.model;

/**
 * Created by 薛岑明 on 2017/3/20.
 */
public class PrivateMessage {
    private long fromUserId;
    private String fromUserHeaderThumb;
    private String fromUserNickName;
    private long toUserId;
    private String content;
    private int msgType;
    private String time;
    private long offset;//时间

    public PrivateMessage() {
    }

    public PrivateMessage(long fromUserId, String fromUserHeaderThumb, String fromUserNickName,
                          long toUserId, String content, int msgType, String time, long offset) {
        this.fromUserId = fromUserId;
        this.fromUserHeaderThumb = fromUserHeaderThumb;
        this.fromUserNickName = fromUserNickName;
        this.toUserId = toUserId;
        this.content = content;
        this.msgType = msgType;
        this.time = time;
        this.offset = offset;
    }

    public String getFromUserHeaderThumb() {
        return fromUserHeaderThumb;
    }

    public void setFromUserHeaderThumb(String fromUserHeaderThumb) {
        this.fromUserHeaderThumb = fromUserHeaderThumb;
    }

    public void setFromUserNickName(String fromUserNickName) {
        this.fromUserNickName = fromUserNickName;
    }

    public String getFromUserNickName() {
        return fromUserNickName;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
