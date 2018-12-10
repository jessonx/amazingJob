package com.xcm.webservice;

import com.xcm.business.UserBusiness;
import com.xcm.model.Enum.GenderEnum;
import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.FocusRes;
import com.xcm.model.resource.LabelRes;
import com.xcm.model.resource.LockRes;
import com.xcm.model.resource.NotificationRes;
import com.xcm.model.resource.ResumeRes;
import com.xcm.model.resource.UserBasicRes;
import com.xcm.model.resource.UserProfileDetailRes;
import com.xcm.util.StringUtil;
import com.xcm.webservice.util.HttpDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Produces("application/json; charset=UTF-8")
public class UserWebService {
    @Autowired
    public UserBusiness userBusiness;
    @Autowired
    public HttpDao httpDao;

    @POST
    @Path("/me/profile/set")
    public Map<Object, Object> setUserProfile(@FormParam("headerThumb") String headerThumb,
                                              @FormParam("nickName") String nickName,
                                              @FormParam("gender") int gender,
                                              @FormParam("description") String description,
                                              @FormParam("education") String education,
                                              @FormParam("location") String location
    ) {
        HttpSession httpSession = httpDao.getHttpSession();
        long userId = (long) httpSession.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        if (gender != GenderEnum.MALE.value() && gender != GenderEnum.FEMALE.value()) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, locale);
        }
        if (StringUtil.isNullOrEmpty(headerThumb) || StringUtil.isNullOrEmpty(description)
                || StringUtil.isNullOrEmpty(education) || StringUtil.isNullOrEmpty(location) || StringUtil.isNullOrEmpty(nickName)) {
            throw new BusinessException(ErrorCode.INCOMPLETE_INFO, locale);
        }
        int userType = userBusiness.setUserProfile(userId, headerThumb, nickName, gender, description, education, location);
        Map<Object, Object> map = new HashMap();
        map.put("userType", userType);
        return map;
    }

    @POST
    @Path("/label/add")
    public Map<Object, Object> addUserLabel(@FormParam("labelName") String labelName
    ) {
        HttpSession httpSession = httpDao.getHttpSession();
        long userId = (long) httpSession.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        LabelRes labelRes = userBusiness.addUserLabel(userId, labelName, locale);
        Map<Object, Object> map = new HashMap();
        map.put("label", labelRes);
        return map;
    }

    @POST
    @Path("/label/delete")
    public Map<Object, Object> deleteUserLabel(@FormParam("labelNum") int labelNum
    ) {
        HttpSession httpSession = httpDao.getHttpSession();
        long userId = (long) httpSession.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();

        userBusiness.deleteUserLabel(userId, labelNum, locale);
        Map<Object, Object> map = new HashMap();
        map.put("result", true);
        return map;
    }

    @GET
    @Path("/me/basic")
    public Map<Object, Object> getUserBasicInfo() {
        HttpSession httpSession = httpDao.getHttpSession();
        long userId = (long) httpSession.getAttribute(HttpDao.USER_ID);
        UserBasicRes basicRes = userBusiness.getUserBasicInfo(userId);
        Map<Object, Object> map = new HashMap();
        map.put("basicRes", basicRes);
        return map;
    }

    @GET
    @Path("/me/focus")
    public Map<Object, Object> getUserFocus() {
        HttpSession httpSession = httpDao.getHttpSession();
        long userId = (long) httpSession.getAttribute(HttpDao.USER_ID);
        FocusRes focusRes = userBusiness.getUserFocusRes(userId);
        Map<Object, Object> map = new HashMap();
        map.put("focus", focusRes);
        return map;
    }

    @GET
    @Path("/me/profile/detail")
    public Map<Object, Object> getMyUserProfileDetail() {
        Locale locale = httpDao.getLocale();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        UserProfileDetailRes res = userBusiness.getUserProfileDetailRes(userId);
        Map<Object, Object> map = new HashMap();
        map.put("profileDetailRes", res);
        return map;
    }

    @GET
    @Path("/profile/detail")
    public Map<Object, Object> getUserProfileDetail(@QueryParam("userId") long userId) {
        Locale locale = httpDao.getLocale();
        HttpSession session = httpDao.getHttpSession();
        long ownUserId = (long) session.getAttribute(HttpDao.USER_ID);
        UserProfileDetailRes res = userBusiness.getUserProfileDetailRes(userId);
        userBusiness.addUserRecentVisitor(userId, ownUserId);
        Map<Object, Object> map = new HashMap();
        map.put("profileDetailRes", res);
        return map;
    }

    @POST
    @Path("/praise")
    public Map<Object, Object> praiseUser(@FormParam("userId") long userId) {
        HttpSession httpSession = httpDao.getHttpSession();
        long ownUserId = (long) httpSession.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        userBusiness.praiseUserByUserId(ownUserId, userId, locale);
        Map<Object, Object> map = new HashMap();
        map.put("result", true);
        return map;
    }

    @POST
    @Path("/focus/enterprise")
    public Map<Object, Object> focusEnterprise(@FormParam("enterpriseId") long enterpriseId) {
        HttpSession httpSession = httpDao.getHttpSession();
        long ownUserId = (long) httpSession.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        userBusiness.focusEnterprise(ownUserId, enterpriseId, locale);
        Map<Object, Object> map = new HashMap();
        map.put("result", true);
        return map;
    }

    @POST
    @Path("/me/resume/setting")
    public Map<Object, Object> setMyResume(
            @FormParam("realName") String realName,
            @FormParam("birthDay") String birthDay,
            @FormParam("employmentLength") String employmentLength,
            @FormParam("phoneNum") String phoneNum,
            @FormParam("email") String email,
            @FormParam("schoolName") String schoolName,
            @FormParam("major") String major,
            @FormParam("schoolTime") String schoolTime,
            @FormParam("workExperience") String workExperience,
            @FormParam("projectExperience") String projectExperience,
            @FormParam("certificate") String certificate,
            @FormParam("description") String description,
            @FormParam("highestEducation") String highestEducation) {
        HttpSession httpSession = httpDao.getHttpSession();
        long userId = (long) httpSession.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        if (StringUtil.isNullOrEmpty(realName) ||
                StringUtil.isNullOrEmpty(birthDay) ||
                StringUtil.isNullOrEmpty(employmentLength) ||
                StringUtil.isNullOrEmpty(phoneNum) ||
                StringUtil.isNullOrEmpty(email) ||
                StringUtil.isNullOrEmpty(schoolName) ||
                StringUtil.isNullOrEmpty(major) ||
                StringUtil.isNullOrEmpty(schoolTime) ||
                StringUtil.isNullOrEmpty(workExperience) ||
                StringUtil.isNullOrEmpty(projectExperience) ||
                StringUtil.isNullOrEmpty(certificate) ||
                StringUtil.isNullOrEmpty(description) ||
                StringUtil.isNullOrEmpty(highestEducation)
                ) {
            throw new BusinessException(ErrorCode.INCOMPLETE_INFO, locale);
        }
        userBusiness.setMyResume(userId, realName, birthDay,
                employmentLength, phoneNum, email, schoolName, major,
                schoolTime, workExperience, projectExperience,
                certificate, description, locale, highestEducation);
        Map<Object, Object> map = new HashMap();
        map.put("result", true);
        return map;
    }

    @GET
    @Path("/me/resume")
    public Map<Object, Object> getMyResume() {
        Map<Object, Object> map = new HashMap<>();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        ResumeRes res = userBusiness.getResumeResByUserId(userId);
        map.put("res", res);
        return map;
    }

    @GET
    @Path("resume")
    public Map<Object, Object> getOthersResumeRes(@QueryParam("userId") long userId) {
        Map<Object, Object> map = new HashMap<>();
        ResumeRes res = userBusiness.getResumeResByUserId(userId);
        map.put("res", res);
        return map;
    }


    @GET
    @Path("notification/list")
    public Map<Object, Object> getNotificationList(@QueryParam("offset") long offset,
                                                   @DefaultValue("10") @QueryParam("count") int count,
                                                   @QueryParam("loadMore") boolean loadMore) {
        Map<Object, Object> map = new HashMap<>();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        List<NotificationRes> list = userBusiness.getNotificationListByUserId(userId, offset, count, loadMore);
        map.put("list", list);
        return map;
    }

    @POST
    @Path("notification/del")
    public Map<Object, Object> delNotification(@FormParam("offset") long offset) {
        Map<Object, Object> map = new HashMap<>();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();

        userBusiness.delNotification(userId, offset, locale);
        map.put("result", true);
        return map;
    }

    @GET
    @Path("/unlock/res")
    public Map<Object, Object> getLockRes() {
        Map<Object, Object> map = new HashMap<>();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        LockRes res = userBusiness.getLockResByUserId(userId);
        map.put("res", res);
        return map;
    }
}
