package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/3/22.
 */
public class ResumeRes {
    private long resumeId;
    private long userId;
    private int gender;
    private String location;
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
    private String headerThumb;
    private String highestEducation;

    public ResumeRes() {
    }


    public ResumeRes(long resumeId, long userId, int gender,
                     String location, String realName, String birthDay,
                     String employmentLength, String phoneNum, String email,
                     String schoolName, String major, String schoolTime,
                     String workExperience, String projectExperience,
                     String certificate, String description,
                     String headerThumb, String highestEducation) {
        this.resumeId = resumeId;
        this.userId = userId;
        this.gender = gender;
        this.location = location;
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
        this.headerThumb = headerThumb;
        this.highestEducation = highestEducation;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getHeaderThumb() {
        return headerThumb;
    }

    public void setHeaderThumb(String headerThumb) {
        this.headerThumb = headerThumb;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getResumeId() {
        return resumeId;
    }

    public void setResumeId(long resumeId) {
        this.resumeId = resumeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmploymentLength() {
        return employmentLength;
    }

    public void setEmploymentLength(String employmentLength) {
        this.employmentLength = employmentLength;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchoolTime() {
        return schoolTime;
    }

    public void setSchoolTime(String schoolTime) {
        this.schoolTime = schoolTime;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getProjectExperience() {
        return projectExperience;
    }

    public void setProjectExperience(String projectExperience) {
        this.projectExperience = projectExperience;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
