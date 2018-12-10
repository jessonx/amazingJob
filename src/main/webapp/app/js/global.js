/**
 * Created by 薛岑明 on 2017/3/1.
 */
var baseWebServiceUrl = "/offer/webService";

//标签
function Skill(labelNum, labelName) {
    this.labelNum = labelNum;
    this.labelName = labelName;
}

//    推荐职位
function RecommendJobRes(jobId, jobName, location, salary,
                         publishDate, enterpriseName, enterpriseId) {
    this.jobId = jobId;
    this.jobName = jobName;
    this.location = location;
    this.salary = salary;
    this.publishDate = publishDate;
    this.enterpriseName = enterpriseName;
    this.enterpriseId = enterpriseId;
}

//来访者

function Visitor(userId, headerThumb, nickName, visitDate) {
    this.userId = userId;
    this.headerThumb = headerThumb;
    this.nickName = nickName;
    this.visitDate = visitDate;
}

function preSuccess(data) {
    var result = false;
    var err_msg = '';
    var err_code = 0;
    if (data["err_msg"] != null) {
        err_msg = data["err_msg"];
        err_code = data['err_code'];
        alertModal(err_msg, 'ERROR');
        if (err_code == 20005) {
            //没有登录1S后跳转至登录
            var sleep = setTimeout(function () {
                window.location.href = '/offer/app/login.html';
            }, 2000);
        }
        result = false;
    } else {
        result = true;
    }
    return result;

}

function alertModal(content, title) {
    $('#myModalLabel').html(title);
    $('#err_msg').html(content);
    $('#errModal').modal('show');               // initializes and invokes show immediately
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        var result = unescape(r[2]);
        if (result == "" || result == null) {
            window.location.href = "404.html";
        } else {
            return result;
        }
    }
    else {
        window.location.href = "404.html";
    }
}
//写cookies

function setCookie(name, value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}

//得到cookie
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

    if (arr = document.cookie.match(reg))

        return unescape(arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}