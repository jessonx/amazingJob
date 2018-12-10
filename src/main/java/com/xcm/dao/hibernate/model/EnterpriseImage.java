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
@Table(name = "enterprise_image")
public class EnterpriseImage {
    private long enterpriseImageId;
    private long enterpriseId;
    private String imageLocation;
    private int status;
    private long createdBy;
    private Date createdOn;
    private long modifiedBy;
    private Date modifiedOn;

    public EnterpriseImage() {
    }

    public EnterpriseImage(long enterpriseId, String imageLocation, int status,
                           long createdBy, Date createdOn,
                           long modifiedBy, Date modifiedOn) {
        this.enterpriseId = enterpriseId;
        this.imageLocation = imageLocation;
        this.status = status;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "enterpriseImageId")
    public long getEnterpriseImageId() {
        return enterpriseImageId;
    }

    public void setEnterpriseImageId(long enterpriseImageId) {
        this.enterpriseImageId = enterpriseImageId;
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
    @Column(name = "imageLocation")
    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnterpriseImage that = (EnterpriseImage) o;

        if (enterpriseImageId != that.enterpriseImageId) return false;
        if (enterpriseId != that.enterpriseId) return false;
        if (status != that.status) return false;
        if (createdBy != that.createdBy) return false;
        if (modifiedBy != that.modifiedBy) return false;
        if (imageLocation != null ? !imageLocation.equals(that.imageLocation) : that.imageLocation != null)
            return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (modifiedOn != null ? !modifiedOn.equals(that.modifiedOn) : that.modifiedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (enterpriseImageId ^ (enterpriseImageId >>> 32));
        result = 31 * result + (int) (enterpriseId ^ (enterpriseId >>> 32));
        result = 31 * result + (imageLocation != null ? imageLocation.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (int) (createdBy ^ (createdBy >>> 32));
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (int) (modifiedBy ^ (modifiedBy >>> 32));
        result = 31 * result + (modifiedOn != null ? modifiedOn.hashCode() : 0);
        return result;
    }
}
