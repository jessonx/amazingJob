package com.xcm.business;

import com.xcm.dao.EnterpriseDao;
import com.xcm.dao.FriendDao;
import com.xcm.dao.JobDao;
import com.xcm.dao.UserDao;
import com.xcm.dao.hibernate.model.Enterprises;
import com.xcm.dao.hibernate.model.Resume;
import com.xcm.dao.hibernate.model.UserLabel;
import com.xcm.dao.hibernate.model.Users;
import com.xcm.model.Enum.GenderEnum;
import com.xcm.model.Enum.ImageTypeEnum;
import com.xcm.model.Enum.UserTypeEnum;
import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.FocusRes;
import com.xcm.model.resource.LabelRes;
import com.xcm.model.resource.LockRes;
import com.xcm.model.resource.NotificationRes;
import com.xcm.model.resource.ResumeRes;
import com.xcm.model.resource.UserBasicRes;
import com.xcm.model.resource.UserProfileDetailRes;
import com.xcm.socket.MessageCenter;
import com.xcm.socket.body.model.NotificationModel;
import com.xcm.socket.body.model.PrivateMessage;
import com.xcm.socket.core.model.SocketRequest;
import com.xcm.util.CommonUtil;
import com.xcm.util.JsonUtil;
import com.xcm.util.ResourceUtil;
import com.xcm.util.StringUtil;
import com.xcm.webSocket.commad.OfflineMsgType;
import com.xcm.webSocket.commad.SocketId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class UserBusiness {
    @Autowired
    private UserDao userDao;
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private MessageCenter messageCenter;
    @Autowired
    private EnterpriseDao enterpriseDao;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file, String path, ImageTypeEnum imageEnum) {
        if (file == null) {
            throw new BusinessException(ErrorCode.FILE_CANNOT_BE_NULL,
                    Locale.CHINA);
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new BusinessException(ErrorCode.FILE_NAME_CANNOT_BE_NULL,
                    Locale.CHINA);
        }
        Date date = new Date();
        String newFileName = date.getTime() + fileName;
        path = path + newFileName;
        File newFile = new File(path);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        if (imageEnum != null) {
            if (imageEnum == ImageTypeEnum.USER_HEADER_THUMB) {
                return "../users/headerThumb/" + newFileName;
            } else if (imageEnum == ImageTypeEnum.ENTERPRISE_IMAGE) {
                return "../enterprises/icon/" + newFileName;
            }
        }
        return "";
    }

    /**
     * 上传头像
     *
     * @param file
     */
    public String uploadHeaderThumb(MultipartFile file) {
        String path =
                "G:\\job-offer-system\\job-offer-system\\src\\main\\webapp\\users\\headerThumb\\";
        String filePath = uploadFile(file, path, ImageTypeEnum.USER_HEADER_THUMB);
        return filePath;
    }

    /**
     * 上传企业icon
     *
     * @param file
     */
    public String uploadEnterpriseIcon(MultipartFile file) {
        String path =
                "G:\\job-offer-system\\job-offer-system\\src\\main\\webapp\\enterprises\\icon\\";
        String filePath = uploadFile(file, path, ImageTypeEnum.ENTERPRISE_IMAGE);
        return filePath;
    }

    /**
     * 设置用户资料
     *
     * @param userId
     * @param headerThumb
     * @param nickName
     * @param gender
     * @param description
     * @param education
     * @param location
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public int setUserProfile(long userId, String headerThumb, String nickName,
                              int gender, String description, String education, String location) {
        Users user = userDao.getUserById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_IS_NOT_EXIST, Locale.CHINA);
        }
        user.setModifiedOn(new Date());
        user.setModifiedBy(userId);
        user.setNickName(nickName);
        user.setHeaderThumb(headerThumb);
        user.setGender(gender);
        user.setDescription(description);
        user.setEducation(education);
        user.setLocation(location);
        user.setInfoVerified(true);
        userDao.saveUser(user);
        return user.getUserType();
    }

    /**
     * 添加标签
     *
     * @param userId
     * @param labelName
     * @param locale
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public LabelRes addUserLabel(long userId, String labelName, Locale locale) {
        UserLabel userLabelSearch = userDao.getLabelByUserIdAndLabelName(userId, labelName);
        if (userLabelSearch != null) {
            throw new BusinessException(ErrorCode.LABEL_NAME_REPEATED, locale);
        }
        Date date = new Date();
        int labelNum = userDao.increaseUserLabelNum(userId);
        UserLabel addLabel = new UserLabel(userId, labelNum, labelName, 0, userId, date, userId, date);
        userDao.saveUserLabel(addLabel);
        return new LabelRes(labelNum, labelName);
    }

    /**
     * 删除标签
     *
     * @param userId
     * @param labelNum
     * @param locale
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void deleteUserLabel(long userId, int labelNum, Locale locale) {
        UserLabel userLabel = userDao.getLabelByUserIdAndLabelNum(userId, labelNum);
        if (userLabel != null) {
            userLabel.setStatus(1);
            userLabel.setModifiedOn(new Date());
            userLabel.setModifiedBy(userId);
            userDao.saveUserLabel(userLabel);
        }
    }

    /**
     * 获得用户基本信息
     *
     * @param userId
     * @return
     */
    public UserBasicRes getUserBasicInfo(long userId) {
        Users users = userDao.getUserById(userId);
        UserBasicRes res = new UserBasicRes();
        if (users != null) {
            res = ResourceUtil.changeUserToUserBasicRes(users);
            res.setNotificationCount(userDao.getNotificationCount(userId));
            res.setMessageCount(userDao.getMessageCount(userId));
            res.setProfileInfoVerified(users.isInfoVerified());
            if (users.getUserType() == UserTypeEnum.ENTERPRISE.value()) {
                Enterprises enterprises = enterpriseDao.getEnterpriseByFounderUserId(userId);
                if (enterprises == null) {
                    res.setEnterpriseInfoVerified(false);
                } else {
                    res.setEnterpriseInfoVerified(true);
                }
            }
        }
        return res;
    }

    /**
     * 获得用户的关注信息
     *
     * @param userId
     * @return
     */
    public FocusRes getUserFocusRes(long userId) {
        Users user = userDao.getUserById(userId);
        int userType = UserTypeEnum.PERSONAL.value();
        if (user != null) {
            userType = user.getUserType();
        }
        long focusJobsCount = 0;
        long postJobsCount = 0;
        long focusEnterPrisesCount = 0;
        long friendsCount = 0;
        long receiveResumesCount = 0;
        long publishJobsCount = 0;
        long recentContactsCount = 0;
        friendsCount = friendDao.getFriendsCount(userId);

        if (userType == UserTypeEnum.PERSONAL.value()) {
            focusJobsCount = userDao.getFocusJobsCount(userId);
            postJobsCount = jobDao.getPostJobsCount(userId);
            focusEnterPrisesCount = userDao.getFocusEnterPrisesCount(userId);
        } else if (userType == UserTypeEnum.ENTERPRISE.value()) {
            Enterprises enterprises = enterpriseDao.getEnterpriseByFounderUserId(userId);
            if (enterprises != null) {
                long enterpriseId = enterprises.getEnterpriseId();
                receiveResumesCount = enterpriseDao.getReceiveResumesCount(enterpriseId);
                publishJobsCount = enterpriseDao.getPublishJobsCount(enterpriseId);
                recentContactsCount = friendDao.getRecentContactsCount(userId);
            }
        }


        return new FocusRes(recentContactsCount, publishJobsCount, receiveResumesCount, userId, userType, focusJobsCount, postJobsCount, focusEnterPrisesCount, friendsCount);
    }


    public UserProfileDetailRes getUserProfileDetailRes(long userId) {
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
            LabelRes res =
                    new LabelRes(label.getLabelNum(), label.getLabelName());
            resList.add(res);
        }
        long praisedCount = userDao.getPraisedCount(userId);
        long friendsCount = friendDao.getFriendsCount(userId);
        UserProfileDetailRes detailRes = new UserProfileDetailRes(userId
                , praisedCount, friendsCount, headerThumb, gender,
                education, location, resList, description, nickName);
        return detailRes;
    }

    /**
     * 点赞用户
     *
     * @param ownUserId
     * @param userId
     * @param locale
     */
    public void praiseUserByUserId(long ownUserId, long userId, Locale locale) {
        double score = userDao.getScoreUserPraise(ownUserId, userId);
        if (score != 0) {
            throw new BusinessException(ErrorCode.REPEATED_USER_PRAISE, locale);
        }
        userDao.praiseUser(ownUserId, userId);
    }

    /**
     * 关注企业
     *
     * @param ownUserId
     * @param enterpriseId
     * @param locale
     */
    public void focusEnterprise(long ownUserId, long enterpriseId, Locale locale) {
        double score = userDao.getScoreUserFocusEnterprise(ownUserId, enterpriseId);
        if (score != 0) {
            throw new BusinessException(ErrorCode.REPEATED_ENTERPRISE_FOCUS, locale);
        }
        userDao.addUserFocusEnterprise(ownUserId, enterpriseId);
    }

    /**
     * 添加用户最近访客
     *
     * @param userId
     * @param ownUserId
     */
    public void addUserRecentVisitor(long userId, long ownUserId) {
        friendDao.addUserRecentVisitor(userId, ownUserId);
    }

    /**
     * @param userId
     * @param realName
     * @param birthDay
     * @param employmentLength
     * @param phoneNum
     * @param email
     * @param schoolName
     * @param major
     * @param schoolTime
     * @param workExperience
     * @param projectExperience
     * @param certificate
     * @param description
     * @param locale
     * @param highestEducation
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void setMyResume(long userId, String realName, String birthDay,
                            String employmentLength, String phoneNum, String email,
                            String schoolName, String major, String schoolTime,
                            String workExperience, String projectExperience,
                            String certificate, String description, Locale locale, String highestEducation) {
        if (!StringUtil.isTelStr(phoneNum)) {
            throw new BusinessException(ErrorCode.TEL_NUM_FORM_ERROR, locale);
        }
        if (!StringUtil.isMailStr(email)) {
            throw new BusinessException(ErrorCode.EMAIL_FORM_ERROR, locale);
        }
        Resume resume = userDao.getNormalResumeByUserId(userId);
        Date date = new Date();
        if (resume == null) {
            resume = new Resume(userId, realName, birthDay,
                    employmentLength, phoneNum, email,
                    schoolName, major, schoolTime, workExperience,
                    projectExperience, certificate, description, 0, userId, date, userId, date, highestEducation);
        }
        resume.setRealName(realName);
        resume.setBirthDay(birthDay);
        resume.setEmploymentLength(employmentLength);
        resume.setPhoneNum(phoneNum);
        resume.setEmail(email);
        resume.setSchoolName(schoolName);
        resume.setMajor(major);
        resume.setSchoolTime(schoolTime);
        resume.setWorkExperience(workExperience);
        resume.setProjectExperience(projectExperience);
        resume.setDescription(description);
        resume.setModifiedOn(date);
        resume.setModifiedBy(userId);
        resume.setHighestEducation(highestEducation);
        userDao.saveResume(resume);
    }

    /**
     * @param userId
     * @return
     */
    public ResumeRes getResumeResByUserId(long userId) {
        Users user = userDao.getUserById(userId);
        ResumeRes res = new ResumeRes();
        if (user == null) {
            return res;
        }
        Resume resume = userDao.getNormalResumeByUserId(userId);
        if (resume == null) {
            return res;
        }
        res = wrapResumeRes(resume, user);
        return res;
    }

    private ResumeRes wrapResumeRes(Resume resume, Users user) {
        ResumeRes res = new ResumeRes(resume.getResumeId(), user.getUserId(),
                user.getGender(), user.getLocation(),
                resume.getRealName(), resume.getBirthDay(),
                resume.getEmploymentLength(), resume.getPhoneNum(),
                resume.getEmail(), resume.getSchoolName(),
                resume.getMajor(), resume.getSchoolTime(),
                resume.getWorkExperience(), resume.getProjectExperience(),
                resume.getCertificate(), resume.getDescription(),
                user.getHeaderThumb(), resume.getHighestEducation());
        return res;
    }


    /**
     * 拉去通知列表
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<NotificationRes> getNotificationListByUserId(long userId,
                                                             long offset, int count, boolean loadMore) {
        List<NotificationRes> resList = new LinkedList<>();
        List<Map<String, Object>> list = userDao.getNotificationListByUserId(userId, offset, count, loadMore);
        for (Map<String, Object> map : list) {
            NotificationModel notificationModel = JsonUtil.StringToObject((String) map.get("value"),
                    NotificationModel.class);
            long thisOffset = (Long) map.get("score");
            String time = CommonUtil.getDateStrByTime(thisOffset);
            NotificationRes res = new NotificationRes(notificationModel.getTitle(),
                    notificationModel.getBody(), notificationModel.getLocation(), time, thisOffset);
            resList.add(res);
        }
        return resList;
    }

    /**
     * 锁屏界面出现的
     *
     * @param userId
     * @return
     */
    public LockRes getLockResByUserId(long userId) {
        LockRes res = new LockRes();
        Users users = userDao.getUserById(userId);
        if (users != null) {
            res = new LockRes(users.getNickName(), users.getHeaderThumb());
        }
        return res;
    }

    /**
     * 删除通知
     *
     * @param userId
     * @param offset
     * @param locale
     * @return
     */
    public void delNotification(long userId, long offset, Locale locale) {
        userDao.delNotificationByOffset(userId, offset);
    }
}
