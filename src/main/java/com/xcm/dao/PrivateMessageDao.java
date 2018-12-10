package com.xcm.dao;

import com.xcm.dao.hibernate.model.PrivateMessageRecord;
import com.xcm.socket.body.model.PrivateMessage;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

import static com.xcm.model.key.OfflineMessageRedisKey.OFFLINE_MESSAGE;

/**
 * Created by 薛岑明 on 2017/3/20.
 */
@Repository
public class PrivateMessageDao extends BaseDao {
    /**
     * 保存私信
     *
     * @param privateMessageRecord
     */
    public void savePrivateMessageRecord(PrivateMessageRecord privateMessageRecord) {
        getSession().save(privateMessageRecord);
    }
}
