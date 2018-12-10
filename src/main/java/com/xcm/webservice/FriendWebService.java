package com.xcm.webservice;

import com.xcm.business.FriendBusiness;
import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.FriendRequestRes;
import com.xcm.model.resource.FriendRes;
import com.xcm.model.resource.RecentContactsRes;
import com.xcm.model.resource.RecentVisitorRes;
import com.xcm.webservice.util.HttpDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by 薛岑明 on 2017/3/16.
 */
@Produces("application/json; charset=UTF-8")
public class FriendWebService {
    @Autowired
    private HttpDao httpDao;
    @Autowired
    private FriendBusiness friendBusiness;

    @GET
    @Path("/recent/Visitor")
    public Map<Object, Object> getRecentVisitor(@QueryParam("userId") long userId) {
        Locale locale = httpDao.getLocale();
        if (userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMETER_ERROR, locale);
        }
        List<RecentVisitorRes> list =
                friendBusiness.getRecentVisitorResList(userId);
        Map<Object, Object> map = new HashMap<>();
        map.put("recentVisitors", list);
        return map;
    }

    @GET
    @Path("/list")
    public Map<Object, Object> getFriendList(@QueryParam("offset") long offset,
                                             @DefaultValue("5") @QueryParam("count") int count,
                                             @QueryParam("loadMore") boolean loadMore) {
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        List<FriendRes> list =
                friendBusiness.getFriendList(userId, offset, count, loadMore);
        Map<Object, Object> map = new HashMap<>();
        map.put("list", list);
        return map;
    }

    @POST
    @Path("/add")
    public Map<Object, Object> addFriend(@FormParam("userId") long userId) {
        HttpSession session = httpDao.getHttpSession();
        long ownUserId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        friendBusiness.sendFriendRequest(ownUserId, userId, locale);
        Map<Object, Object> map = new HashMap<>();
        map.put("result", true);
        return map;
    }

    @POST
    @Path("/delete")
    public Map<Object, Object> delFriend(@FormParam("userId") long userId) {
        HttpSession session = httpDao.getHttpSession();
        long ownUserId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        friendBusiness.delFriend(ownUserId, userId, locale);
        Map<Object, Object> map = new HashMap<>();
        map.put("result", true);
        return map;
    }

    @POST
    @Path("/pass")
    public Map<Object, Object> passFriendRequest(
            @FormParam("friendRequestId") long friendRequestId) {
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        friendBusiness.passFriendRequest(userId, friendRequestId, locale);
        Map<Object, Object> map = new HashMap<>();
        map.put("result", true);
        return map;
    }

    @POST
    @Path("/ignore")
    public Map<Object, Object> ignoreFriendRequest(
            @FormParam("friendRequestId") long friendRequestId) {
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        Locale locale = httpDao.getLocale();
        friendBusiness.ignoreFriendRequest(userId, friendRequestId, locale);
        Map<Object, Object> map = new HashMap<>();
        map.put("result", true);
        return map;
    }

    @GET
    @Path("/requestList")
    public Map<Object, Object> getFriendRequestList(@QueryParam("offset") long offset,
                                                    @DefaultValue("5") @QueryParam("count") int count,
                                                    @QueryParam("loadMore") boolean loadMore) {
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        List<FriendRequestRes> list =
                friendBusiness.getFriendRequestList(userId, offset, count, loadMore);
        Map<Object, Object> map = new HashMap<>();
        map.put("list", list);
        return map;
    }

    @GET
    @Path("/recent/contact")
    public Map<Object, Object> getRecentContact(@QueryParam("offset") long offset,
                                                @DefaultValue("5") @QueryParam("count") int count,
                                                @QueryParam("loadMore") boolean loadMore) {
        HttpSession session = httpDao.getHttpSession();
        long userId = (long) session.getAttribute(HttpDao.USER_ID);
        List<RecentContactsRes> list = friendBusiness.getRecentContact(userId, offset, count, loadMore);
        Map<Object, Object> map = new HashMap<>();
        map.put("list", list);
        return map;
    }
}
