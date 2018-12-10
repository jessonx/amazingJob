package com.xcm.webservice;

import com.xcm.business.JobBusiness;
import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.CandidateRes;
import com.xcm.model.resource.EnterpriseBasicRes;
import com.xcm.model.resource.JobBasicRes;
import com.xcm.model.resource.JobDetailRes;
import com.xcm.model.resource.RecommendJobRes;
import com.xcm.model.resource.tuple.TwoTuple;
import com.xcm.util.CommonUtil;
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


@Produces("application/json; charset=UTF-8")
public class JobWebService {
    @Autowired
    private HttpDao httpDao;
    @Autowired
    private JobBusiness jobBusiness;

    @GET
    @Path("/recommend")
    public Map<Object, Object> getRecommendJobs() {
        Map<Object, Object> map = new HashMap();
        List<RecommendJobRes> list = jobBusiness.getRecommendJobResList();
        map.put("recommendJobs", list);
        return map;
    }

    @GET
    @Path("/me/post/list")
    public Map<Object, Object> getPostJobs(@QueryParam("offset") long offset,
                                           @DefaultValue("10") @QueryParam("count") int count,
                                           @DefaultValue("false") @QueryParam("loadMore") boolean loadMore) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        List<JobBasicRes> list = jobBusiness.getPostJobsList(userId, offset, count, loadMore);
        map.put("postJobs", list);
        return map;
    }

    @GET
    @Path("/me/focus/list")
    public Map<Object, Object> getFocusJobs(@QueryParam("offset") long offset,
                                            @DefaultValue("10") @QueryParam("count") int count,
                                            @QueryParam("loadMore") boolean loadMore) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        List<JobBasicRes> list = jobBusiness.getFocusJobsList(userId, offset, count, loadMore);
        map.put("focusJobs", list);
        return map;
    }

    @POST
    @Path("/me/focus/del")
    public Map<Object, Object> delFocusJobs(@FormParam("jobId") long jobId) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        jobBusiness.delFocusJob(userId, jobId);
        map.put("result", true);
        return map;
    }

    @POST
    @Path("/focus")
    public Map<Object, Object> focusJob(@FormParam("jobId") long jobId) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        jobBusiness.focusJob(userId, jobId, locale);
        map.put("result", true);
        return map;
    }

    @POST
    @Path("/post")
    public Map<Object, Object> postJob(@FormParam("jobId") long jobId) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        jobBusiness.postJob(userId, jobId, locale);
        map.put("result", true);
        return map;
    }

    @GET
    @Path("/publish/list")
    public Map<Object, Object> getPublishJobsListById(
            @QueryParam("enterpriseId") long enterpriseId) {
        Map<Object, Object> map = new HashMap<>();
        List<JobBasicRes> list = jobBusiness.getPublishJobsListById(enterpriseId);
        map.put("list", list);
        return map;
    }

    @GET
    @Path("detail")
    public Map<Object, Object> getUserDetail(@QueryParam("jobId") long jobId) {
        Map<Object, Object> map = new HashMap<>();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        TwoTuple<JobDetailRes, EnterpriseBasicRes> tuple = jobBusiness.getJobDetail(jobId, userId);
        map.put("jobDetail", tuple.first);
        map.put("enterpriseBasic", tuple.second);
        return map;
    }

    @POST
    @Path("/me/post/del")
    public Map<Object, Object> delUserJobPostRecord(@FormParam("jobId") long jobId) {
        Map<Object, Object> map = new HashMap<>();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        jobBusiness.delUserJobPostRecordByUserIdAndJobId(userId, jobId, locale);
        map.put("result", true);
        return map;
    }

    @GET
    @Path("/search")
    public Map<Object, Object> searchJobs(@DefaultValue("0") @QueryParam("salaryType") int salaryType,
                                          @DefaultValue("0") @QueryParam("locationType") int locationType,
                                          @DefaultValue("0") @QueryParam("educationType") int educationType,
                                          @DefaultValue("0") @QueryParam("fieldType") int fieldType,
                                          @DefaultValue("0") @QueryParam("experienceType") int experienceType,
                                          @QueryParam("offset") long offset,
                                          @DefaultValue("10") @QueryParam("count") int count,
                                          @DefaultValue("false") @QueryParam("loadMore") boolean loadMore) {
        Map<Object, Object> map = new HashMap<>();
        HttpSession session = httpDao.getHttpSession();

        session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        List<JobBasicRes> list = new LinkedList<>();
        if (CommonUtil.isJobParamNormal(salaryType) &&
                CommonUtil.isJobParamNormal(locationType) &&
                CommonUtil.isJobParamNormal(educationType) &&
                CommonUtil.isJobParamNormal(fieldType) &&
                CommonUtil.isJobParamNormal(experienceType)) {
            list = jobBusiness.searchJobs(salaryType, locationType, educationType, fieldType, experienceType, offset, count, loadMore, locale);

        } else {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, locale);
        }
        map.put("list", list);
        return map;
    }

    @GET
    @Path("/candidates")
    public Map<Object, Object> getOtherCandidates(
            @QueryParam("jobId") long jobId,
            @QueryParam("offset") long offset,
                                                  @DefaultValue("10") @QueryParam("count") int count,
                                                  @QueryParam("loadMore") boolean loadMore) {
        Map<Object, Object> map = new HashMap();
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        List<CandidateRes> list = jobBusiness.getOtherCandidates(jobId,userId, offset, count, loadMore);
        map.put("resList", list);
        return map;
    }

}
