package com.xcm.socket.body.model;

/**
 * Created by 薛岑明 on 2017/4/12.
 */
public class MessageAckModel {
    private String messageId;
    private long fromUserId;

    public MessageAckModel() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public MessageAckModel(String messageId, long fromUserId) {
        this.messageId = messageId;
        this.fromUserId = fromUserId;
    }
}
