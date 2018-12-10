package com.xcm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

public class BaseDao {
    @Resource(name = "redisTemplate")
    protected SetOperations<String, String> setOperations;
    @Resource(name = "redisTemplate")
    protected ZSetOperations<String, String> zSetOperations;
    @Resource(name = "redisTemplate")
    protected RedisOperations<String, String> redisOperations;
    @Resource(name = "redisTemplate")
    protected HashOperations<String, String, String> hashOperations;
    @Resource(name = "redisTemplate")
    protected ValueOperations<String, String> stringValueOperations;
    @Resource(name = "redisTemplate")
    protected ListOperations<String, String> listOperations;
    @Resource(name = "redisTemplate")
    protected ValueOperations<String, Long> longValueOperations;
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected String formatToExcludeCurrent(double score) {
        return String.format("(%s", score);
    }


    public void saveZSetWithCount(final String key, final byte[] value,
                                  final long score, long count,
                                  RedisTemplate<String, String> redisTemplate) {
        redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.zAdd(key.getBytes(), score, value);
                return null;
            }
        });
        Long result = redisTemplate.opsForZSet().zCard(key);
        long lCount = result == null ? 0 : result.longValue();
        if (lCount > count) {
            redisTemplate.opsForZSet().removeRange(key, 0, result - count - 1);
        }
    }

    public List<Map<String, Long>> reverseRangeByScoreWithScoresByPaging(
            String key, long offset, long count, boolean loadMore,
            RedisTemplate<String, String> redisTemplate) {
        List<Map<String, Long>> list = new ArrayList<Map<String, Long>>();
        Set<TypedTuple<String>> result = null;
        if (loadMore) {
            result = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(
                    key, 0, offset - 1, 0, count);
        } else {
            result = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(
                    key, 0, Long.MAX_VALUE, 0, count);
        }
        if (result != null) {
            for (TypedTuple<String> r : result) {
                Map<String, Long> map = new HashMap<String, Long>();
                long value = Long.valueOf(r.getValue()).longValue();
                long score = r.getScore().longValue();
                map.put("value", value);
                map.put("score", score);
                list.add(map);
            }
        }
        return list;
    }

    public List<Map<String, Long>> reverseRangeWithScoresByPaging(String key,
                                                                  long offset, long count, boolean loadMore) {
        List<Map<String, Long>> list = new ArrayList<Map<String, Long>>();
        Set<TypedTuple<String>> tuples = null;
        if (!loadMore) {
            tuples = zSetOperations.reverseRangeWithScores(key, 0, count - 1);

        } else {
            tuples = zSetOperations.reverseRangeWithScores(key, offset,
                    offset + count - 1);
        }
        if (null != tuples) {
            for (TypedTuple<String> r : tuples) {
                Map<String, Long> map = new HashMap<String, Long>();
                long value = new Long(r.getValue()).longValue();
                long score = r.getScore().longValue();
                long rank = zSetOperations.reverseRank(key,
                        String.valueOf(value));
                map.put("value", value);
                map.put("score", score);
                // redis排名从0开始,出去显示要加上一位
                map.put("rank", rank + 1);
                list.add(map);
            }
        }
        return list;

    }
}
