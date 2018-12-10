package com.xcm.business;

import com.xcm.dao.EnterpriseDao;
import com.xcm.dao.FriendDao;
import com.xcm.dao.JobDao;
import com.xcm.dao.UserDao;
import com.xcm.dao.hibernate.model.EnterpriseImage;
import com.xcm.dao.hibernate.model.Enterprises;
import com.xcm.dao.hibernate.model.Jobs;
import com.xcm.dao.hibernate.model.UserJobPostRecord;
import com.xcm.dao.hibernate.model.UserLabel;
import com.xcm.dao.hibernate.model.Users;
import com.xcm.model.Enum.GenderEnum;
import com.xcm.model.Enum.JobStatusEnum;
import com.xcm.model.Enum.UserJobPostHandleOptionEnum;
import com.xcm.model.Enum.UserJobPostStatusEnum;
import com.xcm.model.Enum.UserTypeEnum;
import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.EnterpriseBasicRes;
import com.xcm.model.resource.EnterpriseDetailRes;
import com.xcm.model.resource.JobBasicRes;
import com.xcm.model.resource.LabelRes;
import com.xcm.model.resource.UserPostRecordOptionRes;
import com.xcm.model.resource.UserPostRecordRes;
import com.xcm.model.resource.UserProfileDetailRes;
import com.xcm.model.resource.entity.UserJobPostRecordEntity;
import com.xcm.util.CommonUtil;
import com.xcm.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 薛岑明 on 2017/3/16.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class EnterpriseBusiness {
    @Autowired
    private EnterpriseDao enterpriseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private FriendDao friendDao;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void setEnterpriseProfile(long userId, String enterpriseName,
                                     String enterpriseIcon, String description,
                                     String address, String telNum, List<String> enterpriseImgList, Locale locale) {
        if (!StringUtil.isTelStr(telNum)) {
            throw new BusinessException(ErrorCode.TEL_NUM_FORM_ERROR, locale);
        }
        Users user = userDao.getUserById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_IS_NOT_EXIST, locale);
        }
        if (user.getUserType() != UserTypeEnum.ENTERPRISE.value()) {
            throw new BusinessException(ErrorCode.ENTERPRISE_IS_NOT_EXIST, locale);
        }
        Enterprises enterprise = enterpriseDao.getEnterpriseByFounderUserId(userId);
        Date date = new Date();
        if (enterprise == null) {
            enterprise = new Enterprises("http://onxm1cexj.bkt.clouddn.com/default-enterprise.png?imageView2/5/w/200/h/200/format/jpg/q/75|imageslim", "", "", "", "",
                    userId, false, 0, userId, date, userId, date);
        }
        enterprise.setModifiedOn(date);
        enterprise.setModifiedBy(userId);
        enterprise.setName(enterpriseName);
        enterprise.setIcon(enterpriseIcon);
        enterprise.setLocation(address);
        enterprise.setDescription(description);
        enterprise.setTelphoneNum(telNum);
        if (!enterprise.isInfoVerified()) {
            enterprise.setInfoVerified(true);
        }
        enterpriseDao.saveEnterprise(enterprise);
        for (String img : enterpriseImgList) {
            EnterpriseImage enterpriseImage = new EnterpriseImage(enterprise.getEnterpriseId(),
                    img, 0, userId, date, userId, date);
            enterpriseDao.saveEnterpriseImage(enterpriseImage);
        }

    }

    /**
     * 发布一个职位
     *
     * @param userId
     * @param name
     * @param location
     * @param description
     * @param salary
     * @param locationType
     * @param educationType
     * @param fieldType
     * @param experienceType
     * @param locale
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void publishJob(long userId, String name, String location, String description, long salary, int locationType, int educationType, int fieldType, int experienceType, Locale locale) {
        Users user = userDao.getUserById(userId);
        int userType = user.getUserType();
        if (userType != UserTypeEnum.ENTERPRISE.value()) {
            throw new BusinessException(ErrorCode.NO_PERMISSION, locale);
        }
        if (salary <= 0) {
            throw new BusinessException(ErrorCode.SALARY_FORMAT_ERROR, locale);
        }
        long enterpriseId = getEnterpriseIdByFounderUserId(userId, locale);
        Date date = new Date();
        Jobs job = new Jobs(enterpriseId, name,
                location, salary, description, date,
                0, userId, date, userId, date,
                locationType, educationType, fieldType, experienceType);
        jobDao.saveJob(job);
    }

    /**
     * 通过创建者Id找到企业id
     *
     * @param userId
     * @param locale
     * @return
     */
    private long getEnterpriseIdByFounderUserId(long userId, Locale locale) {
        Enterprises enterprise = enterpriseDao.getEnterpriseByFounderUserId(userId);
        if (enterprise == null) {
            throw new BusinessException(ErrorCode.ENTERPRISE_IS_NOT_EXIST, locale);
        }
        long enterpriseId = enterprise.getEnterpriseId();
        return enterpriseId;
    }

    /**
     * 公司发布的职位
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<JobBasicRes> getMyEnterprisePublishJobs(long userId,
                                                        long offset, int count, boolean loadMore) {
        List<JobBasicRes> resList = new LinkedList<>();
        Users user = userDao.getUserById(userId);
        int userType = user.getUserType();
        if (userType != UserTypeEnum.ENTERPRISE.value()) {
            return resList;
        }
        Enterprises enterprise = enterpriseDao.getEnterpriseByFounderUserId(userId);
        if (enterprise == null) {
            return resList;
        }
        long enterpriseId = enterprise.getEnterpriseId();
        List<Jobs> list = jobDao.getJobsByEnterpriseIdWithPage(enterpriseId, offset, count, loadMore);
        for (Jobs job : list) {
            JobBasicRes res = wrapJobBasicRes(job, enterprise);
            res.setOffset(job.getModifiedOn().getTime());
            resList.add(res);
        }
        return resList;
    }

    public List<JobBasicRes> getPublishJobsList(long enterpriseId, long offset, int count, boolean loadMore) {
        List<JobBasicRes> resList = new LinkedList<>();
        Enterprises enterprise = enterpriseDao.getNormalEnterpriseById(enterpriseId);
        if (enterprise == null) {
            return resList;
        }
        List<Jobs> list = jobDao.getJobsByEnterpriseIdWithPage(enterpriseId, offset, count, loadMore);
        for (Jobs job : list) {
            JobBasicRes res = wrapJobBasicRes(job, enterprise);
            resList.add(res);
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
     * 获得EnterpriseDetailRes
     *
     * @param enterpriseId
     * @return
     */
    public EnterpriseDetailRes getEnterpriseDetailRes(long enterpriseId) {
        EnterpriseDetailRes res = new EnterpriseDetailRes();
        Enterprises enterprise =
                enterpriseDao.getNormalEnterpriseById(enterpriseId);
        if (enterprise == null) {
            return res;
        }
        List<EnterpriseImage> enterpriseImageList =
                enterpriseDao.getEnterpriseImageListByEnterpriseIdId(enterpriseId);
        List<String> images = enterpriseImageList.stream()
                .map(EnterpriseImage::getImageLocation)
                .collect(Collectors.toList());

        String firstImg = "";
        if (images.size() > 1) {
            firstImg = images.get(0);
            images.remove(0);
        }
        long focusCount = enterpriseDao.getEnterpriseFocusCount(enterpriseId);
        long jobsCount = jobDao.getNormalJobsCount(enterpriseId);

        long userId = enterprise.getFounderUserId();
        Users user = userDao.getUserById(userId);
        String nickName = "";
        String description = "";
        String education = "";
        String location = "";
        String headerThumb = "";
        int gender = GenderEnum.MALE.value();
        if (user != null) {
            nickName = user.getNickName();
            description = user.getDescription();
            education = user.getEducation();
            location = user.getLocation();
            gender = user.getGender();
            headerThumb = user.getHeaderThumb();
        }
        List<UserLabel> labels = userDao.getUserLabelsByUserId(userId);
        List<LabelRes> resList = new LinkedList<>();
        for (UserLabel label : labels) {
            LabelRes labelRes =
                    new LabelRes(label.getLabelNum(), label.getLabelName());
            resList.add(labelRes);
        }
        long praisedCount = userDao.getPraisedCount(userId);
        long friendsCount = friendDao.getFriendsCount(userId);
        UserProfileDetailRes detailRes = new UserProfileDetailRes(userId
                , praisedCount, friendsCount, headerThumb, gender,
                education, location, resList, description, nickName);


        res = new EnterpriseDetailRes(enterpriseId, enterprise.getLocation(),
                enterprise.getName(), enterprise.getIcon(), focusCount,
                jobsCount, enterprise.getTelphoneNum(),
                enterprise.getDescription(), images, detailRes, firstImg);
        return res;
    }

    /**
     * 自己关注的企业
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<EnterpriseBasicRes> getFocusEnterprisesList(long userId,
                                                            long offset, int count, boolean loadMore) {
        List<EnterpriseBasicRes> resList = new LinkedList<>();
        List<Map<String, Long>> list = enterpriseDao.getFocusEnterprisesList(userId, offset, count, loadMore);
        for (Map<String, Long> map : list) {
            long enterpriseId = map.get("value");
            long thisOffset = (map.get("score")).longValue();
            Enterprises enterprise = enterpriseDao.getNormalEnterpriseById(enterpriseId);
            if (enterprise != null) {
                long focusCount = enterpriseDao.getEnterpriseFocusCount(enterpriseId);
                long jobsCount = jobDao.getNormalJobsCount(enterpriseId);
                EnterpriseBasicRes enterpriseBasic = new EnterpriseBasicRes(enterpriseId,
                        enterprise.getLocation(), enterprise.getName(), enterprise.getIcon(),
                        focusCount, jobsCount, enterprise.getTelphoneNum(), thisOffset);
                resList.add(enterpriseBasic);
            }
        }
        return resList;
    }

    /**
     * 删除关注的企业
     *
     * @param userId
     * @param enterpriseId
     */
    public void delFocusEnterprises(long userId, long enterpriseId) {
        enterpriseDao.deldelFocusEnterprises(userId,enterpriseId);

    }

    /**
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<UserPostRecordRes> getMyReceivePostsList(long userId, long offset, int count, boolean loadMore) {
        Enterprises enterprise = enterpriseDao.getEnterpriseByFounderUserId(userId);
        List<UserPostRecordRes> resList = new LinkedList<>();
        if (enterprise == null) {
            return resList;
        }
        long enterpriseId = enterprise.getEnterpriseId();
        List<UserJobPostRecordEntity>
                entities = jobDao.getReceivePostsEntityByEnterpriseId(enterpriseId, offset, count, loadMore);
        for (UserJobPostRecordEntity entity : entities) {
            long postUserId = entity.getUserId();
            Users postUser = userDao.getUserById(postUserId);
            if (postUser != null) {
                UserPostRecordRes res = wrapUserPostRecordRes(entity, postUser);
                resList.add(res);
            }
        }
        return resList;
    }

    /**
     * 打包UserPostRecordRes
     *
     * @param user
     * @param entity
     * @return
     */
    private UserPostRecordRes wrapUserPostRecordRes(UserJobPostRecordEntity entity, Users user) {
        String dateStr = CommonUtil.getDateStrByTime(entity.getCreatedOn().getTime());
        String statusStr = "";
        int status = entity.getStatus();
        List<UserPostRecordOptionRes> options = new LinkedList<>();
        UserPostRecordOptionRes tongzhimianshi = new UserPostRecordOptionRes
                (UserJobPostHandleOptionEnum.TONG_ZHI_MIAN_SHI.value(), "通知面试");
        UserPostRecordOptionRes buheshi = new UserPostRecordOptionRes
                (UserJobPostHandleOptionEnum.BU_HE_SHI.value(), "不合适");
        UserPostRecordOptionRes mianshitongguo = new UserPostRecordOptionRes
                (UserJobPostHandleOptionEnum.MIAN_SHI_TONG_GUO.value(), "面试通过");
        UserPostRecordOptionRes enterprisedel = new UserPostRecordOptionRes
                (UserJobPostHandleOptionEnum.ENTERPRISE_DEl.value(), "删除");
        if (status == UserJobPostStatusEnum.CHU_SHEN.value()) {
            statusStr = "初审中";
            options.add(tongzhimianshi);
        } else if (status == UserJobPostStatusEnum.BU_HE_SHI.value()) {
            statusStr = "不合适";
        } else if (status == UserJobPostStatusEnum.DELETE.value()) {
            statusStr = "已删除";
        } else if (status == UserJobPostStatusEnum.MIAN_SHI_TONG_GUO.value()) {
            statusStr = "面试通过";
        } else if (status == UserJobPostStatusEnum.TONG_ZHI_MIAN_SHI.value()) {
            statusStr = "通知面试";
            options.add(mianshitongguo);
        } else if (status == UserJobPostStatusEnum.YI_GUO_QI.value()) {
            statusStr = "已过期";
        }
        options.add(buheshi);
        options.add(enterprisedel);
        UserPostRecordRes res = new UserPostRecordRes(user.getUserId(),
                user.getGender(), user.getNickName(), entity.getJobId(), dateStr,
                entity.getUserJobPostRecord(), status,
                statusStr, entity.getJobName(), entity.getCreatedOn().getTime(), options);
        return res;
    }

    /**
     * 企业处理用户投递
     *
     * @param userId
     * @param userJobPostRecord
     * @param option
     * @param locale
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void handleUserJobPostRecord(long userId, long userJobPostRecord,
                                        int option, Locale locale) {
        Enterprises enterprises = enterpriseDao.getEnterpriseByFounderUserId(userId);
        if (enterprises == null) {
            throw new BusinessException(ErrorCode.NO_PERMISSION, locale);
        }
        UserJobPostRecord userJobPostRecordObject = jobDao.getUserJobPostRecordById(userJobPostRecord);

        if (userJobPostRecordObject == null) {
            throw new BusinessException(ErrorCode.USER_JOB_POST_RECORD_IS_NOT_EXIT, locale);
        }
        if (userJobPostRecordObject.getUserJobPostRecord()
                != userJobPostRecord) {
            throw new BusinessException(ErrorCode.NO_PERMISSION, locale);
        }
        userJobPostRecordObject.setModifiedBy(userId);
        userJobPostRecordObject.setModifiedOn(new Date());
        if (option == UserJobPostHandleOptionEnum.TONG_ZHI_MIAN_SHI.value()) {
            userJobPostRecordObject.setStatus(UserJobPostStatusEnum.TONG_ZHI_MIAN_SHI.value());

        } else if (option == UserJobPostHandleOptionEnum.BU_HE_SHI.value()) {
            userJobPostRecordObject.setStatus(UserJobPostStatusEnum.BU_HE_SHI.value());

        } else if (option == UserJobPostHandleOptionEnum.MIAN_SHI_TONG_GUO.value()) {
            userJobPostRecordObject.setStatus(UserJobPostStatusEnum.MIAN_SHI_TONG_GUO.value());

        } else if (option == UserJobPostHandleOptionEnum.ENTERPRISE_DEl.value()) {
            userJobPostRecordObject.setStatus(UserJobPostStatusEnum.BU_HE_SHI.value());
        }
        jobDao.saveUserJobPostRecord(userJobPostRecordObject);
    }

    /**
     * 删除职位
     *
     * @param userId
     * @param jobId
     * @param locale
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void delJobByJobId(long userId, long jobId, Locale locale) {
        long enterpriseId = getEnterpriseIdByFounderUserId(userId, locale);
        Jobs jobs = jobDao.getNormalJobById(jobId);
        if (jobs == null) {
            throw new BusinessException(ErrorCode.JOB_IS_NOT_EXIST, locale);
        }

        if (jobs.getEnterpriseId() != enterpriseId) {
            throw new BusinessException(ErrorCode.NO_PERMISSION, locale);
        }
        jobs.setModifiedBy(userId);
        jobs.setModifiedOn(new Date());
        jobs.setStatus(JobStatusEnum.DEL.value());
        jobDao.saveJob(jobs);
        List<UserJobPostRecord> list = jobDao.getUserJobPostRecordByJobId(jobId);
        for (UserJobPostRecord userJobPostRecord : list) {
            userJobPostRecord.setStatus(UserJobPostStatusEnum.YI_GUO_QI.value());
            userJobPostRecord.setModifiedOn(new Date());
            userJobPostRecord.setModifiedBy(userId);
            jobDao.saveUserJobPostRecord(userJobPostRecord);
        }
    }


}
