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
@Table(name = "user_job_post_record")
public class UserJobPostRecord {
    private long userJobPostRecord;
    private long userId;
    private long jobId;
    private int status;
    private long createdBy;
    private Date createdOn;
    private long modifiedBy;
    private Date modifiedOn;

    public UserJobPostRecord() {
    }

    public UserJobPostRecord(long userId, long jobId,
                             int status, long createdBy, Date createdOn,
                             long modifiedBy, Date modifiedOn) {
        this.userId = userId;
        this.jobId = jobId;
        this.status = status;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "userJobPostRecord")
    public long getUserJobPostRecord() {
        return userJobPostRecord;
    }

    public void setUserJobPostRecord(long userJobPostRecord) {
        this.userJobPostRecord = userJobPostRecord;
    }

    @Basic
    @Column(name = "userId")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "jobId")
    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
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

        UserJobPostRecord that = (UserJobPostRecord) o;

        if (userJobPostRecord != that.userJobPostRecord) return false;
        if (userId != that.userId) return false;
        if (jobId != that.jobId) return false;
        if (status != that.status) return false;
        if (createdBy != that.createdBy) return false;
        if (modifiedBy != that.modifiedBy) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (modifiedOn != null ? !modifiedOn.equals(that.modifiedOn) : that.modifiedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userJobPostRecord ^ (userJobPostRecord >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (jobId ^ (jobId >>> 32));
        result = 31 * result + status;
        result = 31 * result + (int) (createdBy ^ (createdBy >>> 32));
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (int) (modifiedBy ^ (modifiedBy >>> 32));
        result = 31 * result + (modifiedOn != null ? modifiedOn.hashCode() : 0);
        return result;
    }
}
