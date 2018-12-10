package com.xcm.socket.core.model;

/**
 * Created by 薛岑明 on 2017/1/5.
 */
public class RequestHeader {
    private int sid;//service id
    private int cid;//command id
    private long cseqNo;//客户端发来的消息序号
    private long sseqNo;//服务器返回消息序号, 有值表示改消息被离线储存, 需客户端确认消息.
    private int offMsgType;//消息类型 0 default

    public RequestHeader() {
    }

    public RequestHeader(int sid, int cid, long cseqNo, long sseqNo, int offMsgType) {
        this.sid = sid;
        this.cid = cid;
        this.cseqNo = cseqNo;
        this.sseqNo = sseqNo;
        this.offMsgType = offMsgType;
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

    public int getOffMsgType() {
        return offMsgType;
    }

    public void setOffMsgType(int offMsgType) {
        this.offMsgType = offMsgType;
    }
}
