package com.xcm.dao;

import com.xcm.dao.hibernate.model.EnterpriseImage;
import com.xcm.dao.hibernate.model.Enterprises;
import com.xcm.dao.hibernate.model.Jobs;
import com.xcm.dao.hibernate.model.UserJobPostRecord;
import com.xcm.model.key.UserRedisKey;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 薛岑明 on 2017/3/16.
 */
@Repository
public class EnterpriseDao extends BaseDao {
    /**
     * 通过创建者enterpriseId得到Enterprises
     *
     * @param enterpriseId
     * @return
     */
    public Enterprises getNormalEnterpriseById(long enterpriseId) {
        Criteria criteria = getSession().createCriteria(Enterprises.class)
                .add(Restrictions.eq("enterpriseId", enterpriseId))
                .add(Restrictions.eq("status", 0))
                .setMaxResults(1);
        Object o = criteria.uniqueResult();
        return null == o ? null : (Enterprises) o;
    }

    /**
     * 通过创建者userid得到Enterprises
     *
     * @param userId
     * @return
     */
    public Enterprises getEnterpriseByFounderUserId(long userId) {
        Criteria criteria = getSession().createCriteria(Enterprises.class)
                .add(Restrictions.eq("founderUserId", userId))
                .add(Restrictions.eq("status", 0))
                .setMaxResults(1);
        Object o = criteria.uniqueResult();
        return null == o ? null : (Enterprises) o;


    }

    /**
     * 保存enterprise
     *
     * @param enterprise
     */
    public void saveEnterprise(Enterprises enterprise) {
        getSession().save(enterprise);
    }

    public long getEnterpriseFocusCount(long enterpriseId) {
        String key = String.format(UserRedisKey.ENTERPRISES_BE_FOCUSED, String.valueOf(enterpriseId));
        return zSetOperations.zCard(key);
    }

    /**
     * 通过enterpriseId查找EnterpriseImage
     *
     * @param enterpriseId
     * @return
     */
    public List<EnterpriseImage> getEnterpriseImageListByEnterpriseIdId(long enterpriseId) {
        Criteria criteria = getSession().createCriteria(EnterpriseImage.class)
                .add(Restrictions.eq("enterpriseId", enterpriseId))
                .add(Restrictions.eq("status", 0));
        return criteria.list();
    }

    /**
     * 得到关注的企业
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<Map<String, Long>> getFocusEnterprisesList(long userId, long offset, int count, boolean loadMore) {
        String key = String.format(UserRedisKey.USER_FOCUS_ENTERPRISES, String.valueOf(userId));
        return reverseRangeByScoreWithScoresByPaging(key, offset, count, loadMore, redisTemplate);
    }

    /**
     * 删除关注企业
     *
     * @param userId
     * @param enterpriseId
     */
    public void deldelFocusEnterprises(long userId, long enterpriseId) {
        String key = String.format(UserRedisKey.USER_FOCUS_ENTERPRISES, String.valueOf(userId));
        zSetOperations.remove(key, String.valueOf(enterpriseId));
    }

    /**
     * 获得发布的职位
     *
     * @param enterpriseId
     * @return
     */
    public long getPublishJobsCount(long enterpriseId) {
        Criteria criteria = getSession().createCriteria(Jobs.class)
                .add(Restrictions.eq("status", 0))
                .add(Restrictions.eq("enterpriseId", enterpriseId));
        criteria = criteria.setProjection(Projections.rowCount());
        long totalCount =
                (long) criteria.uniqueResult();
        return totalCount;
    }

    /**
     * 获得收到的简历
     *
     * @param enterpriseId
     * @return
     */
    public long getReceiveResumesCount(long enterpriseId) {
        String sql = "SELECT " +
                "  t.jobId," +
                "  t.jobName," +
                "  ujpr.`userJobPostRecord`," +
                "  ujpr.`createdOn`," +
                "  ujpr.`status`," +
                "  ujpr.`modifiedOn`, " +
                "  ujpr.`userId` " +
                "FROM" +
                "  (SELECT " +
                "    j.`jobId` AS jobId," +
                "    j.`name` AS jobName " +
                "  FROM" +
                "    jobs AS j " +
                "    INNER JOIN enterprises AS e " +
                "      ON j.`enterpriseId` = e.`enterpriseId` " +
                "  WHERE j.`enterpriseId` = ? " +
                "    AND j.`status` = 0) AS t " +
                "  INNER JOIN user_job_post_record AS ujpr " +
                "    ON ujpr.`jobId` = t.jobId " +
                "WHERE ujpr.`status`IN (1,2,4)";
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql)
                .setParameter(0, enterpriseId);
        return query.list().size();
    }

    /**
     * 保存enterpriseImage
     *
     * @param enterpriseImage
     */
    public void saveEnterpriseImage(EnterpriseImage enterpriseImage) {
        getSession().save(enterpriseImage);
    }


}
