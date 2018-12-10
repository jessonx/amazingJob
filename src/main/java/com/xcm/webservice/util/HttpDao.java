package com.xcm.webservice.util;


import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.util.StringUtil;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Component
public class HttpDao {

    // Constants
    public static final String USER_ID = "userId";
    public static final String PERMISSIONID = "permissionId";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    private String userKey = "userId";

    public HttpDao() {
    }

    public HttpDao(String userKey) {
        this.userKey = userKey;
    }

    /**
     * 获取当前HTTP请求对象
     *
     * @return HttpServletRequest对象
     */
    public HttpServletRequest getRequest() {
        Message message = PhaseInterceptorChain.getCurrentMessage();
        return (HttpServletRequest) message
                .get(AbstractHTTPDestination.HTTP_REQUEST);
    }

    /**
     * 获取当前HTTP请求的Session对象
     *
     * @return HttpSession对象
     * @throws BusinessException
     */
    public HttpSession getHttpSession() throws BusinessException {
        return getHttpSession(true);
    }

    public boolean isLogin() {
        HttpSession session = getRequest().getSession(false);
        return session != null && session.getAttribute(USER_ID) != null;
    }

    /**
     * @param restrict 是否限制login
     * @return
     * @throws BusinessException
     */
    public HttpSession getHttpSession(boolean restrict)
            throws BusinessException {
        HttpSession session = getRequest().getSession(false);
        if (restrict
                && (session == null || session.getAttribute(userKey) == null)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, getLocale());
        }
        return session;
    }

    public String getClientType() {
        return getRequest().getHeader("Source");
    }

    public int getClientVersion() {
        int versionCode = 0;
        String version = getRequest().getHeader("VersionCode");
        if (!StringUtil.isNullOrEmpty(version)) {
            versionCode = Integer.parseInt(version);
        }
        return versionCode;
    }

    /**
     * 获取IOS version
     *
     * @return
     */
    public String getClientIosVersion() {
        String version = getRequest().getHeader("version");
        return version;
    }

    /**
     * 获取客户端Locale
     *
     * @return 客户端Locale
     */
    public Locale getLocale() {
        return getRequest().getLocale();
    }

    /**
     * 获取用户ip
     *
     * @return
     */
    public String getIpAddr() {

        String ip = getRequest().getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getRemoteAddr();
        }
        return ip == null ? "" : ip;
    }
}
