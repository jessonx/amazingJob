package com.xcm.dao;

import com.xcm.dao.hibernate.model.Resume;
import com.xcm.dao.hibernate.model.UserAuthorization;
import com.xcm.dao.hibernate.model.UserLabel;
import com.xcm.dao.hibernate.model.UserLoginHistory;
import com.xcm.dao.hibernate.model.Users;
import com.xcm.model.Enum.UserStatusEnum;
import com.xcm.model.key.OfflineMessageRedisKey;
import com.xcm.model.key.UserRedisKey;
import com.xcm.socket.body.model.NotificationModel;
import com.xcm.util.JsonUtil;
import com.xcm.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Repository
public class UserDao extends BaseDao {

    /**
     * 通过token查找User
     *
     * @param token
     * @return
     */
    public UserAuthorization getUserAuthorizationByTokenAndTokenType(String token, int tokenType) {
        Criteria criteria = getSession().createCriteria(UserAuthorization.class)
                .add(Restrictions.eq("token", token))
                .add(Restrictions.eq("tokenType", tokenType))
                .setMaxResults(1);
        return (UserAuthorization) criteria.uniqueResult();
    }

    /**
     * 通过userId得到UserAuthorization
     *
     * @param userId
     * @return
     */
    public UserAuthorization getUserAuthorizationByUserId(long userId) {
        Criteria criteria = getSession().createCriteria(UserAuthorization.class)
                .add(Restrictions.eq("userId", userId))
                .setMaxResults(1);
        return (UserAuthorization) criteria.uniqueResult();
    }

    /**
     * 获得用户登陆历史
     *
     * @param userId
     * @return
     */
    public UserLoginHistory getUserLoginHistoryByUserId(long userId) {
        Criteria criteria = getSession().createCriteria(UserLoginHistory.class)
                .add(Restrictions.eq("userId", userId))
                .setMaxResults(1);
        return (UserLoginHistory) criteria.uniqueResult();
    }

    /**
     * 保存用户登录记录
     *
     * @param userLoginHistory
     */
    public void saveUserLoginHistory(UserLoginHistory userLoginHistory) {
        getSession().save(userLoginHistory);
    }

    /**
     * 保存用户
     *
     * @param users
     */
    public void saveUser(Users users) {
        getSession().save(users);
    }

    /**
     * 保存用户userAuthorization
     *
     * @param userAuthorization
     */
    public void saveUserAuthorization(UserAuthorization userAuthorization) {
        getSession().save(userAuthorization);
    }

    /**
     * 通过UserId查找USER
     *
     * @param userId
     * @return
     */
    public Users getUserById(long userId) {
        Criteria criteria = getSession().createCriteria(Users.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("status", UserStatusEnum.NORMAL.value()))
                .setMaxResults(1);
        return (Users) criteria.uniqueResult();
    }

    /**
     * 通过userId和labelName查找UserLabel
     *
     * @param userId
     * @param labelName
     * @return
     */
    public UserLabel getLabelByUserIdAndLabelName(long userId, String labelName) {
        Criteria criteria = getSession().createCriteria(UserLabel.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("labelName", labelName))
                .add(Restrictions.eq("status", 0))
                .setMaxResults(1);
        Object object = criteria.uniqueResult();
        return null == object ? null : (UserLabel) object;
    }

    /**
     * 用户LabelNum++
     *
     * @param userId
     * @return
     */
    public int increaseUserLabelNum(long userId) {
        String key = String.format(UserRedisKey.USER_LABEL_COUNT, String.valueOf(userId));
        return Math.toIntExact(longValueOperations.increment(key, 1));
    }

    /**
     * 保存UserLabel
     *
     * @param userLabel
     */
    public void saveUserLabel(UserLabel userLabel) {
        getSession().save(userLabel);
    }

    /**
     * 通过userId和labelNum查找UserLabel
     *
     * @param userId
     * @param labelNum
     * @return
     */
    public UserLabel getLabelByUserIdAndLabelNum(long userId, int labelNum) {
        Criteria criteria = getSession().createCriteria(UserLabel.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("labelNum", labelNum))
                .add(Restrictions.eq("status", 0))
                .setMaxResults(1);
        Object object = criteria.uniqueResult();
        return null == object ? null : (UserLabel) object;
    }

    /**
     * 获得通知数量
     *
     * @param userId
     * @return
     */
    public long getNotificationCount(long userId) {
        String key = String.format(UserRedisKey.USER_NOTIFICATION_COUNT,
                String.valueOf(userId));
        return zSetOperations.zCard(key);
    }

    /**
     * 保存用户消息
     *
     * @param userId
     * @param notificationModel
     */
    public void saveNotificationByUserId(long userId,
                                         NotificationModel notificationModel) {
        String key = String.format(UserRedisKey.USER_NOTIFICATION_COUNT,
                String.valueOf(userId));
        Double score = Double.valueOf(System.currentTimeMillis());
        zSetOperations.add(key, JsonUtil.ObjectToJson(notificationModel), score);
    }

    /**
     * 拉取消息
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<Map<String, Object>> getNotificationListByUserId(long userId, long offset, long count, boolean loadMore) {
        String key = String.format(UserRedisKey.USER_NOTIFICATION_COUNT,
                String.valueOf(userId));
        Set<ZSetOperations.TypedTuple<String>> tuples = null;
        if (loadMore) {
            tuples = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(
                    key, 0, offset - 1, 0, count);
        } else {
            tuples = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(
                    key, 0, Long.MAX_VALUE, 0, count);
        }
        List<Map<String, Object>> list = new LinkedList<>();
        if (null != tuples) {
            for (ZSetOperations.TypedTuple<String> r : tuples) {
                Map<String, Object> map = new HashMap<>();
                String value = r.getValue();
                long score = r.getScore().longValue();
                map.put("value", value);
                map.put("score", score);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 删除
     *
     * @param userId
     * @param offset
     */
    public void delNotificationByOffset(long userId, long offset) {
        String key = String.format(UserRedisKey.USER_NOTIFICATION_COUNT,
                String.valueOf(userId));
        zSetOperations.removeRangeByScore(key, offset, offset);
    }

    /**
     * 获得消息数量
     *
     * @param toUserId
     * @return
     */
    public long getMessageCount(long toUserId) {
        String key2 = String.format(OfflineMessageRedisKey.OFFLINE_MESSAGE, String.valueOf(toUserId));
        Set<String> fromUserIds = setOperations.members(key2);
        long sum = 0;
        for (String fromUserId : fromUserIds) {
            String key = String.format(OfflineMessageRedisKey.OFFLINE_MESSAGE_FROM_TO,
                    String.valueOf(fromUserId), String.valueOf(toUserId));
            sum += zSetOperations.zCard(key);
        }

        return sum;
    }

    /**
     * 关注的职位数量
     *
     * @param userId
     * @return
     */
    public long getFocusJobsCount(long userId) {
        String key = String.format(UserRedisKey.USER_FOCUS_JOBS,
                String.valueOf(userId));
        return zSetOperations.zCard(key);
    }

    /**
     * 关注的企业
     *
     * @param userId
     * @return
     */
    public long getFocusEnterPrisesCount(long userId) {
        String key = String.format(UserRedisKey.USER_FOCUS_ENTERPRISES,
                String.valueOf(userId));
        return zSetOperations.zCard(key);
    }

    /**
     * 获得备点赞数
     *
     * @param userId
     * @return
     */
    public long getPraisedCount(long userId) {
        String key = String.format(UserRedisKey.USER_BE_PRAISED, String.valueOf(userId));
        return zSetOperations.zCard(key);
    }

    /**
     * 获得标签
     *
     * @param userId
     * @return
     */
    public List<UserLabel> getUserLabelsByUserId(long userId) {
        Criteria criteria = getSession().createCriteria(UserLabel.class)
                .add(Restrictions.eq("status", 0))
                .add(Restrictions.eq("userId", userId));
        return criteria.list();
    }

    /**
     * 获得点赞时间
     *
     * @param ownUserId
     * @param userId
     * @return
     */
    public double getScoreUserPraise(long ownUserId, long userId) {
        String key = String.format(UserRedisKey.USER_BE_PRAISED, String.valueOf(userId));
        Double score = zSetOperations.score(key, String.valueOf(ownUserId));
        return score == null ? 0 : score.doubleValue();
    }

    /**
     * 点赞
     *
     * @param ownUserId
     * @param userId
     */
    public void praiseUser(long ownUserId, long userId) {
        String key = String.format(UserRedisKey.USER_BE_PRAISED, String.valueOf(userId));
        double score = System.currentTimeMillis();
        zSetOperations.add(key, String.valueOf(ownUserId), score);
    }

    /**
     * 获得用户挂住企业的时间
     *
     * @param ownUserId
     * @param enterpriseId
     * @return
     */
    public double getScoreUserFocusEnterprise(long ownUserId, long enterpriseId) {
        String key = String.format(UserRedisKey.USER_FOCUS_ENTERPRISES, String.valueOf(ownUserId));
        Double score = zSetOperations.score(key, String.valueOf(enterpriseId));
        return score == null ? 0 : score.doubleValue();
    }

    /**
     * 添加用户关注企业（同时添加企业被关注）
     *
     * @param ownUserId
     * @param enterpriseId
     */
    public void addUserFocusEnterprise(long ownUserId, long enterpriseId) {
        String key = String.format(UserRedisKey.USER_FOCUS_ENTERPRISES, String.valueOf(ownUserId));
        double score = System.currentTimeMillis();
        zSetOperations.add(key, String.valueOf(enterpriseId), score);
        String key2 = String.format(UserRedisKey.ENTERPRISES_BE_FOCUSED, String.valueOf(enterpriseId));
        zSetOperations.add(key2, String.valueOf(ownUserId), score);
    }

    /**
     * 通过userId查找
     *
     * @param userId
     * @return
     */
    public Resume getNormalResumeByUserId(long userId) {
        Criteria criteria = getSession().createCriteria(Resume.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("status", 0));
        Object o = criteria.uniqueResult();
        return null == o ? null : (Resume) o;
    }

    /**
     * 保存简历
     *
     * @param resume
     */
    public void saveResume(Resume resume) {
        getSession().save(resume);
    }


}
