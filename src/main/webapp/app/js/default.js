/**
 * Created by 薛岑明 on 2017/3/20.
 */

var lock = getCookie("lock");
if (lock != null && lock == 1) {
    window.location.href = "lock.html"
}
var globalUserId = 0;
var globalNickName = "";
var globalHeaderThumb = "";
var globalUserType = 1;
//获得基本信息
$.ajax({
    url: baseWebServiceUrl + "/user/me/basic",
    method: "GET",
    data: {},
    success: function (data) {
        if (preSuccess(data)) {
            var basicRes = data['basicRes'];
            var headerThumb = basicRes["headerThumb"];
            var nickName = basicRes["nickName"];
            var userType = basicRes["userType"];
            var userId = basicRes["userId"];
            var profileInfoVerified = basicRes['profileInfoVerified'];
            var enterpriseInfoVerified = basicRes['enterpriseInfoVerified'];
            if (!profileInfoVerified) {
                window.location.href = "complete_user.html"
            }
            if (userType == 2 && !enterpriseInfoVerified) {
                window.location.href = "complete_enterprise.html"
            }
            globalUserType = userType;
            globalUserId = userId;
            globalNickName = nickName;
            globalHeaderThumb = headerThumb;
            var notificationCount = basicRes["notificationCount"];
            var messageCount = basicRes["messageCount"];
            sidebarInfo.$set(sidebarInfo, 'headerThumb', headerThumb);
            sidebarInfo.$set(sidebarInfo, 'nickName', nickName);
            sidebarInfo.$set(sidebarInfo, 'userId', userId);
            sidebarInfo.$set(sidebarInfo, 'userType', userType);
            topMessage.$set(topMessage, 'notificationCount', notificationCount);
            topMessage.$set(topMessage, 'messageCount', messageCount);
            topMessage.$set(topMessage, 'headerThumb', headerThumb);
            topMessage.$set(topMessage, 'nickName', nickName);
        }

    },
    error: function () {

    }
});

var topMessage = new Vue({
    el: '#topMessage',
    data: {
        notificationCount: 0,
        messageCount: 0,
        headerThumb: "",
        nickName: ""
    },
    methods: {
        logout: function () {
            $.ajax({
                method: 'POST',
                url: baseWebServiceUrl + '/security/logout',
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (preSuccess(data)) {
                        window.location.href = "login.html";
                    }
                },
                error: function (data) {

                }
            });
        }
    }
});
var sidebarInfo = new Vue({
    el: '#sidebarInfo',
    data: {
        headerThumb: "",
        nickName: "",
        userType: 1,
        userId: 0
    }
});



