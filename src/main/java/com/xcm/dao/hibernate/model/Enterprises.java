
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
 * Created by 薛岑明 on 2017/3/16.
 */
@Entity
@Table(name = "enterprises")
public class Enterprises {
    private long enterpriseId;
    private String icon;
    private String name;
    private String location;
    private String description;
    private String telphoneNum;
    private long founderUserId;
    private boolean infoVerified;
    private int status;
    private long createdBy;
    private Date createdOn;
    private long modifiedBy;
    private Date modifiedOn;

    public Enterprises() {
    }

    public Enterprises(String icon, String name,
                       String location, String description, String telphoneNum,
                       long founderUserId, boolean infoVerified, int status,
                       long createdBy, Date createdOn, long modifiedBy, Date modifiedOn) {
        this.icon = icon;
        this.name = name;
        this.location = location;
        this.description = description;
        this.telphoneNum = telphoneNum;
        this.founderUserId = founderUserId;
        this.infoVerified = infoVerified;
        this.status = status;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "enterpriseId")
    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Basic
    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "telphoneNum")
    public String getTelphoneNum() {
        return telphoneNum;
    }

    public void setTelphoneNum(String telphoneNum) {
        this.telphoneNum = telphoneNum;
    }

    @Basic
    @Column(name = "founderUserId")
    public long getFounderUserId() {
        return founderUserId;
    }

    public void setFounderUserId(long founderUserId) {
        this.founderUserId = founderUserId;
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

        Enterprises that = (Enterprises) o;

        if (enterpriseId != that.enterpriseId) return false;
        if (founderUserId != that.founderUserId) return false;
        if (status != that.status) return false;
        if (createdBy != that.createdBy) return false;
        if (modifiedBy != that.modifiedBy) return false;
        if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (telphoneNum != null ? !telphoneNum.equals(that.telphoneNum) : that.telphoneNum != null) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (modifiedOn != null ? !modifiedOn.equals(that.modifiedOn) : that.modifiedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (enterpriseId ^ (enterpriseId >>> 32));
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (telphoneNum != null ? telphoneNum.hashCode() : 0);
        result = 31 * result + (int) (founderUserId ^ (founderUserId >>> 32));
        result = 31 * result + status;
        result = 31 * result + (int) (createdBy ^ (createdBy >>> 32));
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (int) (modifiedBy ^ (modifiedBy >>> 32));
        result = 31 * result + (modifiedOn != null ? modifiedOn.hashCode() : 0);
        return result;
    }
}
