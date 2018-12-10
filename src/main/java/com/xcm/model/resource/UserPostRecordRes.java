package com.xcm.model.resource;

import java.util.List;

/**
 * Created by 薛岑明 on 2017/3/23.
 */
public class UserPostRecordRes {
    private long userId;
    private int gender;
    private String nickName;
    private long jobId;
    private String date;
    private long userJobPostRecord;
    private int status;
    private String statusStr;
    private String jobName;
    private long offset;
    private List<UserPostRecordOptionRes> options;


    public UserPostRecordRes(long userId,
                             int gender, String nickName,
                             long jobId, String date, long userJobPostRecord,
                             int status, String statusStr, String jobName,
                             long offset,List<UserPostRecordOptionRes> options) {
        this.userId = userId;
        this.gender = gender;
        this.nickName = nickName;
        this.jobId = jobId;
        this.date = date;
        this.userJobPostRecord = userJobPostRecord;
        this.status = status;
        this.statusStr = statusStr;
        this.jobName = jobName;
        this.offset = offset;
        this.options = options;
    }

    public List<UserPostRecordOptionRes> getOptions() {
        return options;
    }

    public void setOptions(List<UserPostRecordOptionRes> options) {
        this.options = options;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public UserPostRecordRes() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getUserJobPostRecord() {
        return userJobPostRecord;
    }

    public void setUserJobPostRecord(long userJobPostRecord) {
        this.userJobPostRecord = userJobPostRecord;
    }
}
