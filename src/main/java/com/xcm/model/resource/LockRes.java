package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/4/12.
 */
public class LockRes {
    private String nickName;
    private String headerThumb;

    public LockRes() {
    }

    public LockRes(String nickName, String headerThumb) {
        this.nickName = nickName;
        this.headerThumb = headerThumb;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeaderThumb() {
        return headerThumb;
    }

    public void setHeaderThumb(String headerThumb) {
        this.headerThumb = headerThumb;
    }
}
