package com.xcm.socket.core.model;

/**
 * Created by 薛岑明 on 2017/1/5.
 */
public class SocketRequest <T>{
    private RequestHeader requestHeader;
    private T body;
    private long userId;

    public SocketRequest() {
    }

    public SocketRequest(RequestHeader requestHeader, T body, long userId) {
        this.requestHeader = requestHeader;
        this.body = body;
        this.userId = userId;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
