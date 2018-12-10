package com.xcm.model.resource;


public class JobDetailRes {
    private long jobId;
    private String name;
    private String location;
    private String description;
    private String salary;
    private String publishDate;
    private int status;
    private String statusStr;
    private String education;
    private String field;
    private String experience;

    public JobDetailRes(long jobId, String name,
                        String location, String description,
                        String salary, String publishDate,
                        int status, String statusStr,
                        String education, String field,
                        String experience) {
        this.jobId = jobId;
        this.name = name;
        this.location = location;
        this.description = description;
        this.salary = salary;
        this.publishDate = publishDate;
        this.status = status;
        this.statusStr = statusStr;
        this.education = education;
        this.field = field;
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public JobDetailRes() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
