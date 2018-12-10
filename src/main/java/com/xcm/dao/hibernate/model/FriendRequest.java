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
 * Created by 薛岑明 on 2017/3/18.
 */
@Entity
@Table(name = "friend_request")
public class FriendRequest {
    private long friendRequestId;
    private long fromUserId;
    private long toUserId;
    private int status;
    private long createdBy;
    private Date createdOn;
    private long modifiedBy;
    private Date modifiedOn;

    public FriendRequest() {
    }

    public FriendRequest( long fromUserId, long toUserId, int status,
                         long createdBy, Date createdOn, long modifiedBy, Date modifiedOn) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "friendRequestId")
    public long getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(long friendRequestId) {
        this.friendRequestId = friendRequestId;
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
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "createdBy")
    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "createdOn")
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "modifiedBy")
    public long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Basic
    @Column(name = "modifiedOn")
    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendRequest that = (FriendRequest) o;

        if (friendRequestId != that.friendRequestId) return false;
        if (fromUserId != that.fromUserId) return false;
        if (toUserId != that.toUserId) return false;
        if (status != that.status) return false;
        if (createdBy != that.createdBy) return false;
        if (modifiedBy != that.modifiedBy) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (modifiedOn != null ? !modifiedOn.equals(that.modifiedOn) : that.modifiedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (friendRequestId ^ (friendRequestId >>> 32));
        result = 31 * result + (int) (fromUserId ^ (fromUserId >>> 32));
        result = 31 * result + (int) (toUserId ^ (toUserId >>> 32));
        result = 31 * result + status;
        result = 31 * result + (int) (createdBy ^ (createdBy >>> 32));
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (int) (modifiedBy ^ (modifiedBy >>> 32));
        result = 31 * result + (modifiedOn != null ? modifiedOn.hashCode() : 0);
        return result;
    }
}
