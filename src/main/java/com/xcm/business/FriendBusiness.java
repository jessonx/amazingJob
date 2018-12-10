package com.xcm.business;

import com.xcm.dao.FriendDao;
import com.xcm.dao.OfflineMessageDao;
import com.xcm.dao.UserDao;
import com.xcm.dao.hibernate.model.FriendRequest;
import com.xcm.dao.hibernate.model.Users;
import com.xcm.model.Enum.FriendRequestStatusEnum;
import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.FriendRequestRes;
import com.xcm.model.resource.FriendRes;
import com.xcm.model.resource.RecentContactsRes;
import com.xcm.model.resource.RecentVisitorRes;
import com.xcm.model.resource.tuple.TwoTuple;
import com.xcm.socket.MessageCenter;
import com.xcm.socket.body.model.NotificationModel;
import com.xcm.util.CommonUtil;
import com.xcm.webSocket.commad.OfflineMsgType;
import com.xcm.webSocket.commad.SocketId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class FriendBusiness {
    @Autowired
    private FriendDao friendDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageCenter messageCenter;
    @Autowired
    private OfflineMessageDao offlineMessageDao;

    private static final int RECENT_VISITOR_COUNT = 8;

    /**
     * 得到最近浏览者
     *
     * @param userId
     * @return
     */
    public List<RecentVisitorRes> getRecentVisitorResList(long userId) {
        List<RecentVisitorRes> resList = new LinkedList<>();
        List<TwoTuple<Long, Long>> list =
                friendDao.getRecentVisitorByUserId(userId, RECENT_VISITOR_COUNT);
        for (TwoTuple<Long, Long> tuple : list) {
            long visitorUserId = tuple.first;
            long time = tuple.second;
            String visitDate = CommonUtil.getDateStrByTime(time);
            Users visitorUser = userDao.getUserById(visitorUserId);
            if (visitorUser != null) {
                String headerThumb = visitorUser.getHeaderThumb();
                String nickName = visitorUser.getNickName();
                RecentVisitorRes res =
                        new RecentVisitorRes(visitorUserId, headerThumb, nickName, visitDate);
                resList.add(res);
            }
        }
        return resList;
    }

    /**
     * 分页得到好友列表
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<FriendRes> getFriendList(long userId, long offset,
                                         int count, boolean loadMore) {
        List<FriendRes> resList = new LinkedList<>();
        List<Map<String, Long>> list =
                friendDao.getFriendList(userId, offset, count, loadMore);
        for (Map<String, Long> map : list) {
            long friendUserId = map.get("value");
            Users friendUser = userDao.getUserById(friendUserId);
            FriendRes friendRes = wrapFriendRes(map, friendUser);
            resList.add(friendRes);
        }
        return resList;
    }

    /**
     * 发送好友请求
     *
     * @param ownUserId
     * @param userId
     * @param locale
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void sendFriendRequest(long ownUserId, long userId, Locale locale) {
        if (userId==ownUserId){
            throw new BusinessException(ErrorCode.CAN_NOT_ADD_FRIEND_SELF,locale);
        }

        Users ownUsers = userDao.getUserById(ownUserId);
        if (ownUsers == null) {
            throw new BusinessException(ErrorCode.USER_IS_NOT_EXIST, locale);
        }

        FriendRequest friendRequest = friendDao.getPendingFriendRequest(ownUserId, userId);
        if (friendRequest != null) {
            throw new BusinessException(ErrorCode.REPEATED_FRIEND_REQUEST, locale);
        }

        Date date = new Date();
        friendRequest = new FriendRequest(ownUserId, userId, FriendRequestStatusEnum.PENDING.value(),
                ownUserId, date, ownUserId, date);
        friendDao.saveFriendRequest(friendRequest);

        NotificationModel notificationModel = new NotificationModel("好友请求",
                ownUsers.getHeaderThumb(), ownUsers.getNickName() + " 向您发送好友请求", "friend.html");
        messageCenter.senNotification(userId, notificationModel);

    }

    /**
     * 删除好友
     *
     * @param ownUserId
     * @param userId
     * @param locale
     */
    public void delFriend(long ownUserId, long userId, Locale locale) {
        friendDao.removeFriend(ownUserId, userId);
    }

    /**
     * 同意加好友
     *
     * @param userId
     * @param friendRequestId
     * @param locale
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void passFriendRequest(long userId, long friendRequestId, Locale locale) {
        FriendRequest friendRequest =
                friendDao.getPendingFriendRequestById(friendRequestId);
        if (friendRequest == null) {
            throw new BusinessException(ErrorCode.FRIEND_REQUEST_NOT_EXIST, locale);
        }
        long fromUserId = friendRequest.getFromUserId();
        long toUserId = friendRequest.getToUserId();
        if (toUserId != userId) {
            throw new BusinessException(ErrorCode.NO_PERMISSION, locale);
        }
        friendRequest.setStatus(FriendRequestStatusEnum.PASS.value());
        friendRequest.setModifiedBy(userId);
        friendRequest.setModifiedOn(new Date());
        friendDao.saveFriendRequest(friendRequest);
        friendDao.addFriend(fromUserId, toUserId);
        Users users = userDao.getUserById(toUserId);
        NotificationModel notificationModel = new NotificationModel("通过好友请求",
                users.getHeaderThumb(), users.getNickName() + " 通过了您的好友请求", "friend.html");
        messageCenter.senNotification(fromUserId, notificationModel);
    }

    /**
     * 忽略好友起请求
     *
     * @param userId
     * @param friendRequestId
     * @param locale
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void ignoreFriendRequest(long userId, long friendRequestId, Locale locale) {
        FriendRequest friendRequest =
                friendDao.getPendingFriendRequestById(friendRequestId);
        if (friendRequest == null) {
            throw new BusinessException(ErrorCode.FRIEND_REQUEST_NOT_EXIST, locale);
        }
        long toUserId = friendRequest.getToUserId();
        if (toUserId != userId) {
            throw new BusinessException(ErrorCode.NO_PERMISSION, locale);
        }
        friendRequest.setStatus(FriendRequestStatusEnum.IGNORE.value());
        friendRequest.setModifiedBy(userId);
        friendRequest.setModifiedOn(new Date());
        friendDao.saveFriendRequest(friendRequest);

    }

    /**
     * 好友请求列表
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<FriendRequestRes> getFriendRequestList(long userId,
                                                       long offset, int count, boolean loadMore) {
        List<FriendRequest> list =
                friendDao.getPendingFriendRequestList(userId, offset, count, loadMore);
        List<FriendRequestRes> resList = new LinkedList<>();
        for (FriendRequest request : list) {
            long id = request.getFriendRequestId();
            long fromUserId = request.getFromUserId();
            Users user = userDao.getUserById(fromUserId);
            if (user != null) {
                String headerThumb = user.getHeaderThumb();
                String nickName = user.getNickName();
                long resOffset = request.getCreatedOn().getTime();
                String time = CommonUtil.getDateStrByTime(resOffset);
                FriendRequestRes res = new FriendRequestRes(id,
                        fromUserId, time, headerThumb, nickName, resOffset);
                resList.add(res);
            }
        }
        return resList;
    }

    /**
     * 分页获取最近联系人
     *
     * @param userId
     * @param offset
     * @param count
     * @param loadMore
     * @return
     */
    public List<RecentContactsRes> getRecentContact(long userId,
                                                    long offset, int count, boolean loadMore) {
        List<RecentContactsRes> resList = new LinkedList<>();
        List<Map<String, Long>> list =
                friendDao.getRecentContactList(userId, offset, count, loadMore);
        for (Map<String, Long> map : list) {
            long friendUserId = map.get("value");
            Users friendUser = userDao.getUserById(friendUserId);
            RecentContactsRes res = wrapRecentContactsRes(map, friendUser);
            long fromUserId = res.getUserId();
            long toUserId = userId;
            long messageCount = offlineMessageDao.
                    getMessageCountByFromUserIdAndToUserId(fromUserId, toUserId);
            res.setMessageCount(messageCount);
            resList.add(res);
        }
        return resList;

    }

    private RecentContactsRes wrapRecentContactsRes(Map<String, Long> map, Users friendUser) {
        long friendUserId = map.get("value");
        long resOffset = map.get("score");
        RecentContactsRes res = new RecentContactsRes();
        if (friendUser != null) {
            String nickName = friendUser.getNickName();
            int gender = friendUser.getGender();
            String description = friendUser.getDescription();
            String headerThumb = friendUser.getHeaderThumb();
            res = new RecentContactsRes(friendUserId,
                    description, nickName, headerThumb, gender, resOffset);
        }
        return res;
    }

    private FriendRes wrapFriendRes(Map<String, Long> map, Users friendUser) {
        long friendUserId = map.get("value");
        long resOffset = map.get("score");
        FriendRes friendRes = new FriendRes();
        if (friendUser != null) {
            String nickName = friendUser.getNickName();
            int gender = friendUser.getGender();
            String description = friendUser.getDescription();
            String headerThumb = friendUser.getHeaderThumb();
            friendRes =
                    new FriendRes(friendUserId, description, nickName, headerThumb, gender, resOffset);
        }
        return friendRes;
    }


}
