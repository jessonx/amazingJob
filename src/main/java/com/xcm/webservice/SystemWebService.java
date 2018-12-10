package com.xcm.webservice;

import com.xcm.business.QiniuBusiness;
import com.xcm.model.resource.QiniuRes;
import com.xcm.webservice.util.HttpDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 薛岑明 on 2017/4/7.
 */
@Produces("application/json; charset=UTF-8")
public class SystemWebService {
    @Autowired
    private QiniuBusiness qiniuBusiness;
    @Autowired
    private HttpDao httpDao;

    @GET
    @Path("uploadToken")
    public Map<Object, Object> getUploadToken(@DefaultValue("1") @QueryParam("type") int type) {
        httpDao.getHttpSession();
        QiniuRes qiniuRes = qiniuBusiness.getUploadToken(type);
        Map<Object, Object> map = new HashMap<>();
        map.put("res", qiniuRes);
        return map;
    }

}
