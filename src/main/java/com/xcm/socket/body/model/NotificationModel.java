package com.xcm.socket.body.model;

/**
 * Created by 薛岑明 on 2017/4/5.
 */
public class NotificationModel {
    private String title;
    private String icon;
    private String body;
    private String location;

    public NotificationModel() {
    }

    public NotificationModel(String title,
                             String icon, String body, String location) {
        this.title = title;
        this.icon = icon;
        this.body = body;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
