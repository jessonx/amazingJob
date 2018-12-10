package com.xcm.model.resource;


public class RecommendJobRes {
    private long jobId;
    private String jobName;
    private String location;
    private String salary;
    private String publishDate;
    private String enterpriseName;
    private long enterpriseId;

    public RecommendJobRes(long jobId, String jobName,
                           String location, String salary, String publishDate,
                           String enterpriseName, long enterpriseId) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.location = location;
        this.salary = salary;
        this.publishDate = publishDate;
        this.enterpriseName = enterpriseName;
        this.enterpriseId = enterpriseId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
