package com.xcm.business;

import com.xcm.model.Enum.ImageTypeEnum;
import com.xcm.model.resource.QiniuRes;
import com.xcm.util.ConfigManager;
import com.xcm.util.QiniuUtil;
import org.springframework.stereotype.Service;

/**
 * Created by 薛岑明 on 2017/4/7.
 */
@Service
public class QiniuBusiness {

    /**
     * 获得上传凭证
     *
     * @param type
     * @return
     */
    public QiniuRes getUploadToken(int type) {
        String token = QiniuUtil.uploadToken();
        String domain = ConfigManager.getProperty(QiniuUtil.testDomain_property);
        String style = null;
        if (type == ImageTypeEnum.USER_HEADER_THUMB.value()) {
            style = ConfigManager.getProperty(QiniuUtil.defaultStyle_property);
        } else if (type == ImageTypeEnum.ENTERPRISE_IMAGE.value()) {
            style = ConfigManager.getProperty(QiniuUtil.enterpriseImageStyle_property);

        }
        return new QiniuRes(domain, style, token);
    }
}
