package com.xcm.business;

import com.xcm.dao.EnterpriseDao;
import com.xcm.dao.JobDao;
import com.xcm.dao.UserDao;
import com.xcm.dao.hibernate.model.Enterprises;
import com.xcm.dao.hibernate.model.Jobs;
import com.xcm.dao.hibernate.model.Resume;
import com.xcm.dao.hibernate.model.UserJobPostRecord;
import com.xcm.dao.hibernate.model.Users;
import com.xcm.model.Enum.FocusJobStatusEnum;
import com.xcm.model.Enum.JobStatusEnum;
import com.xcm.model.Enum.UserJobPostStatusEnum;
import com.xcm.model.Enum.UserTypeEnum;
import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.CandidateRes;
import com.xcm.model.resource.EnterpriseBasicRes;
import com.xcm.model.resource.JobBasicRes;
import com.xcm.model.resource.JobDetailRes;
import com.xcm.model.resource.RecommendJobRes;
import com.xcm.model.resource.tuple.TwoTuple;
import com.xcm.socket.MessageCenter;
import com.xcm.socket.body.model.NotificationModel;
import com.xcm.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by 薛岑明 on 2017/3/16.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class JobBusiness {
    @Autowired
    private JobDao jobDao;
    @Autowired
    private EnterpriseDao enterpriseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageCenter messageCenter;

    /**
     * 获得推荐职位（暂定最新）
     *
     * @return
     */
    public List<RecommendJobRes> getRecommendJobResList() {
        List<RecommendJobRes> resList = new LinkedList<>();
        List<Jobs> jobsList = jobDao.getRecommendJobList();
        if (jobsList != null) {
            for (Jobs job : jobsList) {
                long jobId = job.getJobId();
                String name = job.getName();
                String salary = job.getSalary() + "元/月";
                Date publishDate = job.getPublishDate();
                String location = job.getLocation();
                long enterpriseId = job.getEnterpriseId();
                Enterprises enterprise = enterpriseDao.getNormalEnterpriseById(enterpriseId);
                String enterpriseName = enterprise.getName();
                String publishDateStr = CommonUtil.getDateStrByTime(publishDate.getTime());
                RecommendJobRes jobRes = new RecommendJobRes(jobId,
                        name, location, salary, publishDateStr, enterpriseName, enterpriseId);
                resList.add(jobRes);
            }
        }
        return resList;
    }

    private JobBasicRes wrapJobBasicRes(Jobs job, Enterprises enterprises) {
        long jobId = job.getJobId();
        String name = job.getName();
        String location = job.getLocation();
        long enterpriseId = job.getEnterpriseId();
        String salary = job.getSalary() + "元/月";
        String enterpriseName = enterprises.getName();
        String timeStr = CommonUtil.getDateStrByTime(job.getPublishDate().getTime());
        return new JobBasicRes(jobId, name, location, enterpriseId, enterpriseName, salary, timeStr);
    }

    /**
     * 得到投递的职位
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<JobBasicRes> getPostJobsList(long userId, long offset,
                                             int count, boolean loadMore) {
        List<JobBasicRes> resList = new LinkedList<>();
        List<UserJobPostRecord> list = jobDao.getUserPostJobs(userId, offset, count, loadMore);
        for (UserJobPostRecord record : list) {
            long jobId = record.getJobId();
            Jobs job = jobDao.getJobById(jobId);
            if (job != null) {
                long enterpriseId = job.getEnterpriseId();
                Enterprises enterprise = enterpriseDao.getNormalEnterpriseById(enterpriseId);
                if (enterprise != null) {
                    JobBasicRes res = wrapJobBasicRes(job, enterprise);
                    int status = record.getStatus();
                    res.setStatus(status);
                    String statusStr = "";
                    if (status == UserJobPostStatusEnum.CHU_SHEN.value()) {
                        statusStr = "初审中";
                    } else if (status == UserJobPostStatusEnum.BU_HE_SHI.value()) {
                        statusStr = "不合适";
                    } else if (status == UserJobPostStatusEnum.DELETE.value()) {
                        statusStr = "已删除";
                    } else if (status == UserJobPostStatusEnum.MIAN_SHI_TONG_GUO.value()) {
                        statusStr = "面试通过";
                    } else if (status == UserJobPostStatusEnum.TONG_ZHI_MIAN_SHI.value()) {
                        statusStr = "通知面试";
                    } else if (status == UserJobPostStatusEnum.YI_GUO_QI.value()) {
                        statusStr = "已过期";
                    }
                    long thisOffset = record.getModifiedOn().getTime();
                    res.setOffset(thisOffset);
                    res.setStatusStr(statusStr);
                    resList.add(res);
                }
            }
        }
        return resList;
    }

    /**
     * 收藏的职位
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<JobBasicRes> getFocusJobsList(long userId, long offset,
                                              int count, boolean loadMore) {
        List<JobBasicRes> resList = new LinkedList<>();
        List<Map<String, Long>> list = jobDao.getFocusJobsList(userId, offset, count, loadMore);
        for (Map<String, Long> map : list) {
            long jobId = map.get("value");
            long thisOffset = map.get("score");
            Jobs job = jobDao.getJobById(jobId);
            if (job != null) {
                long enterpriseId = job.getEnterpriseId();
                Enterprises enterprise = enterpriseDao.getNormalEnterpriseById(enterpriseId);
                if (enterprise != null) {
                    JobBasicRes res = wrapJobBasicRes(job, enterprise);
                    int status = FocusJobStatusEnum.CAN_POST.value();
                    String statusStr = "可以投递";
                    if (job.getStatus() != JobStatusEnum.NORMAL.value()) {
                        status = FocusJobStatusEnum.OVER_TIME.value();
                        statusStr = "已过期";
                    } else {
                        UserJobPostRecord record = jobDao.getUserJobPostRecordByUserIdAndJobId(userId, jobId);
                        if (record != null) {
                            statusStr = "已投递";

                        }
                    }
                    res.setOffset(thisOffset);
                    res.setStatusStr(statusStr);
                    res.setStatus(status);
                    resList.add(res);
                }
            }
        }
        return resList;
    }

    /**
     * 删除关注职位
     *
     * @param userId
     * @param jobId
     */
    public void delFocusJob(long userId, long jobId) {
        jobDao.delFocusJob(userId, jobId);
    }

    /**
     * 关注职位
     *
     * @param userId
     * @param jobId
     * @param locale
     */
    public void focusJob(long userId, long jobId, Locale locale) {
        double score = jobDao.getScoreFocusByUserIdAndJobId(userId, jobId);
        if (score != 0) {
            throw new BusinessException(ErrorCode.REPEATED_JOB_FOCUS, locale);
        }
        jobDao.focusJob(userId, jobId);
    }

    /**
     * 投递职位
     *
     * @param userId
     * @param jobId
     * @param locale
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void postJob(long userId, long jobId, Locale locale) {

        Resume resume = userDao.getNormalResumeByUserId(userId);
        if (resume == null) {
            throw new BusinessException(ErrorCode.NO_RESUME, locale);
        }
        Users users = userDao.getUserById(userId);
        if (users.getUserType() == UserTypeEnum.ENTERPRISE.value()) {
            throw new BusinessException(ErrorCode.ENTERPRISE_USER_CAN_NOT_POST, locale);
        }
        Jobs jobs = jobDao.getNormalJobById(jobId);
        if (jobs == null) {
            throw new BusinessException(ErrorCode.JOB_IS_NOT_EXIST, locale);
        }
        Enterprises enterprises = enterpriseDao.getNormalEnterpriseById(jobs.getEnterpriseId());
        if (enterprises == null) {
            throw new BusinessException(ErrorCode.ENTERPRISE_IS_NOT_EXIST, locale);
        }
        UserJobPostRecord record = jobDao.getUserJobPostRecordByUserIdAndJobId(userId, jobId);
        if (record != null) {
            throw new BusinessException(ErrorCode.REPEATED_JOB_POST, locale);
        }
        Date date = new Date();
        record = new UserJobPostRecord(userId, jobId,
                UserJobPostStatusEnum.CHU_SHEN.value(), userId, date, userId, date);
        jobDao.saveUserJobPostRecord(record);

        NotificationModel notificationModel = new NotificationModel("职位投递",
                users.getHeaderThumb(), users.getNickName() +
                " 向您的公司发布的：" + jobs.getName() +
                ",投递了简历，请及时查看", "receive_resumes.html");
        messageCenter.senNotification(enterprises.getFounderUserId(), notificationModel);
    }

    /**
     * 根据enterpriseId得到发布的职位
     *
     * @param enterpriseId
     * @return
     */
    public List<JobBasicRes> getPublishJobsListById(long enterpriseId) {
        List<JobBasicRes> resList = new LinkedList<>();
        Enterprises enterprise = enterpriseDao.getNormalEnterpriseById(enterpriseId);
        if (enterprise == null) {
            return resList;
        }
        List<Jobs> list = jobDao.getNormalJobsByEnterpriseId(enterpriseId);
        for (Jobs job : list) {
            JobBasicRes res = wrapJobBasicRes(job, enterprise);
            res.setOffset(job.getModifiedOn().getTime());
            resList.add(res);
        }
        return resList;
    }

    /**
     * 获取职位详细信息
     *
     * @param jobId
     * @param userId
     * @return
     */
    public TwoTuple<JobDetailRes, EnterpriseBasicRes> getJobDetail(long jobId, long userId) {
        TwoTuple<JobDetailRes, EnterpriseBasicRes> tuple = new TwoTuple<>(new JobDetailRes(),
                new EnterpriseBasicRes());
        Jobs job = jobDao.getNormalJobById(jobId);
        if (job == null) {
            return tuple;
        }
        long enterpriseId = job.getEnterpriseId();
        Enterprises enterprise = enterpriseDao.getNormalEnterpriseById(enterpriseId);
        if (enterprise == null) {
            return tuple;
        }
        int status = FocusJobStatusEnum.CAN_POST.value();
        String statusStr = "可以投递";
        if (job.getStatus() != JobStatusEnum.NORMAL.value()) {
            status = FocusJobStatusEnum.OVER_TIME.value();
            statusStr = "已过期";
        } else {
            UserJobPostRecord record =
                    jobDao.getUserJobPostRecordByUserIdAndJobId(userId, jobId);
            if (record != null) {
                statusStr = "已投递";

            }
        }
        String dateStr = CommonUtil.getDateStrByTime(job.getPublishDate().getTime());
        String education = null;
        String field = null;
        String experience = null;
        switch (job.getEducationType()) {
            case 0:
                education = "不要求";
                break;
            case 1:
                education = "大专";
                break;
            case 2:
                education = "本科";
                break;
            case 3:
                education = "硕士";
                break;
            case 4:
                education = "博士";
                break;
            default:
                break;
        }
        switch (job.getFieldType()) {
            case 0:
                field = "不限";
                break;
            case 1:
                field = "金融";

                break;
            case 2:
                field = "IT";
                break;
            case 3:
                field = "行政";
                break;
            case 4:
                field = "文娱";
                break;
            default:
                break;
        }
        switch (job.getExperienceType()) {
            case 0:
                experience = "不限";
                break;
            case 1:
                experience = "3年以下";
                break;
            case 2:
                experience = "3-5年";
                break;
            case 3:
                experience = "5-10年";
                break;
            case 4:
                experience = "10年以上";
                break;
            default:
                break;
        }
        JobDetailRes jobDetail = new JobDetailRes(jobId, job.getName(), job.getLocation(), job.getDescription(),
                job.getSalary() + "元/月", dateStr, status, statusStr, education, field, experience);
        long focusCount = enterpriseDao.getEnterpriseFocusCount(enterpriseId);
        long jobsCount = jobDao.getNormalJobsCount(enterpriseId);
        EnterpriseBasicRes enterpriseBasic = new EnterpriseBasicRes(enterpriseId,
                enterprise.getLocation(), enterprise.getName(), enterprise.getIcon(),
                focusCount, jobsCount, enterprise.getTelphoneNum(), 0);
        tuple = new TwoTuple<>(jobDetail, enterpriseBasic);
        return tuple;
    }

    /**
     * 删除投递记录
     *
     * @param userId
     * @param jobId
     * @param locale
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void delUserJobPostRecordByUserIdAndJobId(long userId, long jobId, Locale locale) {
        UserJobPostRecord record = jobDao.getUserJobPostRecordByUserIdAndJobId(userId, jobId);
        if (record == null) {
            throw new BusinessException(ErrorCode.USER_JOB_POST_RECORD_IS_NOT_EXIT, locale);
        }
        record.setModifiedBy(userId);
        record.setModifiedOn(new Date());
        record.setStatus(UserJobPostStatusEnum.DELETE.value());
        jobDao.saveUserJobPostRecord(record);
    }

    /**
     * 搜索
     *
     * @param salaryType
     * @param locationType
     * @param educationType
     * @param fieldType
     * @param experienceType
     * @param offset
     * @param count
     * @param loadMore
     * @param locale
     * @return
     */
    public List<JobBasicRes> searchJobs(int salaryType,
                                        int locationType, int educationType,
                                        int fieldType, int experienceType, long offset, int count,
                                        boolean loadMore, Locale locale) {
        List<JobBasicRes> resList = new ArrayList<>();
        List<Jobs> list = jobDao.searchJobs(salaryType, locationType,
                educationType, fieldType,
                experienceType, offset, count, loadMore);
        for (Jobs job : list) {
            long enterpriseId = job.getEnterpriseId();
            Enterprises enterprises = enterpriseDao.getNormalEnterpriseById(enterpriseId);
            if (enterprises != null) {
                JobBasicRes res = wrapJobBasicRes(job, enterprises);
                long thisOffset = job.getModifiedOn().getTime();
                res.setOffset(thisOffset);
                resList.add(res);
            }
        }
        return resList;
    }


    /**
     * 获得其他应聘者
     *
     * @param jobId
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<CandidateRes> getOtherCandidates(long jobId, long userId, long offset, int count, boolean loadMore) {
        Jobs jobs = jobDao.getNormalJobById(jobId);
        List<CandidateRes> resList = new LinkedList<>();
        if (jobs == null) {
            return resList;
        }
        String jobName = jobs.getName();
        List<UserJobPostRecord> list = jobDao.getUserJobPostRecordByJobIdLoadMore(jobId, offset, count, loadMore);
        for (UserJobPostRecord record : list) {
            long candiateUid = record.getUserId();
            if (candiateUid != userId) {
                Users candidate = userDao.getUserById(candiateUid);
                String nickName = candidate.getNickName();
                int gender = candidate.getGender();

                CandidateRes res = new CandidateRes(candiateUid, jobId,
                        nickName, gender, jobName, record.getModifiedOn().getTime());
                resList.add(res);
            }
        }
        return resList;
    }


}
