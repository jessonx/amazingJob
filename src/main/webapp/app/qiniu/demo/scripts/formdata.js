var domain = null;
var token = null;
var style = null;
$.ajax({
    "type": "GET",
    "url": baseWebServiceUrl + "/system/uploadToken",
    "cache": false,
    "dataType": "json",
    success: function (data) {
        if (preSuccess(data)) {
            //页面中删除
            var res = data['res'];
            domain = res['domain'];
            $('#userfile').val(res['token']);
            style = res['style']
        }
    },
    error: function () {
        this.err_msg = "网络未连接";
    }
})
;
// jQuery zepto vue angular 等库皆有 progress 的实现 以jQuery为例：


$(function () {
    var $key = $('#key');  // file name    eg: the file is image.jpg,but $key='a.jpg', you will upload the file named 'a.jpg'
    var $userfile = $('#userfile');  // the file you selected

    // upload info
    var $selectedFile = $('.selected-file');
    var $progress = $(".progress");
    var $uploadedResult = $('.uploaded-result');

    $("#userfile").change(function () {  // you can ues 'onchange' here to uplpad automatically after select a file
        $uploadedResult.html('');
        var selectedFile = $userfile.val();
        if (selectedFile) {
            // randomly generate the final file name
            var ramdomName = Math.random().toString(36).substr(2) + $userfile.val().match(/\.?[^.\/]+$/);
            $key.val(ramdomName);
            $selectedFile.html('文件：' + selectedFile);
        } else {
            return false;
        }
        var f = new FormData(document.getElementById("testform"));
        $.ajax({
            url: 'http://up-z2.qiniu.com/',  // Different bucket zone has different upload url, you can get right url by the browser error massage when uploading a file with wrong upload url.
            type: 'POST',
            data: f,
            processData: false,
            contentType: false,
            xhr: function () {
                myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) {
                    myXhr.upload.addEventListener('progress', function (e) {
                        console.log(e);
                        if (e.lengthComputable) {
                            var percent = e.loaded / e.total * 100;
                            $progress.html('上传：' + e.loaded + "/" + e.total + " bytes. " + percent.toFixed(2) + "%");
                        }
                    }, false);
                }
                return myXhr;
            },
            success: function (res) {
                console.log("成功：" + JSON.stringify(res));
                var url = domain + res.key + style;

            },
            error: function (res) {
                alertModal("失败:" + JSON.stringify(res), "ERROR");
            }
        });
        return false;
    });
});