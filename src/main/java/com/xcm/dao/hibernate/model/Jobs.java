package com.xcm.dao.hibernate.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "jobs")
public class Jobs {
    private long jobId;
    private long enterpriseId;
    private String name;
    private String location;
    private long salary;
    private String description;
    private Date publishDate;
    private int status;
    private long createdBy;
    private Date createdOn;
    private long modifiedBy;
    private Date modifiedOn;
    private int locationType;
    private int educationType;
    private int fieldType;
    private int experienceType;

    public Jobs() {
    }

    public Jobs(long enterpriseId, String name, String location,
                long salary, String description, Date publishDate,
                int status, long createdBy, Date createdOn,
                long modifiedBy, Date modifiedOn, int locationType,
                int educationType, int fieldType, int experienceType) {
        this.enterpriseId = enterpriseId;
        this.name = name;
        this.location = location;
        this.salary = salary;
        this.description = description;
        this.publishDate = publishDate;
        this.status = status;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
        this.locationType = locationType;
        this.educationType = educationType;
        this.fieldType = fieldType;
        this.experienceType = experienceType;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "jobId")
    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "enterpriseId")
    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "salary")
    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "publishDate")
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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


    @Basic
    @Column(name = "locationType")
    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    @Basic
    @Column(name = "educationType")
    public int getEducationType() {
        return educationType;
    }

    public void setEducationType(int educationType) {
        this.educationType = educationType;
    }

    @Basic
    @Column(name = "fieldType")
    public int getFieldType() {
        return fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    @Basic
    @Column(name = "experienceType")
    public int getExperienceType() {
        return experienceType;
    }

    public void setExperienceType(int experienceType) {
        this.experienceType = experienceType;
    }
}
