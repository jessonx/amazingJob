package com.xcm.dao.hibernate.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by 薛岑明 on 2017/4/3.
 */
@Entity
@Table(name = "private_message_record")
public class PrivateMessageRecord {
    private long privateMessageRecordId;
    private long fromUserId;
    private long toUserId;
    private String messageId;
    private Date messageOn;
    private String content;

    public PrivateMessageRecord() {
    }

    public PrivateMessageRecord(long fromUserId, long toUserId,
                                String messageId, Date messageOn, String content) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.messageId = messageId;
        this.messageOn = messageOn;
        this.content = content;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "privateMessageRecordId")
    public long getPrivateMessageRecordId() {
        return privateMessageRecordId;
    }

    public void setPrivateMessageRecordId(long privateMessageRecordId) {
        this.privateMessageRecordId = privateMessageRecordId;
    }

    @Basic
    @Column(name = "fromUserId")
    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    @Basic
    @Column(name = "toUserId")
    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    @Basic
    @Column(name = "messageId")
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "messageOn")
    public Date getMessageOn() {
        return messageOn;
    }

    public void setMessageOn(Date messageOn) {
        this.messageOn = messageOn;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateMessageRecord that = (PrivateMessageRecord) o;

        if (privateMessageRecordId != that.privateMessageRecordId) return false;
        if (fromUserId != that.fromUserId) return false;
        if (toUserId != that.toUserId) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (messageOn != null ? !messageOn.equals(that.messageOn) : that.messageOn != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (privateMessageRecordId ^ (privateMessageRecordId >>> 32));
        result = 31 * result + (int) (fromUserId ^ (fromUserId >>> 32));
        result = 31 * result + (int) (toUserId ^ (toUserId >>> 32));
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (messageOn != null ? messageOn.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
