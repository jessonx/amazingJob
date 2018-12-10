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
@Table(name = "user_authorization")
public class UserAuthorization {
    private long userAuthorizationId;
    private Long userId;
    private String userIdentity;
    private int tokenType;
    private String token;
    private long createdBy;
    private Date createdOn;

    public UserAuthorization() {
    }

    public UserAuthorization(Long userId, String userIdentity, int tokenType, String token, long createdBy, Date createdOn) {
        this.userId = userId;
        this.userIdentity = userIdentity;
        this.tokenType = tokenType;
        this.token = token;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "userAuthorizationId")
    public long getUserAuthorizationId() {
        return userAuthorizationId;
    }

    public void setUserAuthorizationId(long userAuthorizationId) {
        this.userAuthorizationId = userAuthorizationId;
    }

    @Basic
    @Column(name = "userId")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "userIdentity")
    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    @Basic
    @Column(name = "tokenType")
    public int getTokenType() {
        return tokenType;
    }

    public void setTokenType(int tokenType) {
        this.tokenType = tokenType;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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


}
