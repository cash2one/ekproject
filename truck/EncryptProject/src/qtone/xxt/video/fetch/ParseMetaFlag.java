package qtone.xxt.video.fetch;

import java.nio.ByteBuffer;

/**
 * @author Ethan lam
 * 2012-5-12
 */
public class ParseMetaFlag {

	static Boolean DEBUG = false;
	
	/**
	 * 分析视频文件的 MetaData 数据结构
	 */
	protected static void analysMetaArrayDatas(byte[] metaData){
         //生成MetaData数据
		 int spos = 0;
//		 System.out.println("MetaData Data Array: 视频信息有:"+byte2Number(metaData,26,3)+" 个参数。");
		 for(int pos =spos;pos<metaData.length;){
			int strlength =  byte2Number(metaData,pos,2);
			if(strlength==0){
				break;
			}
			pos += 2;
			String paramNameStr = byte2String(metaData,pos,strlength);
			pos += strlength;
			int dataType = byte2Number(metaData,pos,1);
			pos += 1;
			int dataLen = 0;
			boolean isNumber = true;
			Object value = 0;
			//判断数据类型
			switch(dataType){
			     case 0: dataLen = 8; value = readDouble(metaData,pos,8); break;//Number    DOUBULE ;  
			     case 1: dataLen = 1; value = getAMFBoolean(metaData,pos); break;//Boolean ;      UI8
			     case 2: value = getAMFString(metaData,pos); dataLen = tempOp;  break;//String;      SCRIPTDATASTRING
			     case 3: dataLen = 4; break;//Object ;      SCRIPTDATAOBJECT
			     case 4: dataLen = 4; break;//MovieClip (reserved, not supported) ;  
			     case 5: dataLen = 4; break;//Null ;  
			     case 6: dataLen = 4; break;//Undefined ;  
			     case 7: dataLen = 2; break;//Reference ;   UI16
			     case 8: dataLen = 4; break;//ECMA array ;   SCRIPTDATAECMAARRAY
			     case 9: dataLen = 4; break;//Object end marker ;
			     case 10: dataLen = 4; break;//Strict array ;   SCRIPTDATAECMAARRAY
			     case 11: dataLen = 4; break;//Date ;  SCRIPTDATADATE
			     case 12: dataLen = 4; break;//Long string ;      SCRIPTDATALONGSTRING
			     default :System.out.println("找不到类型"); break;
			} 
		
			System.out.println("MetaData array Data(len:"+strlength+") Name: "+paramNameStr +",Value:"+value+" [Type:"+dataType+"]");
			pos += dataLen;
		 }
		 
	}

	static int tempOp = 0;
	
    public static double readDouble(byte[] mpb, int start, int len){
        ByteBuffer bbuf = ByteBuffer.allocate(len);
        byte[] buf = new byte[len];
        System.arraycopy(mpb,start,buf,0,len);
        bbuf.put(buf);
        bbuf.rewind();
        buf = null;
        return  bbuf.getDouble();
    }//readDouble()
	
    
    public static Boolean getAMFBoolean(byte[] mbb, int pos){
        int val = readUint(mbb, pos, 1);
        pos += 1;
        return new Boolean((val == 1));
    }//getAMFBoolean()
    
    
    public static String getAMFString(byte[] mbb, int pos){
    	tempOp = 0; //重置
        int bytes2read = readUint(mbb, pos, 2);
        pos += 2;
        tempOp += 2;
        String str = readString(mbb, pos, bytes2read);
        pos += bytes2read;
        tempOp += bytes2read;
        return str;

    }//getAMFString()
    
    
    // read String from existing byte[]
    public static String readString(byte[] mpb, int start, int len){

    	byte [] buf = new byte[len];
        String str = null;
        try{
            System.arraycopy(mpb,start,buf,0,len);
            str = new String(buf);
            buf = null;
        }catch(Exception e){
            System.out.println("Error - could not read string from given bytes");
            str = "";
        }
        return str;

    }//readString()
    
    public static int readUint(byte[] mpb, int start, int len){
        int uint = 0;
        for(int i=0;i<len;i++){
            uint += (mpb[i+start] & 0xFF) << ((len -i -1)*8);
        }
        return uint;
    }//readUint()
    
	/**
	 * 显示数字
	 * @param b
	 * @param offLenght
	 * @param length
	 * @return
	 */
	protected static int byte2Number(byte[] b,int offLenght,int length){
		String str = "";
		for(int i=0;i<length;i++){
			str+=byte2hexString(b[offLenght+i]);
		}
		if(DEBUG)
		System.out.println("[debug]:byte2Number:"+str);
		return Integer.parseInt(str,16);
	}
	
	/**
	 * 显示字符串
	 * @param b
	 * @param offLenght
	 * @param length
	 * @return
	 */
	protected static String byte2String(byte[] b,int offLenght,int length){
		String str = new String(b,offLenght,length);
		return str;
	}
	
	
	static String byte2hexString(byte b){
		String stmp =  String.valueOf(Integer.toHexString(b & 0XFF));
		if (stmp.length() == 1) {
			stmp = "0" + stmp.toUpperCase();
		} else {
			stmp= stmp.toUpperCase();
		}
		return stmp;
	}
	
}
