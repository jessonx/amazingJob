package com.xcm.model.resource;

import java.util.List;

/**
 * Created by 薛岑明 on 2017/3/20.
 */
public class EnterpriseDetailRes {
    private long enterpriseId;
    private String location;
    private String name;
    private String icon;
    private long focusCount;
    private long jobsCount;
    private String telNum;
    private String description;
    private List<String> introduceImages;
    private UserProfileDetailRes userProfileDetailRes;
    private String firstImg;

    public EnterpriseDetailRes() {
    }

    public EnterpriseDetailRes(long enterpriseId, String location,
                               String name, String icon, long focusCount,
                               long jobsCount, String telNum, String description,
                               List<String> introduceImages, UserProfileDetailRes res, String firstImg) {
        this.enterpriseId = enterpriseId;
        this.location = location;
        this.name = name;
        this.icon = icon;
        this.focusCount = focusCount;
        this.jobsCount = jobsCount;
        this.telNum = telNum;
        this.description = description;
        this.introduceImages = introduceImages;
        this.userProfileDetailRes = res;
        this.firstImg = firstImg;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public UserProfileDetailRes getUserProfileDetailRes() {
        return userProfileDetailRes;
    }

    public void setUserProfileDetailRes(UserProfileDetailRes userProfileDetailRes) {
        this.userProfileDetailRes = userProfileDetailRes;
    }

    public List<String> getIntroduceImages() {
        return introduceImages;
    }

    public void setIntroduceImages(List<String> introduceImages) {
        this.introduceImages = introduceImages;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
