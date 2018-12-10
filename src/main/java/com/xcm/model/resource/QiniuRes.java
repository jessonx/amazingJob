package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/4/7.
 */
public class QiniuRes {
    private String domain;
    private String style;
    private String token;

    public QiniuRes() {
    }

    public QiniuRes(String domain, String style, String token) {
        this.domain = domain;
        this.style = style;
        this.token = token;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
