package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/4/16.
 */
public class CandidateRes {
    private long userId;
    private long jobId;
    private String nickName;
    private int gender;
    private String jobName;
    private long offset;

    public CandidateRes() {
    }

    public CandidateRes(long userId,long jobId, String nickName,
                        int gender, String jobName, long offset) {
        this.jobId = jobId;
        this.userId = userId;
        this.nickName = nickName;
        this.gender = gender;
        this.jobName = jobName;
        this.offset = offset;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
