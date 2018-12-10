package com.xcm.util;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QiniuUtil {

    private static final String accessKey_property = "qiniu.AccessKey";
    private static final String secretKey_property = "qiniu.SecretKey";
    private static final String bucket_property = "qiniu.bucket";
    public static final String defaultStyle_property = "qiniu.style.defaut";
    public static final String enterpriseImageStyle_property = "qiniu.style.enterpriseImage";
    public static final String testDomain_property = "qiniu.test.url";

    public static String uploadToken(String bucket, String accessKey, String secretKey, String fileKey) {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, fileKey);
        return upToken;
    }

    public static String uploadToken() {
        String accessKey = ConfigManager.getProperty(accessKey_property);
        String secretKey = ConfigManager.getProperty(secretKey_property);
        String bucket = ConfigManager.getProperty(bucket_property);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }


}
