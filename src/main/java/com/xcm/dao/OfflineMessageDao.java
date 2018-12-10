package com.xcm.dao;

import com.xcm.model.key.OfflineMessageRedisKey;
import com.xcm.socket.body.model.PrivateMessage;
import com.xcm.socket.core.model.SocketResponse;
import com.xcm.util.JsonUtil;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 薛岑明 on 2017/4/3.
 */
@Repository
public class OfflineMessageDao extends BaseDao {
    /**
     * 離綫消息
     *
     * @param toUserId
     * @param toUserId
     * @return
     */
    public Set<String> getOfflineMessageByUserId(long toUserId, long fromUserId) {
        String key = String.format(OfflineMessageRedisKey.OFFLINE_MESSAGE_FROM_TO,
                String.valueOf(fromUserId), String.valueOf(toUserId));
        Set<String> set = zSetOperations.rangeByScore(key, 0, Long.MAX_VALUE);
        return set;
    }

    /**
     * 保存离线消息，只有私信保存
     *
     * @param <T>
     * @param fromUserId
     * @param toUserId
     * @param response
     */
    public <T> void saveOffLineMessage(long fromUserId, long toUserId, SocketResponse<PrivateMessage> response, String messageId) {

        String key = String.format(OfflineMessageRedisKey.OFFLINE_MESSAGE_FROM_TO,
                String.valueOf(fromUserId), String.valueOf(toUserId));
        Double score = Double.valueOf(messageId);
        String objStr = JsonUtil.ObjectToJson(response);
        zSetOperations.add(key, objStr, score);
        String key2 = String.format(OfflineMessageRedisKey.OFFLINE_MESSAGE, String.valueOf(toUserId));
        setOperations.add(key2, String.valueOf(fromUserId));
    }

    /**
     * 删除消息
     *
     * @param fromUserId
     * @param toUserId
     * @param messageId
     */
    public void delOfflineMsgBymessageIdAndUserId(long fromUserId, long toUserId, String messageId) {
        String key = String.format(OfflineMessageRedisKey.OFFLINE_MESSAGE_FROM_TO,
                String.valueOf(fromUserId), String.valueOf(toUserId));
        Double score = Double.valueOf(messageId);
        //删除消息
        zSetOperations.removeRangeByScore(key, score, score);
        String key2 = String.format(OfflineMessageRedisKey.OFFLINE_MESSAGE, String.valueOf(toUserId));
        if (zSetOperations.zCard(key) == 0) {
            setOperations.remove(key2, fromUserId);
        }
    }

    /**
     * 得到未读消息数目
     *
     * @param fromUserId
     * @param toUserId
     */
    public long getMessageCountByFromUserIdAndToUserId(long fromUserId, long toUserId) {
        String key = String.format(OfflineMessageRedisKey.OFFLINE_MESSAGE_FROM_TO,
                String.valueOf(fromUserId), String.valueOf(toUserId));
        return zSetOperations.zCard(key);
    }
}
