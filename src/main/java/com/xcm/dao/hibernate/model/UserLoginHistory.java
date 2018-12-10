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
 * Created by jm-xuecenming on 2016/12/16.
 */
@Entity
@Table(name = "user_login_history")
public class UserLoginHistory {
    private long userLoginHistoryRecordId;
    private long userId;
    private int phoneType;
    private String version;
    private String deviceId;
    private String deviceType;
    private String ip;
    private String versionName;
    private Date createdOn;

    public UserLoginHistory() {
    }

    public UserLoginHistory(long userId, int phoneType, String version, String deviceId, String deviceType, String ip, String versionName, Date createdOn) {
        this.userId = userId;
        this.phoneType = phoneType;
        this.version = version;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.ip = ip;
        this.versionName = versionName;
        this.createdOn = createdOn;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "userLoginHistoryRecordId")
    public long getUserLoginHistoryRecordId() {
        return userLoginHistoryRecordId;
    }

    public void setUserLoginHistoryRecordId(long userLoginHistoryRecordId) {
        this.userLoginHistoryRecordId = userLoginHistoryRecordId;
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
    @Column(name = "phoneType")
    public int getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(int phoneType) {
        this.phoneType = phoneType;
    }

    @Basic
    @Column(name = "version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "deviceId")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "deviceType")
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "versionName")
    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Basic
    @Column(name = "createdOn")
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLoginHistory that = (UserLoginHistory) o;

        if (userLoginHistoryRecordId != that.userLoginHistoryRecordId) return false;
        if (userId != that.userId) return false;
        if (phoneType != that.phoneType) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (versionName != null ? !versionName.equals(that.versionName) : that.versionName != null) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userLoginHistoryRecordId ^ (userLoginHistoryRecordId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + phoneType;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (versionName != null ? versionName.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        return result;
    }
}
