package com.xcm.dao;

import com.xcm.dao.hibernate.model.Jobs;
import com.xcm.dao.hibernate.model.UserJobPostRecord;
import com.xcm.model.Enum.JobStatusEnum;
import com.xcm.model.Enum.UserJobPostStatusEnum;
import com.xcm.model.key.FriendRedisKey;
import com.xcm.model.key.UserRedisKey;
import com.xcm.model.resource.entity.UserJobPostRecordEntity;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.sun.tools.xjc.reader.Ring.add;

/**
 * Created by 薛岑明 on 2017/3/16.
 */
@Repository
public class JobDao extends BaseDao {
    private static final int RECOMMEND_JOBS_COUNT = 5;

    /**
     * 推荐职位
     *
     * @return
     */
    public List<Jobs> getRecommendJobList() {
        Criteria criteria = getSession().createCriteria(Jobs.class)
                .addOrder(Order.desc("createdOn"))
                .setMaxResults(RECOMMEND_JOBS_COUNT);
        return criteria.list();
    }

    /**
     * 得到用户投递的记录
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<UserJobPostRecord> getUserPostJobs(long userId, long offset, int count, boolean loadMore) {
        Criteria criteria = getSession().createCriteria(UserJobPostRecord.class)
                .add(Restrictions.eq("userId", userId))
                .addOrder(Order.desc("modifiedOn"))
                .setMaxResults(count)
                .add(Restrictions.in("status", new Integer[]{
                        1, 2, 3, 4, 5
                }));
        if (loadMore) {
            criteria.add(Restrictions.lt("modifiedOn", new Date(offset)));
        }
        return criteria.list();

    }

    /**
     * 得到企业收到用户的投递
     *
     * @param enterpriseId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<UserJobPostRecordEntity> getReceivePostsEntityByEnterpriseId(long enterpriseId, long offset, int count, boolean loadMore) {
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
                "    ON ujpr.`jobId` = t.jobId ";
        //status (1,2,4)
        String subStr = "WHERE ujpr.`createdOn`<?";
        if (loadMore) {
            sql = sql + subStr + " AND ujpr.`status`IN (1,2,4) ORDER BY ujpr.`createdOn` DESC ";
        } else {
            sql = sql + "WHERE ujpr.`status`IN (1,2,4) ORDER BY ujpr.`createdOn` DESC ";
        }
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql)
                .setParameter(0, enterpriseId);
        if (loadMore) {
            query.setParameter(1, new Date(offset));
        }
        query.setMaxResults(count);
        query.addScalar("userId", StandardBasicTypes.LONG)
                .addScalar("jobId", StandardBasicTypes.LONG)
                .addScalar("jobName", StandardBasicTypes.STRING)
                .addScalar("userJobPostRecord", StandardBasicTypes.LONG)
                .addScalar("createdOn", StandardBasicTypes.TIMESTAMP)
                .addScalar("status", StandardBasicTypes.INTEGER)
                .addScalar("modifiedOn", StandardBasicTypes.TIMESTAMP)
                .setMaxResults(count);
        query.setResultTransformer(Transformers.aliasToBean(UserJobPostRecordEntity.class));
        return query.list();
    }

    /**
     * 得到用户关注的job
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<Map<String, Long>> getFocusJobsList(long userId, long offset, int count, boolean loadMore) {
        String key = String.format(UserRedisKey.USER_FOCUS_JOBS, String.valueOf(userId));
        return reverseRangeByScoreWithScoresByPaging(key, offset, count, loadMore, redisTemplate);
    }

    /**
     * 删除关注职位
     *
     * @param userId
     * @param jobId
     */
    public void delFocusJob(long userId, long jobId) {
        String key = String.format(UserRedisKey.USER_FOCUS_JOBS, String.valueOf(userId));
        zSetOperations.remove(key, String.valueOf(jobId));
    }

    /**
     * 通过id查找status正常的Jobs
     *
     * @param jobId
     * @return
     */
    public Jobs getNormalJobById(long jobId) {
        Criteria c = getSession().createCriteria(Jobs.class)
                .add(Restrictions.eq("jobId", jobId))
                .add(Restrictions.eq("status", JobStatusEnum.NORMAL.value()))
                .setMaxResults(1);
        Object o = c.uniqueResult();
        return null == o ? null : (Jobs) o;
    }

    /**
     * 搜索职位
     *
     * @param salaryType
     * @param locationType
     * @param educationType
     * @param experienceType
     * @param fieldType
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<Jobs> searchJobs(int salaryType, int locationType,
                                 int educationType, int experienceType,
                                 int fieldType, long offset, int count, boolean loadMore) {

        Criteria criteria = getSession().createCriteria(Jobs.class)
                .addOrder(Order.desc("modifiedOn"))
                .setMaxResults(count);
        if (loadMore) {
            criteria.add(Restrictions.lt("modifiedOn", new Date(offset)));
        }

        if (educationType == 0) {
            criteria.add(Restrictions.in("educationType", new Object[]{0, 1, 2, 3, 4}));
        } else {
            criteria.add(Restrictions.eq("educationType", educationType));
        }

        if (locationType == 0) {
            criteria.add(Restrictions.in("locationType", new Object[]{0, 1, 2, 3, 4}));
        } else {
            criteria.add(Restrictions.eq("locationType", locationType));
        }

        if (fieldType == 0) {
            criteria.add(Restrictions.in("fieldType", new Object[]{0, 1, 2, 3, 4}));
        } else {
            criteria.add(Restrictions.eq("fieldType", fieldType));
        }

        if (experienceType == 0) {
            criteria.add(Restrictions.in("experienceType", new Object[]{0, 1, 2, 3, 4}));
        } else {
            criteria.add(Restrictions.eq("experienceType", experienceType));
        }

        if (salaryType == 1) {
            criteria.add(Restrictions.between("salary", 0l, 3000l));
        } else if (salaryType == 2) {
            criteria.add(Restrictions.between("salary", 3000l, 5000l));
        } else if (salaryType == 3) {
            criteria.add(Restrictions.between("salary", 5000l, 10000l));
        } else if (salaryType == 4) {
            criteria.add(Restrictions.between("salary", 10000l, Long.MAX_VALUE));
        }

        return criteria.list();
    }

    /**
     * 全状态job
     *
     * @param jobId
     * @return
     */
    public Jobs getJobById(long jobId) {
        Criteria c = getSession().createCriteria(Jobs.class)
                .add(Restrictions.eq("jobId", jobId))
                .setMaxResults(1);
        Object o = c.uniqueResult();
        return null == o ? null : (Jobs) o;
    }

    /**
     * 查看user是否关注job
     *
     * @param userId
     * @param jobId
     */
    public double getScoreFocusByUserIdAndJobId(long userId, long jobId) {
        String key = String.format(UserRedisKey.USER_FOCUS_JOBS, String.valueOf(userId));
        Double score = zSetOperations.score(key, String.valueOf(jobId));
        return score == null ? 0 : score.doubleValue();
    }

    /**
     * 关注职位
     *
     * @param userId
     * @param jobId
     */
    public void focusJob(long userId, long jobId) {
        String key = String.format(UserRedisKey.USER_FOCUS_JOBS, String.valueOf(userId));
        double score = System.currentTimeMillis();
        zSetOperations.add(key, String.valueOf(jobId), score);
    }


    /**
     * 查询投递记录
     *
     * @param userId
     * @param jobId
     * @return
     */
    public UserJobPostRecord getUserJobPostRecordByUserIdAndJobId(long userId, long jobId) {
        Criteria c = getSession().createCriteria(UserJobPostRecord.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("jobId", jobId))
                .setMaxResults(1);
        Object o = c.uniqueResult();
        return o == null ? null : (UserJobPostRecord) o;
    }

    /**
     * 保存UserJobPostRecord
     *
     * @param record
     */
    public void saveUserJobPostRecord(UserJobPostRecord record) {
        getSession().save(record);
    }

    /**
     * 投递的职位总数
     *
     * @param userId
     * @return
     */
    public long getPostJobsCount(long userId) {
        Object[] arr = new Object[]{1, 2, 3, 4, 5};
        Criteria criteria = getSession().createCriteria(UserJobPostRecord.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.in("status", arr));
        criteria = criteria.setProjection(Projections.rowCount());
        long totalCount =
                (long) criteria.uniqueResult();
        return totalCount;
    }

    /**
     * 保存职位
     *
     * @param job
     */
    public void saveJob(Jobs job) {
        getSession().save(job);
    }

    /**
     * 分页获得jobs
     *
     * @param enterpriseId
     * @param offset
     * @param count
     * @param loadMore     @return
     */
    public List<Jobs> getJobsByEnterpriseIdWithPage(long enterpriseId, long offset, int count, boolean loadMore) {
        Criteria criteria = getSession().createCriteria(Jobs.class)
                .add(Restrictions.eq("enterpriseId", enterpriseId))
                .addOrder(Order.desc("modifiedOn"))
                .add(Restrictions.eq("status", JobStatusEnum.NORMAL.value()))
                .setMaxResults(count);
        if (loadMore) {
            criteria.add(Restrictions.lt("modifiedOn", new Date(offset)));
        }
        return criteria.list();
    }

    public List<Jobs> getNormalJobsByEnterpriseId(long enterpriseId) {
        Criteria criteria = getSession().createCriteria(Jobs.class)
                .add(Restrictions.eq("enterpriseId", enterpriseId))
                .addOrder(Order.desc("modifiedOn"))
                .add(Restrictions.eq("status", 0));
        return criteria.list();
    }

    /**
     * 得到在招职位
     *
     * @param enterpriseId
     * @return
     */
    public long getNormalJobsCount(long enterpriseId) {
        Criteria criteria = getSession().createCriteria(Jobs.class)
                .add(Restrictions.eq("enterpriseId", enterpriseId))
                .addOrder(Order.desc("modifiedOn"))
                .add(Restrictions.eq("status", 0));
        criteria = criteria.setProjection(Projections.rowCount());
        long totalCount =
                (long) criteria.uniqueResult();
        return totalCount;
    }

    /**
     * @param userJobPostRecord
     * @return
     */
    public UserJobPostRecord getUserJobPostRecordById(long userJobPostRecord) {
        Object[] arr = new Object[]{1, 2, 4};

        Criteria criteria = getSession().createCriteria(UserJobPostRecord.class)
                .add(Restrictions.eq("userJobPostRecord", userJobPostRecord))
                .add(Restrictions.in("status", arr));
        Object object = criteria.uniqueResult();
        return null == object ? null : (UserJobPostRecord) object;
    }

    /**
     * 通过JobId得到UserJobPostRecord List
     *
     * @param jobId
     * @return
     */
    public List<UserJobPostRecord> getUserJobPostRecordByJobId(long jobId) {
        Object[] arr = new Object[]{1, 2, 3, 4, 5};
        Criteria criteria = getSession().createCriteria(UserJobPostRecord.class)
                .add(Restrictions.eq("jobId", jobId))
                .add(Restrictions.in("status", arr));

        return criteria.list();
    }

    /**
     * 通过JobId得到UserJobPostRecord (获得其他应聘者)
     *
     * @param jobId
     * @param offset
     * @param count
     * @param loadMore
     */
    public List<UserJobPostRecord> getUserJobPostRecordByJobIdLoadMore(long jobId, long offset, int count, boolean loadMore) {
        Criteria criteria = getSession().createCriteria(UserJobPostRecord.class)
                .add(Restrictions.eq("jobId", jobId))
                .setMaxResults(count)
                .addOrder(Order.desc("modifiedOn"));
        if (loadMore) {
            criteria.add(Restrictions.lt("modifiedOn", new Date(offset)));
        }
        return criteria.list();
    }


}
