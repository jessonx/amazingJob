/**
 * Created by 薛岑明 on 2017/3/20.
 */
var chat = false;
var receivePrivateMessages = [];
var reconnect_max_count = 100;
var now_reconnect_count = 0;
var baseSocketUrl = "ws://120.24.237.159:8080/offer/websocket/socket";
var ws = new WebSocket(baseSocketUrl);
initWebSocket();
var heartCheck = {
    start: function () {
        // console.log("heartCheck：start");
        var self = this;
        var heartPackageInterval = setInterval(function () {
            // console.log("heartCheck：heartPackageInterval");

            if (ws.readyState == WebSocket.OPEN) {
                sendHeartPackage();
            } else {
                // console.log("heartCheck：clearInterval heartPackageInterval");
            }
        }, 1000);
    },
};

var login = function () {
    var header = new RequestHeader(100, 101, 0, 0, 0);
    var userId = globalUserId;
    var body = "";
    var request = new SocketRequest(header, body, userId);
    sendMessage(request);
    if (chat) {
        setTimeout(function () {
            getOfflineMsg(requestUseId);
        }, 1000);
    }
};

var sendPrivateMessage = function (content, fromUserId, toUserId, fromUserNickName, fromUserHeaderThumb) {
    var header = new RequestHeader(200, 101, 0, 0, 0);
    var body = new PrivateMessage(fromUserId, fromUserNickName, fromUserHeaderThumb, toUserId, content, 0, "", 0);
    var request = new SocketRequest(header, body, globalUserId);
    sendMessage(request, true);
};
var sendMessage = function (request, active) {
    var messageToStr = JSON.stringify(request);
    // console.log("sendMessage:" + messageToStr);
    if (ws.readyState == WebSocket.OPEN) {
        ws.send(messageToStr);
    } else {
        if (active) {
            alertModal("连接断开发送失败", "WARN");
        }
        // console.log("send failed. reconnect");
        reconnect();

    }
};

var reconnectInterval = null;

var reconnect = function () {
    // console.log("begin reconnect");
    if (ws.readyState == WebSocket.OPEN) {
        //重连成功
        now_reconnect_count = 0;
        clearInterval(reconnectInterval);
    } else {
        //超过最大重连次数
        if (now_reconnect_count <= reconnect_max_count) {
            // console.log("reconnect...." + "now_reconnect_count:" + now_reconnect_count);
            if (ws == null ||
                ws.readyState == WebSocket.CLOSED) {
                ws = new WebSocket(baseSocketUrl);
                initWebSocket();
                now_reconnect_count++;
            }

        }
    }
};

var sendHeartPackage = function () {
    // console.log("sendHeartPackage");
    var header = new RequestHeader(1000, 1000, 0, 0, 0);
    var userId = globalUserId;
    var body = "";
    var request = new SocketRequest(header, body, userId);
    sendMessage(request);
};
var getOfflineMsg = function (fromUserId) {
    var toUserId = globalUserId;
    if (toUserId != fromUserId) {
        var header = new RequestHeader(300, 100, 0, 0, 0);
        var body = fromUserId;
        var request = new SocketRequest(header, body, toUserId);
        sendMessage(request);

    }
};

var msgAck = function (fromUserId, messageId) {
    var toUserId = globalUserId;
    if (toUserId != fromUserId) {
        var header = new RequestHeader(300, 200, 0, 0, 0);
        var body = new MessageAckModel(messageId, fromUserId);
        var request = new SocketRequest(header, body, toUserId);
        sendMessage(request);

    }
};

function initWebSocket() {
    ws.onopen = function () {
        // console.log("webSocket：onopen");

        heartCheck.start();
        //登录
        setTimeout(function () {
            login();
        }, 1000);

    };
    ws.onmessage = function (event) {
        // console.log("webSocket：onmessage");
        var receivedStr = event.data;
        // console.log('Received: ' + receivedStr);
        var response = JSON.parse(receivedStr);
        //收到的离线消息种类

        var header = response['responseHeader'];
        var body = response['body'];
        var offlineType = 0;
        if (header != null) {
            offlineType = response['responseHeader']['offMsgType'];
        }

        switch (offlineType) {
            case 1://私信
                var privateMessage = response['body'];
                var fromUserId = privateMessage['fromUserId'];
                var toUserId = privateMessage['toUserId'];
                if (fromUserId != globalUserId) {
                    var messageCount = topMessage.messageCount + 1;
                    topMessage.$set(topMessage, 'messageCount', messageCount);
                }
                if (fromUserId == globalUserId) {
                    if (receivePrivateMessages[toUserId] == null) {
                        receivePrivateMessages[toUserId] = [];
                    }
                    receivePrivateMessages[toUserId].push(privateMessage);
                } else {
                    //发送通知
                    createNewNotification(privateMessage["fromUserNickName"],
                        privateMessage["fromUserHeaderThumb"],
                        privateMessage["content"], "");

                    if (receivePrivateMessages[fromUserId] == null) {
                        receivePrivateMessages[fromUserId] = [];
                    }
                    receivePrivateMessages[fromUserId].push(privateMessage);
                }
                if (chat) {
                    //判断是否在聊天界面
                    if (fromUserId == globalUserId) {
                        chatDiv.$set(chatDiv, 'messages',
                            receivePrivateMessages[toUserId]);

                    } else if (chatDiv.toUserId == fromUserId) {
                        var messageId = response['responseHeader']['messageId'];
                        //删除离线消息
                        var sleep = setTimeout(function () {
                            msgAck(fromUserId, messageId);
                        }, 1000);
                        chatDiv.$set(chatDiv, 'messages',
                            receivePrivateMessages[fromUserId]);
                    }

                }
                break;
            case 2:
                var notification = response['body'];
                createNewNotification(notification["title"],
                    notification["icon"],
                    notification["body"], notification["location"]);
                var notificationCount = topMessage.notificationCount + 1;
                topMessage.$set(topMessage, 'notificationCount', notificationCount);
                break;
            default:
                break;

        }
    };

    ws.onclose = function () {
        // console.log("webSocket：onclose");
        if (reconnectInterval == null) {
            reconnectInterval = setInterval(function () {
                reconnect();
            }, 1000);
        }
    };
    ws.onerror = function () {
        // console.log("webSocket：onerror");
        if (reconnectInterval == null) {
            reconnectInterval = setInterval(function () {
                reconnect();
            }, 1000);
        }
    };
}
