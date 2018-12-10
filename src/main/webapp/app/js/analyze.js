/**
 * Created by 薛岑明 on 2017/3/20.
 * 解析socket模型
 */

var SocketRequest = function (requestHeader, body, userId) {
    this.requestHeader = requestHeader;
    this.body = body;
    this.userId = userId;
};

var RequestHeader = function (sid, cid, cseqNo, sseqNo
    , offMsgType) {
    this.sid = sid;
    this.cid = cid;
    this.cseqNo = cseqNo;
    this.sseqNo = sseqNo;
    this.offMsgType = offMsgType;
};

var SocketResponse = function (responseHeader, body) {
    this.requestHeader = requestHeader;
    this.body = body;
};

var ResponseHeader = function (sid, cid, cseqNo, sseqNo
    , respTime, code, offMsgType, messageId) {
    this.sid = sid;
    this.cid = cid;
    this.cseqNo = cseqNo;
    this.sseqNo = sseqNo;
    this.respTime = respTime;
    this.code = code;
    this.offMsgType = offMsgType;
    this.messageId = messageId;
};

var PrivateMessage = function (fromUserId, fromUserNickName, fromUserHeaderThumb, toUserId, content, msgType, time, offset) {
    this.fromUserId = fromUserId;
    this.fromUserNickName = fromUserNickName;
    this.fromUserHeaderThumb = fromUserHeaderThumb;
    this.toUserId = toUserId;
    this.content = content;
    this.msgType = msgType;
    this.time = time;
    this.offset = offset;

};

var MessageAckModel = function (messageId, fromUserId) {
    this.messageId = messageId;
    this.fromUserId = fromUserId;
}