package cn.qtone.xxt.android.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


/**
 * 测试的活动窗口编写 
 * @author Ethan lam
 *
 */
public class MyActivity extends Activity {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.activity_my_active);
        
        List<String> items = new ArrayList<String>();
        items.add("测试数据1");
        items.add("测试数据2");
        items.add("测试数据3");
        items.add("测试数据4");
        
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.list_item_1, new String[] { "title","img","name","bimg"}, new int[] { R.id.listItemTitle,R.id.listItemImg,R.id.listItemName,R.id.listItemContentImg});
        ((ListView)this.findViewById(R.id.myListView)).setAdapter(adapter);
        
        
    }

    private List<Map<String, Object>> getData() {
        //map.put(参数名字,参数值)
	        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("name", "新消息");
	        map.put("title", "这可能是近年来最令人期待的一场发布会了，旧金山Yerba Buena中心将再一次响起人们的欢呼声和惊叹声，这也是乔布斯逝世库克接棒后第一次独立发布iPhone产品");
	        map.put("img", R.drawable.default_avatar_shadow);
	        map.put("bimg", R.drawable.addressbook_faceback);
	        list.add(map);
	        
	        map = new HashMap<String, Object>();
	        map.put("name", "新消息");
	        map.put("title", "这可能是近年来最令人期待的一场发布会了，旧金山Yerba Buena中心将再一次响起人们的欢呼声和惊叹声，这也是乔布斯逝世库克接棒后第一次独立发布iPhone产品");
	        map.put("img", R.drawable.default_avatar_shadow);
	        map.put("bimg", R.drawable.bg);
	        list.add(map);
	        
	        map = new HashMap<String, Object>();
	        map.put("name", "新消息");
	        map.put("title", "这可能是近年来最令人期待的一场发布会了，旧金山Yerba Buena中心将再一次响起人们的欢呼声和惊叹声，这也是乔布斯逝世库克接棒后第一次独立发布iPhone产品");
	        map.put("img", R.drawable.default_avatar_shadow);
	        map.put("bimg", R.drawable.back);
	        list.add(map);
	        return list;
    } 
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_test_active, menu);
        return true;
    }
	
	
	
}
