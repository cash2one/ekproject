package qtone.xxt.video.fetch;

import java.nio.ByteBuffer;


/**
 * 
 * @author Ethan lam
 * 2012-5-12
 */
public class MetadataTag {
	
	private byte[] header;
	private byte[] tagData;
	
	private boolean DEBUG = true;
	
	private MetaArrayData _metaArrayData;
	
	
	public MetaArrayData get_metaArrayData() {
		return _metaArrayData;
	}


	public MetadataTag(MetaArrayData _metaArrayData){
		this._metaArrayData = _metaArrayData;
	}
	
	
	public void setMetaArrayData(MetaArrayData _metaArrayData){
		this._metaArrayData = _metaArrayData;
	}

	public Object getParamValue(String key){
		return this._metaArrayData.getParamValue(key);
	}
	
	
	/**
	 * 返回整个 MetadataTag 包 
	 * @return
	 */
	public byte[] getDatas(){
		ByteBuffer bbuf =  ByteBuffer.allocate(1024);
		byte[] _metaArrayDataBytes = _metaArrayData.getDatas();
	   	//开始写tag head
		bbuf.put((byte)18);//tag type
	   	int dataSize = _metaArrayDataBytes.length+18;
	   	writeNumberToByte(bbuf,dataSize,3); //dataSzie
	   	bbuf.putInt(0);//timestamp  默认为“0”
		writeNumberToByte(bbuf,0,3); // stream id   默认为“0”
        		
		//开始写 AMF1包信息
		bbuf.put((byte)2);
		bbuf.putShort((short)10);
		bbuf.put("onMetaData".getBytes());
		//AMF2
		bbuf.put((byte)8);
		bbuf.putInt(_metaArrayData.getCurrenParamsLength());
		//输入具体的数值包
		byte[] metaDataTagByte = new byte[bbuf.position()+_metaArrayDataBytes.length+4];
		System.arraycopy(bbuf.array(),0, metaDataTagByte, 0, bbuf.position());
		System.arraycopy(_metaArrayDataBytes,0, metaDataTagByte, bbuf.position(),_metaArrayDataBytes.length);
		
		//pre tag section 
		int lastOp = bbuf.position()+_metaArrayDataBytes.length;
		bbuf = ByteBuffer.allocate(4);
		bbuf.putInt(metaDataTagByte.length-4);//pre tag Size 
		System.arraycopy(bbuf.array(),0, metaDataTagByte,lastOp, bbuf.position());
		
		if(DEBUG){
			System.out.print("byte2hexString:....");
			for(byte b :metaDataTagByte)
			   System.out.print(""+ParseMetaFlag.byte2hexString(b)+" ");
		}
		return metaDataTagByte;
	}
	
	
	/**
	 * 把整数转换为16进制字符串，在用单字节的方式存储到字节中
	 * @param bbuf
	 * @param value
	 * @param bytesLen
	 */
	private void writeNumberToByte(ByteBuffer bbuf,int value,int bytesLen){
	   	String valueHex = Integer.toHexString(value);
	   	System.out.println("dataSizeHex:"+valueHex);
	   	//把字符补零
	    String tempStr = "00000000"; //四字节
	    tempStr = tempStr.substring(0,2*bytesLen-valueHex.length());
	    tempStr+= valueHex;
	    int op = 0;
	   	for(int i = 0;i<bytesLen;i++,op+=2){
	   		bbuf.put((byte)Integer.parseInt(tempStr.substring(op,op+2), 16));
	   	}
	}
	
	
	public static void main(String...args){
		MetadataTag t = new MetadataTag(null);
		t.getDatas();
	}

}
