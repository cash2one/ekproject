package blog.service;

import java.util.List;

import blog.model.Topic;
import service.base.BaseServiceImpl;

public class TopicServiceImp extends BaseServiceImpl<Topic> implements ITopicService{

	static final String GLOBAL_TOPIC_ID = "global:nextTopicId";
	
	static final String TOPICS_FORMAT = "Topics:%d";
	
	static final String TOPICS = "Topics";
	
	public Topic load(long id) {
		// TODO Auto-generated method stub
		return super.get(super.getFormatKeyStr(TOPICS_FORMAT,id));
	}

	public void save(Topic topic) {
		// TODO Auto-generated method stub
		long id = getNextTopicId();
		topic.setId(id);
		String fkey = super.getFormatKeyStr(TOPICS_FORMAT,id);
		super.save(fkey,topic);
		super.addMemberToSet(TOPICS,Long.toString(id));
	}

	
	private long getNextTopicId() {
		return super.incr(GLOBAL_TOPIC_ID);
	}
	
	
	public void update(Topic topic) {
		// TODO Auto-generated method stub
        super.save(super.getFormatKeyStr(TOPICS_FORMAT,topic.getId()),topic);
	}

	
	public void delete(long id) {
		// TODO Auto-generated method stub
        super.remove(super.getFormatKeyStr(TOPICS_FORMAT,id));	
        super.removeSetMember(TOPICS,Long.toString(id));
        
	}

	
	public List<Topic> list(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

}
