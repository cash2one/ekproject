package service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import service.base.BaseServiceImpl;
import util.MD5;



/**
 * 
 * @author yongboy
 * @date 2011-4-4
 * @version 1.0
 */
public class UserServiceImpl extends BaseServiceImpl<User> implements
		IUserService {

	public void save(User user) {
		user.setId(getNextUid());
		user.setPass(MD5.encode(user.getPass()));
		super.save(getId(user.getId()), user);

		super.saveStr(String.format(USER_NAME_FORMAT, user.getName()),
				Long.toString(user.getId()));

		super.addHeadList(GLOBAL_USER_FORMAT, Long.toString(user.getId()));
	}

	public User load(long id) {
		return super.get(getId(id));
	}

	private String getId(long id) {
		return String.format(USER_ID_FORMAT, id);
	}

	private String getName(String name) {
		return String.format(USER_NAME_FORMAT, name);
	}

	private long getNextUid() {
		return super.incr(GLOBAL_USER_ID);
	}

	public void init() {
		String currUserId = getStr(GLOBAL_USER_ID);
		long currUid = 0L;
		if (currUserId == null || currUserId.length() == 0)
			currUid = -1L;

		if (currUid < 1000L) {
			saveStr(GLOBAL_USER_ID, Long.toString(1000L));
		}
	}

	public long loadIdByName(String userName) {
		String id = super.getStr(getName(userName));

		if (id == null || id.equals(""))
			return -1L;

		return Long.valueOf(id);
	}

	public boolean checkExistByName(String userName) {
		return loadIdByName(userName) > 0L;
	}

	public User checkLogin(String username, String password) {
		long userId = loadIdByName(username);

		if (userId < 1L)
			return null;

		return this.load(userId);
	}

	public void addFollowee(long targetUserId, long followeeUserId) {
		if (targetUserId == followeeUserId)
			return;

		super.jedis.sadd(String.format(FOLLOWEES_FORMAT, targetUserId),
				Long.toString(followeeUserId));

		super.jedis.sadd(String.format(FOLLOWERS_FORMAT, followeeUserId),
				Long.toString(targetUserId));
	}

	public Set<String> getFollowers(long userId) {
		return getFollowUserNames(FOLLOWERS_FORMAT, userId);
	}

	public Set<String> getFollowees(long userId) {
		return getFollowUserNames(FOLLOWEES_FORMAT, userId);
	}

	public Set<String> getFollowUserNames(String targetId, long userId) {
		String followerId = String.format(targetId, userId);

		Set<String> idSet = super.jedis.smembers(followerId);

		if (idSet.isEmpty())
			return idSet;

		Set<String> nameSet = new HashSet<String>(idSet.size());
		for (String uid : idSet) {
			User user = load(Long.valueOf(uid));

			if (user == null)
				continue;

			nameSet.add(user.getName());
		}

		return nameSet;
	}

	public List<String> getNewUsers(int page) {
		int start = (page - 1) * 10;
		int end = page * 10;
		List<String> idList = super.jedis
				.lrange(GLOBAL_USER_FORMAT, start, end);

		if (idList.isEmpty())
			return idList;

		List<String> nameSet = new ArrayList<String>(idList.size());
		for (String uid : idList) {
			User user = load(Long.valueOf(uid));

			if (user == null)
				continue;

			nameSet.add(user.getName());
		}

		return nameSet;
	}

	public User loadByName(String userName) {
		long userId = loadIdByName(userName);

		if (userId < 1L)
			return null;

		return load(userId);
	}

	public boolean checkFlollowing(long currUserId, long targeUserId) {
		return super.jedis.sismember(
				String.format(FOLLOWEES_FORMAT, currUserId),
				Long.toString(targeUserId));
	}

	public void addFollowee(String currUserName, String targetUserName) {
		if (currUserName == null || targetUserName == null
				|| currUserName.equals(targetUserName))
			return;

		long currUserId = loadIdByName(currUserName);
		long targetUserId = loadIdByName(targetUserName);

		if (currUserId < 1L || targetUserId < 1L)
			return;

		addFollowee(currUserId, targetUserId);
	}

	public void removeFollowee(String currUserName, String targetUserName) {
		if (currUserName == null || targetUserName == null
				|| currUserName.equals(targetUserName))
			return;

		long currUserId = loadIdByName(currUserName);
		long targetUserId = loadIdByName(targetUserName);

		if (currUserId < 1L || targetUserId < 1L)
			return;

		super.jedis.srem(String.format(FOLLOWEES_FORMAT, currUserId),
				Long.toString(targetUserId));
		super.jedis.srem(String.format(FOLLOWERS_FORMAT, targetUserId),
				Long.toString(currUserId));
	}
	
	private static final String GLOBAL_USER_ID = "global:nextUserId";
	private static final String USER_ID_FORMAT = "user:id:%s";
	private static final String USER_NAME_FORMAT = "user:name:%s";
	private static final String FOLLOWERS_FORMAT = "user:id:%s:followers";
	private static final String FOLLOWEES_FORMAT = "user:id:%s:followees";
	private static final String GLOBAL_USER_FORMAT = "users";
}