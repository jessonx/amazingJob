package com.xcm.model.resource.entity;

import java.util.Date;

/**
 * Created by 薛岑明 on 2017/3/23.
 */
public class UserJobPostRecordEntity {
    private long userId;
    private long jobId;
    private String jobName;
    private long userJobPostRecord;
    private Date createdOn;
    private int status;
    private Date modifiedOn;

    public UserJobPostRecordEntity() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public long getUserJobPostRecord() {
        return userJobPostRecord;
    }

    public void setUserJobPostRecord(long userJobPostRecord) {
        this.userJobPostRecord = userJobPostRecord;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}
