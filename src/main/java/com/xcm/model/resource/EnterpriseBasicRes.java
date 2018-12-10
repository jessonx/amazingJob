package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/3/20.
 */
public class EnterpriseBasicRes {
    private long enterpriseId;
    private String location;
    private String name;
    private String icon;
    private long focusCount;
    private long jobsCount;
    private String telNum;
    private long offset;

    public EnterpriseBasicRes() {
    }

    public EnterpriseBasicRes(long enterpriseId, String location,
                              String name, String icon,
                              long focusCount, long jobsCount, String telNum,long offset) {
        this.enterpriseId = enterpriseId;
        this.location = location;
        this.name = name;
        this.icon = icon;
        this.focusCount = focusCount;
        this.jobsCount = jobsCount;
        this.telNum = telNum;
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getFocusCount() {
        return focusCount;
    }

    public void setFocusCount(long focusCount) {
        this.focusCount = focusCount;
    }

    public long getJobsCount() {
        return jobsCount;
    }

    public void setJobsCount(long jobsCount) {
        this.jobsCount = jobsCount;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }
}
