package service;


import java.util.List;



/**
 * 
 * @author yongboy
 * @date 2011-4-4
 * @version 1.0
 */
public interface IStatusService {
	void save(long userId, String value, String userIp);

	Status load(long id);

	void init();
	
	List<Status> timeline(int page);

	List<Status> timeline(long userId, int page);

	List<Status> mentions(long userId, int page);

	List<Status> mentions(String userName, int page);

	List<Status> posts(long userId, int page);
}