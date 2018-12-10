package com.xcm.webservice;

import com.xcm.business.SecurityBusiness;
import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.UserBasicRes;
import com.xcm.util.StringUtil;
import com.xcm.webservice.util.HttpDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Produces("application/json; charset=UTF-8")
public class SecurityWebService {
    @Autowired
    private SecurityBusiness securityBusiness;
    @Autowired
    private HttpDao httpDao;

    @POST
    @Path("/logout")
    public Map<String, Object> logout() {
        Map<String, Object> result = new HashMap<String, Object>();
        HttpSession session = httpDao.getHttpSession();
        if (null != session) {
            session.invalidate();
        }
        result.put("result", true);
        return result;
    }

    @POST
    @Path("/login")
    public Map<String, Object> login(@FormParam("token") String token,
                                     @FormParam("password") String password,
                                     @DefaultValue("1") @FormParam("tokenType") int tokenType,
                                     @DefaultValue("") @FormParam("deviceId") String deviceId) {
        Map<String, Object> result = new HashMap<String, Object>();
        Locale locale = httpDao.getLocale();
        String ip = httpDao.getIpAddr();
        if (StringUtil.isNullOrEmpty(token) || StringUtil.isNullOrEmpty(password)) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, locale);
        }
        UserBasicRes userBasicRes = securityBusiness.login(token, password,
                tokenType, deviceId, ip, locale);
        HttpSession session = httpDao.getRequest().getSession(true);
        session.setAttribute(HttpDao.USER_ID, userBasicRes.getUserId());
        result.put("useType", userBasicRes.getUserType());
        return result;
    }

    @POST
    @Path("/register")
    public Map<String, Object> register(@FormParam("nickName") String nickName,
                                        @FormParam("token") String token,
                                        @FormParam("password") String password,
                                        @FormParam("userType") int userType,
                                        @DefaultValue("1") @FormParam("tokenType") int tokenType) {
        Map<String, Object> result = new HashMap<String, Object>();
        Locale locale = httpDao.getLocale();
        if (StringUtil.isNullOrEmpty(token) || StringUtil.isNullOrEmpty(password)) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, locale);
        }
        String ip = httpDao.getIpAddr();
        long userId = securityBusiness.register(nickName,
                token, password, userType, ip, locale, tokenType);
        HttpSession session = httpDao.getRequest().getSession(true);
        session.setAttribute(HttpDao.USER_ID, userId);

        result.put("result", true);
        return result;
    }

    @POST
    @Path("/unlock")
    public Map<String, Object> register(@FormParam("password") String password, @DefaultValue("1") @FormParam("tokenType") int tokenType) {
        Map<String, Object> result = new HashMap<String, Object>();
        Locale locale = httpDao.getLocale();
        if (StringUtil.isNullOrEmpty(password)) {
            throw new BusinessException(ErrorCode.PASSWORD_CAN_NOT_BE_NULL, locale);
        }
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        securityBusiness.unlock(userId, password,tokenType,locale);
        result.put("result", true);
        return result;
    }
}
