package qtone.xxt.video.fetch;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FLV MetaData Tag
 * @author Ethan lam
 * 2012-5-12
 */
public class MetaArrayData {

	private int currenParamsLength = 0;
	
	private Map<String,Object> params = new HashMap<String,Object>();

	private List<String> paramsSequence = new ArrayList<String>();
	
	private byte[] datas = null;
	
	
	public int getCurrenParamsLength() {
		return currenParamsLength;
	}


	
	public Object getParamValue(String key){
		if(this.params.containsKey(key))
			return this.params.get(key);
		else
			return null;
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 */
	public void addData(String name,Object value){
		 if(name==null||"".equals(name))
			 return;
		// System.out.println("add Data:"+name+"="+value);
		 if(!this.params.containsKey(name)){
		    currenParamsLength++;
		    paramsSequence.add(name);
		 }
	     params.put(name, value);
	}
	
	
	public void setDatas(byte[] datas){
		this.datas = datas;
	}
	
	public byte[] getDatas(){
		return generateMetaArrayDataBytes();
	}
	
	
    /**
     * 重置部分参数
     * @param nVals
     * @return
     */
	public byte[] newParams(Map<String,Object> nVals){
		currenParamsLength = 0;
		ByteBuffer nDatas = ByteBuffer.allocate(1024);
        //重新生成 MetaData 部分的数据
		for(String key:paramsSequence){
			currenParamsLength++;
            if(nVals!=null && nVals.containsKey(key)){
            	//采用新的值
            	nDatas.put(genByteData(key,nVals.get(key)));
            }else{
            	//采用原来的值
            	nDatas.put(genByteData(key,this.params.get(key)));
            }
        }        
		byte[] gendata = new byte[nDatas.position()];
		System.arraycopy(nDatas.array(),0,gendata,0,nDatas.position());
		return gendata;
	}
	
	
	/**
	 * 数据反转生成
	 * @return
	 */
	public byte[] generateMetaArrayDataBytes(){
		return newParams(null);
	}
	
	
	
	
	/**
	 * 生成字节数组
	 * @param key
	 * @param value
	 * @return
	 */
	private byte[] genByteData(String key,Object value){
		ByteBuffer bbuf = null;
		if(value.getClass().getName().endsWith("Double")){
			bbuf =  ByteBuffer.allocate(2+key.length()+1+8);
            bbuf.putShort((short)key.length());  //参数名称的长度
            bbuf.put(key.getBytes());   //参数名称
            bbuf.put((byte)0);       //数据类型
			bbuf.putDouble(((Double)value).doubleValue()); //参数对应的数值
		}
		if(value.getClass().getName().endsWith("String")){
			int valLen = ((String)value).length();
			bbuf =  ByteBuffer.allocate(2+key.length()+1+2+valLen);
			bbuf.putShort((short)key.length());  //参数名称的长度
            bbuf.put(key.getBytes());   //参数名称
            bbuf.put((byte)2);       //数据类型
            bbuf.putShort((short)valLen);  //值对应字符串的长度
			bbuf.put(((String)value).getBytes()); //参数对应的值
		}
		if(value.getClass().getName().endsWith("Boolean")){
			bbuf =  ByteBuffer.allocate(2+key.length()+1+1);
			bbuf.putShort((short)key.length()); //参数名称的长度
            bbuf.put(key.getBytes());   //参数名称
            bbuf.put((byte)1);       //数据类型
            bbuf.put(((Boolean)value)?(byte)1:(byte)0);     //参数对应的数值
		}
		return bbuf.array();
	}
	
	
	
}
