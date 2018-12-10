package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/3/17.
 */
public class RecentVisitorRes {
    private long userId;
    private String headerThumb;
    private String nickName;
    private String visitDate;

    public RecentVisitorRes() {
    }

    public RecentVisitorRes(long userId, String headerThumb,
                            String nickName, String visitDate) {
        this.userId = userId;
        this.headerThumb = headerThumb;
        this.nickName = nickName;
        this.visitDate = visitDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHeaderThumb() {
        return headerThumb;
    }

    public void setHeaderThumb(String headerThumb) {
        this.headerThumb = headerThumb;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }
}
