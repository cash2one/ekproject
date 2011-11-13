package service;


import java.util.List;
import java.util.Set;


 

/**
 * 
 * @author yongboy
 * @date 2011-4-4
 * @version 1.0
 */
public interface IUserService {

	void save(User user);

	User load(long id);

	long loadIdByName(String userName);

	User loadByName(String userName);

	boolean checkExistByName(String userName);

	User checkLogin(String username, String password);

	void init();

	Set<String> getFollowers(long userId);

	Set<String> getFollowees(long userId);

	List<String> getNewUsers(int page);

	boolean checkFlollowing(long currUserId, long targeUserId);

	void addFollowee(long currUserId, long followeeUserId);

	void addFollowee(String currUserName, String targetUserName);

	void removeFollowee(String currUserName, String targetUserName);
}