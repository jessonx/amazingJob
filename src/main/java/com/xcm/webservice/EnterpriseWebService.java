package com.xcm.webservice;

import com.xcm.business.EnterpriseBusiness;
import com.xcm.business.JobBusiness;
import com.xcm.dao.hibernate.model.Enterprises;
import com.xcm.dao.hibernate.model.UserJobPostRecord;
import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.EnterpriseBasicRes;
import com.xcm.model.resource.EnterpriseDetailRes;
import com.xcm.model.resource.JobBasicRes;
import com.xcm.model.resource.UserPostRecordRes;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by 薛岑明 on 2017/3/17.
 */
@Produces("application/json; charset=UTF-8")
public class EnterpriseWebService {
    @Autowired
    private HttpDao httpDao;
    @Autowired
    private EnterpriseBusiness enterpriseBusiness;

    @POST
    @Path("profile/set")
    public Map<Object, Object> setEnterpriseProfile(@FormParam("enterpriseName") String enterpriseName,
                                                    @FormParam("enterpriseIcon") String enterpriseIcon,
                                                    @FormParam("description") String description,
                                                    @FormParam("address") String address,
                                                    @FormParam("telNum") String telNum,
                                                    @DefaultValue("") @FormParam("enterpriseImgListStr") String enterpriseImgListStr) {
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        if (StringUtil.isNullOrEmpty(enterpriseName) || StringUtil.isNullOrEmpty(enterpriseIcon) ||
                StringUtil.isNullOrEmpty(description) || StringUtil.isNullOrEmpty(address) ||
                StringUtil.isNullOrEmpty(telNum)) {
            throw new BusinessException(ErrorCode.INCOMPLETE_INFO, locale);
        }
        List<String> enterpriseImgList = new LinkedList<>();
        String[] strs = enterpriseImgListStr.split(",");
        for (String str : strs) {
            if (StringUtil.isNullOrEmpty(str)) {
                enterpriseImgList.add(str);
            }
        }
        enterpriseBusiness.setEnterpriseProfile(userId, enterpriseName, enterpriseIcon, description, address, telNum, enterpriseImgList, locale);
        Map<Object, Object> map = new HashMap();
        map.put("result", true);
        return map;
    }

    @POST
    @Path("me/job/publish")
    public Map<Object, Object> publishJob(@FormParam("name") String name,
                                          @FormParam("location") String location,
                                          @FormParam("salary") long salary,
                                          @FormParam("description") String description,
                                          @DefaultValue("0") @FormParam("locationType") int locationType,
                                          @DefaultValue("0") @FormParam("educationType") int educationType,
                                          @DefaultValue("0") @FormParam("fieldType") int fieldType,
                                          @DefaultValue("0") @FormParam("experienceType") int experienceType) {
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        if (StringUtil.isNullOrEmpty(name) || StringUtil.isNullOrEmpty(location) ||
                StringUtil.isNullOrEmpty(description)) {
            throw new BusinessException(ErrorCode.INCOMPLETE_INFO, locale);
        }
        enterpriseBusiness.publishJob(userId, name, location, description, salary, locationType, educationType, fieldType, experienceType, locale);
        Map<Object, Object> map = new HashMap<>();
        map.put("result", true);
        return map;
    }

    @GET
    @Path("me/job/publish/list")
    public Map<Object, Object> getMyEnterprisePublishJobs(@QueryParam("offset") long offset,
                                                          @DefaultValue("10") @QueryParam("count") int count,
                                                          @DefaultValue("false") @QueryParam("loadMore") boolean loadMore) {
        Map<Object, Object> map = new HashMap<>();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        List<JobBasicRes> list = enterpriseBusiness.getMyEnterprisePublishJobs(userId, offset, count, loadMore);
        map.put("list", list);
        return map;
    }

    @GET
    @Path("/job/publish/list")
    public Map<Object, Object> getPublishJobs(@QueryParam("enterpriseId") long enterpriseId,
                                              @QueryParam("offset") long offset,
                                              @DefaultValue("10") @QueryParam("count") int count,
                                              @DefaultValue("false") @QueryParam("loadMore") boolean loadMore) {
        Map<Object, Object> map = new HashMap<>();
        HttpSession session = httpDao.getHttpSession();
        List<JobBasicRes> list = enterpriseBusiness.getPublishJobsList(enterpriseId, offset, count, loadMore);
        map.put("list", list);
        return map;
    }

    @GET
    @Path("detail")
    public Map<Object, Object> getEnterpriseDetail(@QueryParam("enterpriseId") long enterpriseId) {
        Map<Object, Object> map = new HashMap<>();
        EnterpriseDetailRes enterpriseDetailRes =
                enterpriseBusiness.getEnterpriseDetailRes(enterpriseId);
        map.put("detail", enterpriseDetailRes);
        return map;
    }

    @GET
    @Path("/me/focus/list")
    public Map<Object, Object> getFocusEnterprises(@QueryParam("offset") long offset,
                                                   @DefaultValue("10") @QueryParam("count") int count,
                                                   @QueryParam("loadMore") boolean loadMore) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        List<EnterpriseBasicRes> list =
                enterpriseBusiness.getFocusEnterprisesList(userId, offset, count, loadMore);
        map.put("focusEnterprises", list);
        return map;
    }

    @POST
    @Path("/me/focus/del")
    public Map<Object, Object> delFocusEnterprises(@FormParam("enterpriseId") long enterpriseId) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        enterpriseBusiness.delFocusEnterprises(userId, enterpriseId);
        map.put("result", true);
        return map;
    }

    @GET
    @Path("/me/receive/list")
    public Map<Object, Object> getMyReceivePosts(@QueryParam("offset") long offset,
                                                 @DefaultValue("10") @QueryParam("count") int count,
                                                 @QueryParam("loadMore") boolean loadMore) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        List<UserPostRecordRes> list = enterpriseBusiness.getMyReceivePostsList(userId,
                offset, count, loadMore);
        map.put("resList", list);
        return map;
    }

    /**
     * option
     * TONG_ZHI_MIAN_SHI(1), BU_HE_SHI(2),MIAN_SHI_TONG_GUO(3),DEl(4);
     *
     * @param userJobPostRecord
     * @param option
     * @return
     */
    @POST
    @Path("/me/receive/handle")
    public Map<Object, Object> handleUserJobPostRecord(
            @FormParam("userJobPostRecord") long userJobPostRecord, @FormParam("option") int option) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        enterpriseBusiness.handleUserJobPostRecord(userId, userJobPostRecord, option, locale);
        map.put("result", true);
        return map;
    }

    @POST
    @Path("/me/job/del")
    public Map<Object, Object> delJob(
            @FormParam("jobId") long jobId) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        enterpriseBusiness.delJobByJobId(userId, jobId, locale);
        map.put("result", true);
        return map;
    }
}
