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
 * Created by 薛岑明 on 2017/3/22.
 */
@Entity
@Table(name = "resume")
public class Resume {
    private long resumeId;
    private long userId;
    private String realName;
    private String birthDay;
    private String employmentLength;
    private String phoneNum;
    private String email;
    private String schoolName;
    private String major;
    private String schoolTime;
    private String workExperience;
    private String projectExperience;
    private String certificate;
    private String description;
    private int status;
    private long createdBy;
    private Date createdOn;
    private long modifiedBy;
    private Date modifiedOn;
    private String highestEducation;

    public Resume() {
    }

    public Resume(long userId, String realName, String birthDay,
                  String employmentLength, String phoneNum, String email,
                  String schoolName, String major, String schoolTime, String workExperience,
                  String projectExperience, String certificate, String description,
                  int status, long createdBy, Date createdOn, long modifiedBy, Date modifiedOn, String highestEducation) {
        this.userId = userId;
        this.realName = realName;
        this.birthDay = birthDay;
        this.employmentLength = employmentLength;
        this.phoneNum = phoneNum;
        this.email = email;
        this.schoolName = schoolName;
        this.major = major;
        this.schoolTime = schoolTime;
        this.workExperience = workExperience;
        this.projectExperience = projectExperience;
        this.certificate = certificate;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
        this.highestEducation = highestEducation;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "resumeId")
    public long getResumeId() {
        return resumeId;
    }

    public void setResumeId(long resumeId) {
        this.resumeId = resumeId;
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
    @Column(name = "realName")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Basic
    @Column(name = "birthDay")
    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    @Basic
    @Column(name = "employmentLength")
    public String getEmploymentLength() {
        return employmentLength;
    }

    public void setEmploymentLength(String employmentLength) {
        this.employmentLength = employmentLength;
    }

    @Basic
    @Column(name = "highestEducation")
    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    @Basic
    @Column(name = "phoneNum")
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "schoolName")
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Basic
    @Column(name = "major")
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Basic
    @Column(name = "schoolTime")
    public String getSchoolTime() {
        return schoolTime;
    }

    public void setSchoolTime(String schoolTime) {
        this.schoolTime = schoolTime;
    }

    @Basic
    @Column(name = "workExperience")
    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    @Basic
    @Column(name = "projectExperience")
    public String getProjectExperience() {
        return projectExperience;
    }

    public void setProjectExperience(String projectExperience) {
        this.projectExperience = projectExperience;
    }

    @Basic
    @Column(name = "certificate")
    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
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
    @Column(name = "STATUS")
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

        Resume resume = (Resume) o;

        if (resumeId != resume.resumeId) return false;
        if (userId != resume.userId) return false;
        if (status != resume.status) return false;
        if (createdBy != resume.createdBy) return false;
        if (modifiedBy != resume.modifiedBy) return false;
        if (realName != null ? !realName.equals(resume.realName) : resume.realName != null) return false;
        if (birthDay != null ? !birthDay.equals(resume.birthDay) : resume.birthDay != null) return false;
        if (employmentLength != null ? !employmentLength.equals(resume.employmentLength) : resume.employmentLength != null)
            return false;
        if (phoneNum != null ? !phoneNum.equals(resume.phoneNum) : resume.phoneNum != null) return false;
        if (email != null ? !email.equals(resume.email) : resume.email != null) return false;
        if (schoolName != null ? !schoolName.equals(resume.schoolName) : resume.schoolName != null) return false;
        if (major != null ? !major.equals(resume.major) : resume.major != null) return false;
        if (schoolTime != null ? !schoolTime.equals(resume.schoolTime) : resume.schoolTime != null) return false;
        if (workExperience != null ? !workExperience.equals(resume.workExperience) : resume.workExperience != null)
            return false;
        if (projectExperience != null ? !projectExperience.equals(resume.projectExperience) : resume.projectExperience != null)
            return false;
        if (certificate != null ? !certificate.equals(resume.certificate) : resume.certificate != null) return false;
        if (description != null ? !description.equals(resume.description) : resume.description != null) return false;
        if (createdOn != null ? !createdOn.equals(resume.createdOn) : resume.createdOn != null) return false;
        if (modifiedOn != null ? !modifiedOn.equals(resume.modifiedOn) : resume.modifiedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (resumeId ^ (resumeId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        result = 31 * result + (employmentLength != null ? employmentLength.hashCode() : 0);
        result = 31 * result + (phoneNum != null ? phoneNum.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (schoolName != null ? schoolName.hashCode() : 0);
        result = 31 * result + (major != null ? major.hashCode() : 0);
        result = 31 * result + (schoolTime != null ? schoolTime.hashCode() : 0);
        result = 31 * result + (workExperience != null ? workExperience.hashCode() : 0);
        result = 31 * result + (projectExperience != null ? projectExperience.hashCode() : 0);
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (int) (createdBy ^ (createdBy >>> 32));
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (int) (modifiedBy ^ (modifiedBy >>> 32));
        result = 31 * result + (modifiedOn != null ? modifiedOn.hashCode() : 0);
        return result;
    }
}
