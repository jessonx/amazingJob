package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/4/5.
 */
public class NotificationRes {
    private String title;
    private String body;
    private String location;
    private String time;
    private long offset;

    public NotificationRes() {
    }

    public NotificationRes(String title,
                           String body, String location, String time, long offset) {
        this.title = title;
        this.body = body;
        this.location = location;
        this.time = time;
        this.offset = offset;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
