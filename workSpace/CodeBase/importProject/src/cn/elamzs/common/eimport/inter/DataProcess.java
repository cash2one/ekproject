package cn.elamzs.common.eimport.inter;


/**
 * 
 * ���������ݵ������������ ��Ӧ��  ���̴���  �Ľӿ�  
 * @author Ethan.Lam
 *
 */
public interface DataProcess {


	/**
	 * 
	 * ��ÿ��ȡ���ĵ��е�һ�����ݺ�
	 * ʵ����ɶԸ������ݽ�ִ�еĲ�������ѵ�ǰ�����ݲ��뵽Ŀ�����ݿ�
	 *                      
	 * @param data
	 */
	public void currentRowValueProcess(DataElement data);
	

	
	/**
	 * 
	 * ��ִ�����������ݼ��غ󣬵��� afterLoadRowsDataEvent ������
	 * ע�ͣ�����������У��ɶ����ݽ���������
	 * 
	 */
	public void afterLoadAllRowsDataProcess();

	
    
	
}
