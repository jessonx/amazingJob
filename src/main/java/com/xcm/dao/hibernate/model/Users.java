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
@Table(name = "users")
public class Users {

    private long userId;
    private String nickName;
    private int gender;
    private String description;
    private String education;
    private String location;
    private String headerThumb;
    private int status;
    private boolean infoVerified;
    private int userType;
    private Date createdOn;
    private long modifiedBy;
    private Date modifiedOn;

    public Users( String nickName, int gender,
                 String description, String education, String location,
                 String headerThumb, int status, boolean infoVerified,
                 int userType, Date createdOn,
                 long modifiedBy, Date modifiedOn) {
        this.nickName = nickName;
        this.gender = gender;
        this.description = description;
        this.education = education;
        this.location = location;
        this.headerThumb = headerThumb;
        this.status = status;
        this.infoVerified = infoVerified;
        this.userType = userType;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

    public Users() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "userId")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "nickName")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Basic
    @Column(name = "gender")
    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
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
    @Column(name = "education")
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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
    @Column(name = "headerThumb")
    public String getHeaderThumb() {
        return headerThumb;
    }

    public void setHeaderThumb(String headerThumb) {
        this.headerThumb = headerThumb;
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
    @Column(name = "infoVerified")
    public boolean isInfoVerified() {
        return infoVerified;
    }

    public void setInfoVerified(boolean infoVerified) {
        this.infoVerified = infoVerified;
    }

    @Basic
    @Column(name = "userType")
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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

        Users users = (Users) o;

        if (userId != users.userId) return false;
        if (gender != users.gender) return false;
        if (status != users.status) return false;
        if (infoVerified != users.infoVerified) return false;
        if (userType != users.userType) return false;
        if (modifiedBy != users.modifiedBy) return false;
        if (nickName != null ? !nickName.equals(users.nickName) : users.nickName != null) return false;
        if (description != null ? !description.equals(users.description) : users.description != null) return false;
        if (education != null ? !education.equals(users.education) : users.education != null) return false;
        if (location != null ? !location.equals(users.location) : users.location != null) return false;
        if (headerThumb != null ? !headerThumb.equals(users.headerThumb) : users.headerThumb != null) return false;
        if (createdOn != null ? !createdOn.equals(users.createdOn) : users.createdOn != null) return false;
        if (modifiedOn != null ? !modifiedOn.equals(users.modifiedOn) : users.modifiedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + gender;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (headerThumb != null ? headerThumb.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (infoVerified ? 1 : 0);
        result = 31 * result + userType;
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (int) (modifiedBy ^ (modifiedBy >>> 32));
        result = 31 * result + (modifiedOn != null ? modifiedOn.hashCode() : 0);
        return result;
    }
}
