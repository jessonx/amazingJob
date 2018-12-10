package com.xcm.socket.core.model;

import com.xcm.socket.body.model.PrivateMessage;

import java.io.Serializable;

/**
 * Created by 薛岑明 on 2017/1/5.
 */
public class SocketResponse<T> implements Serializable {
    private ResponseHeader responseHeader;
    private T body;

    public SocketResponse() {
    }

    public SocketResponse(SocketRequest<T> socketRequest) {
        RequestHeader header = socketRequest.getRequestHeader();
        T body = socketRequest.getBody();
        ResponseHeader responseHeader = new ResponseHeader(header);
        this.body = body;
        this.responseHeader = responseHeader;
    }

    public SocketResponse(ResponseHeader responseHeader, T body) {
        this.responseHeader = responseHeader;
        this.body = body;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
