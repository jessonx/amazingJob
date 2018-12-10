package com.xcm.socket.core.model;

import java.util.Date;

/**
 * Created by 薛岑明 on 2017/1/5.
 */
public class ResponseHeader {
    private int sid;//service id
    private int cid;//command id
    private long cseqNo;//客户端发来的消息序号
    private long sseqNo;//服务器返回消息序号, 有值表示改消息被离线储存, 需客户端确认消息.
    private long respTime;//返回消息时间戳
    private int code;//服务器返回码, 0表示成功, 其他表示错误, 则body类型为ErrorResp
    private int offMsgType;//消息类型 0 default
    private String messageId;

    public ResponseHeader() {
    }

    public ResponseHeader(RequestHeader header) {
        this.sid = header.getSid();
        this.cid = header.getCid();
        this.cseqNo = header.getCseqNo();
        this.sseqNo = header.getSseqNo();
        this.respTime = new Date().getTime();
    }

    public ResponseHeader(int sid, int cid,
                          long cseqNo, long sseqNo,
                          long respTime, int code, int offMsgType, String messageId) {
        this.sid = sid;
        this.cid = cid;
        this.cseqNo = cseqNo;
        this.sseqNo = sseqNo;
        this.respTime = respTime;
        this.code = code;
        this.offMsgType = offMsgType;
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public long getCseqNo() {
        return cseqNo;
    }

    public void setCseqNo(long cseqNo) {
        this.cseqNo = cseqNo;
    }

    public long getSseqNo() {
        return sseqNo;
    }

    public void setSseqNo(long sseqNo) {
        this.sseqNo = sseqNo;
    }

    public long getRespTime() {
        return respTime;
    }

    public void setRespTime(long respTime) {
        this.respTime = respTime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getOffMsgType() {
        return offMsgType;
    }

    public void setOffMsgType(int offMsgType) {
        this.offMsgType = offMsgType;
    }
}
