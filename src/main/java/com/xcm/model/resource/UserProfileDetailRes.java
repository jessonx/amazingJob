package com.xcm.model.resource;

import java.util.List;

/**
 * Created by 薛岑明 on 2017/3/17.
 */
public class UserProfileDetailRes {
    private long userId;
    private long praisesCount;
    private long friendsCount;
    private String headerThumb;
    private int gender;
    private String education;
    private String location;
    private List<LabelRes> skills;
    private String description;
    private String nickName;
    public UserProfileDetailRes() {
    }

    public UserProfileDetailRes(long userId, long praisesCount,
                                long friendsCount, String headerThumb,
                                int gender, String education,
                                String location, List<LabelRes> skills,
                                String description, String nickName) {
        this.userId = userId;
        this.praisesCount = praisesCount;
        this.friendsCount = friendsCount;
        this.headerThumb = headerThumb;
        this.gender = gender;
        this.education = education;
        this.location = location;
        this.skills = skills;
        this.description = description;
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPraisesCount() {
        return praisesCount;
    }

    public void setPraisesCount(long praisesCount) {
        this.praisesCount = praisesCount;
    }

    public long getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(long friendsCount) {
        this.friendsCount = friendsCount;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<LabelRes> getSkills() {
        return skills;
    }

    public void setSkills(List<LabelRes> skills) {
        this.skills = skills;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
