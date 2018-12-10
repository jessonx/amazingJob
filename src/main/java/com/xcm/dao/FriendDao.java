package com.xcm.dao;

import com.xcm.dao.hibernate.model.FriendRequest;
import com.xcm.model.Enum.FriendRequestStatusEnum;
import com.xcm.model.key.FriendRedisKey;
import com.xcm.model.resource.tuple.TwoTuple;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 薛岑明 on 2017/3/16.
 */
@Repository
public class FriendDao extends BaseDao {

    /**
     * 最近浏览者
     *
     * @param userId
     * @param recentVisitorCount
     * @return
     */
    public List<TwoTuple<Long, Long>> getRecentVisitorByUserId(long userId, int recentVisitorCount) {
        String key = String.format(FriendRedisKey.USER_RECENT_VISITOR,
                String.valueOf(userId));
        Set<ZSetOperations.TypedTuple<String>> set = zSetOperations.
                reverseRangeByScoreWithScores(key, 0, Long.MAX_VALUE, 0, recentVisitorCount);
        List<TwoTuple<Long, Long>> list = new LinkedList<>();
        if (set != null) {
            for (ZSetOperations.TypedTuple<String> tuple : set) {
                Long visitorUserId = Long.valueOf(tuple.getValue());
                Long time = tuple.getScore().longValue();
                list.add(new TwoTuple<>(visitorUserId, time));
            }
        }
        return list;
    }

    /**
     * 好友数
     *
     * @param userId
     * @return
     */
    public long getFriendsCount(long userId) {
        String key = String.format(FriendRedisKey.USER_FRIEND, String.valueOf(userId));
        return zSetOperations.zCard(key);
    }

    /**
     * 好友列表
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     */
    public List<Map<String, Long>> getFriendList(long userId, long offset, int count, boolean loadMore) {
        String key = String.format(FriendRedisKey.USER_FRIEND, String.valueOf(userId));
        return reverseRangeByScoreWithScoresByPaging(key, offset, count, loadMore, redisTemplate);
    }

    /**
     * 得到未处理的好友请求
     *
     * @param fromUserId
     * @param toUserId
     * @return
     */
    public FriendRequest getPendingFriendRequest(long fromUserId, long toUserId) {
        Criteria criteria = getSession().createCriteria(FriendRequest.class)
                .add(Restrictions.eq("fromUserId", fromUserId))
                .add(Restrictions.eq("toUserId", toUserId))
                .add(Restrictions.eq("status", FriendRequestStatusEnum.PENDING.value()))
                .setMaxResults(1);
        Object o = criteria.uniqueResult();
        return null == o ? null : (FriendRequest) o;
    }

    /**
     * 保存好友请求
     *
     * @param friendRequest
     */
    public void saveFriendRequest(FriendRequest friendRequest) {
        getSession().save(friendRequest);
    }

    /**
     * 移除好友
     *
     * @param ownUserId
     * @param userId
     */
    public void removeFriend(long ownUserId, long userId) {
        String key1 = String.format(FriendRedisKey.USER_FRIEND, String.valueOf(ownUserId));
        String key2 = String.format(FriendRedisKey.USER_FRIEND, String.valueOf(userId));
        zSetOperations.remove(key1, String.valueOf(userId));
        zSetOperations.remove(key2, String.valueOf(ownUserId));

    }

    /**
     * 通过id得到未处理的好友请求
     *
     * @param friendRequestId
     * @return
     */
    public FriendRequest getPendingFriendRequestById(long friendRequestId) {
        Criteria criteria = getSession().createCriteria(FriendRequest.class)
                .add(Restrictions.eq("friendRequestId", friendRequestId))
                .setMaxResults(1);
        Object o = criteria.uniqueResult();
        return null == o ? null : (FriendRequest) o;
    }

    /**
     * 添加好友
     *
     * @param fromUserId
     * @param toUserId
     */
    public void addFriend(long fromUserId, long toUserId) {
        String key1 = String.format(FriendRedisKey.USER_FRIEND, String.valueOf(fromUserId));
        String key2 = String.format(FriendRedisKey.USER_FRIEND, String.valueOf(toUserId));
        Double score = (double) new Date().getTime();
        zSetOperations.add(key1, String.valueOf(toUserId), score);
        zSetOperations.add(key2, String.valueOf(fromUserId), score);
    }

    /**
     * 未处理的好友请求列表
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<FriendRequest> getPendingFriendRequestList(long userId,
                                                           long offset, int count, boolean loadMore) {
        Criteria criteria = getSession().createCriteria(FriendRequest.class)
                .add(Restrictions.eq("toUserId", userId))
                .addOrder(Order.desc("createdOn"))
                .add(Restrictions.eq("status", 0))
                .setMaxResults(count);
        if (loadMore) {
            criteria.add(Restrictions.lt("createdOn", new Date(offset)));
        }
        return criteria.list();
    }

    /**
     * 最近联系人数据源
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<Map<String, Long>> getRecentContactList(long userId, long offset, int count, boolean loadMore) {
        String key = String.format(FriendRedisKey.USER_RECENT_CONTACT, String.valueOf(userId));
        return reverseRangeByScoreWithScoresByPaging(key, offset, count, loadMore, redisTemplate);
    }

    /**
     * 获得最近联系人数
     *
     * @param userId
     * @return
     */
    public long getRecentContactsCount(long userId) {
        String key = String.format(FriendRedisKey.USER_RECENT_CONTACT, String.valueOf(userId));
        return zSetOperations.zCard(key);
    }

    /**
     * 添加最近联系人
     *
     * @param fromUserId
     * @param toUserId
     */
    public void addRecentContact(long fromUserId, long toUserId) {
        String key1 = String.format(FriendRedisKey.USER_RECENT_CONTACT, String.valueOf(fromUserId));
        String key2 = String.format(FriendRedisKey.USER_RECENT_CONTACT, String.valueOf(toUserId));
        Double score = (double) new Date().getTime();
        zSetOperations.add(key1, String.valueOf(toUserId), score);
        zSetOperations.add(key2, String.valueOf(fromUserId), score);

    }

    /**
     * 添加用户最近访客
     *
     * @param userId
     * @param ownUserId
     */
    public void addUserRecentVisitor(long userId, long ownUserId) {
        if (userId != ownUserId) {
            String key = String.format(FriendRedisKey.USER_RECENT_VISITOR,
                    String.valueOf(userId));
            Double score = (double) new Date().getTime();
            zSetOperations.add(key, String.valueOf(ownUserId), score);
        }
    }

}
