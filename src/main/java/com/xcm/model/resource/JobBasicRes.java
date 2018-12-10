package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/3/18.
 */
public class JobBasicRes {
    private long jobId;
    private String name;
    private String location;
    private long enterpriseId;
    private String enterpriseName;
    private String salary;
    private String publishDate;
    private int status;
    private String statusStr;
    private long offset;

    public JobBasicRes(long jobId, String name, String location,
                       long enterpriseId, String enterpriseName,
                       String salary, String publishDate) {
        this.jobId = jobId;
        this.name = name;
        this.location = location;
        this.enterpriseId = enterpriseId;
        this.enterpriseName = enterpriseName;
        this.salary = salary;
        this.publishDate = publishDate;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
