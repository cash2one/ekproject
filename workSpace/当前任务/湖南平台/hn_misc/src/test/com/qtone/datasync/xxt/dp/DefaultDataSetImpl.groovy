import com.qtone.datasync.xxt.dp.IDataSet
import java.util.*

import com.qtone.datasync.util.FileUtil
/**
 * 数据库初始数据加载
 */
public class DefaultDataSetImpl implements IDataSet{
	public List<List<String>> loadData(String dataFile){
		List<List<String>> ret = new ArrayList<List<String>>()
		
		def dataFilePath = FileUtil.getFilePath(dataFile)
		List<String> row = new ArrayList<String>()
		new File(dataFilePath).eachLine{line->
			line.split(","){elem->
				row.add(elem)
			}
			
			ret.add(row)
			row.clear()
		}
		
		return ret
	}
}