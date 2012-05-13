package qtone.xxt.video.fetch;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Ethan lam
 * 2012-5-12
 */
public class VideoFile {

	
//	private static  String filePath = "E:/elam/my_jar_project/CodeGenerator/WebRoot/flv/06_1/";
	private static  String filePath = "D:/Workspaces/google/ekproject/truck/HTML5/WebContent/flv/06_1/";
	
	private static  String file = "1";
	private static  String fileSuffix = ".flv";
	private static  String keyFrameDir = "/keyFrames/";
	
	private static Map<String,Integer> keyFramePositions = new HashMap<String,Integer>();
	private static Map<String,Float> keyFrameTimestamps = new HashMap<String,Float>();
	
	
	private static boolean DEBUG = false;
	
	static MetadataTag _metadataTag = null;
	
	static double _duration = 0;
	static double _filesize = 0;
	
   public static void main(String...args) throws Exception{
//	   loadByBytes( );
	   op = 0;
//	   byte2hex(loadByByteBuffer());
	   analysViedoTag(loadByByteBuffer());
   }
	
	
   
   
   /**
    * ��ͨ�ļ��ط�ʽ
    * @throws Exception
    */
	public static void loadByBytes( ) throws Exception {
      BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath+file+fileSuffix));
	  System.out.println("Available bytes:" + in.available());
	    byte[] temp = new byte[1024*20];
		int size = 0;
		int times = 0;
		while ((size = in.read(temp)) != -1) {
			byte2hex(temp);
			times++;
		}
		System.out.println("times:"+(times++));
		in.close();
	}
	
	/**
	 * ��ӡ��Ƶ�ļ�����
	 * @param src
	 * @return
	 */
	private static byte [] print(byte [] src){
		if(src==null || src.length<=0)
			return null;
		
		for(int i = 0;i<src.length;i++){
			int temp = ((int) src[i]) & 0xff;
			temp = temp ^ 2;
			src[i] = (byte) temp;
		}
		return src;
	}
	
	private static int op = 0; //��λ
	private static int hop=0; //��λ
	private static String tips = "0x00000000";

	// �ֽ�����ת����ʮ�������ַ���
	protected static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		if(true)
		for (byte bt : b) {
			stmp = (Integer.toHexString(bt & 0XFF));
			if(op%16==0){
				showPosition();
			}
			op++;
			
			if (stmp.length() == 1) {
				System.out.print(" 0" + stmp.toUpperCase());
			} else {
				System.out.print(" " + stmp.toUpperCase());
			}
		}
		return hs.toUpperCase();
	}
	
	static int startOp = 13; //��һ����Ƶ֡λ��
	static int MetaDataOp = 0; //����metadata�����ݰ���С
	static byte[] fileHeadData = null; //��ȡͷ�ļ�����
	
	
    
	/**
	 * 
	 * ������Ƶ�ļ�����
	 * @param b
	 * @throws Exception 
	 * 
	 */
	protected static void analysViedoTag(byte[] b) throws Exception {
		String hs = "";
		String stmp = "";
		if(true)
		for (int offLenght= 0;offLenght<b.length;offLenght++) {
			stmp = (Integer.toHexString(b[offLenght] & 0XFF));
			
			if(op%16==0){
				showPosition();
			}
			
			if(op<startOp){
				op++;
				continue;
			}
			
			if (stmp.length() == 1) {
				stmp = "0" + stmp.toUpperCase();
			} else {
				stmp= stmp.toUpperCase();
			}
//			System.out.println("Find Tag Type is ��"+"  "+stmp +" At "+op);
			if("09".equals(stmp)){
				//����ƵTag
				isKeyFrame(b,offLenght);
			}else if("12".equals(stmp)){
				System.out.println("FLV METADATE Time Stamp:"+timeMessage(b,offLenght));
			}
			
			
			int next = nextTagStartOp(b,offLenght);
			
			//�����ļ�ͷ
			if(MetaDataOp==0){
				MetaDataOp = startOp+next ; //metaDate������λ�� + Pre tag Size 
				fileHeadData = new byte[MetaDataOp];
				System.out.println("MetaDataOp:"+MetaDataOp);
				for(int i=0;i<MetaDataOp;i++){
					fileHeadData[i] = b[i];
				}
				System.out.println("MetaData analys ......");
				analysMetadataStruct();
				System.out.println("MetaData analys finished!");
			}
			
			next = next -1 ;
//			System.out.println("next Tag offset:"+next);
			offLenght += next;
			op =offLenght+1;
		}
	}
	
	
	
	
	
	/**
	 * �ж��Ƿ�Ϊ�ؼ�֡
	 * @param b
	 * @return
	 * @throws Exception 
	 */
	protected static void isKeyFrame(byte[] b,int offLenght) throws Exception{
		if(!"9".equals((Integer.toHexString(b[offLenght] & 0XFF)))){
			System.out.println("����ƵTag��Ϣ...");
			return;
		}
		String frameType = Integer.toHexString(b[offLenght+11]& 0XFF);
		if(frameType.startsWith("1")){
			//����Ƶ�ؼ�֡
			String keyFrameOp = showPosition();
			System.out.println("������ ��Ƶ ���ؼ�֡�� Tag:  "+(keyFrameOp+"  ").toUpperCase()+" ,Time: "+timeMessage(b,offLenght)+"   "+op);
			keyFramePositions.put(keyFrameOp, op);//��¼��Ƶ���ֵĵط�
			keyFrameTimestamps.put(keyFrameOp,timeMessage(b,offLenght));
			fetchNewKeyFrameFile(b,keyFrameOp,op);
			fetchNewKeyFrameFile2(b,keyFrameOp,op);
		} 
	}
	
	/**
	 * ������Ƶ�ļ��� MetaData ���ݽṹ
	 */
	protected static void analysMetadataStruct(){
		 MetaArrayData _metaArrayData = new MetaArrayData();
		 byte[] metaData = new byte[fileHeadData.length-13]; 
         //����MetaData����
		 System.arraycopy(fileHeadData,13,metaData,0, fileHeadData.length-13);
		 int spos = 24;
		 
		 System.out.println("MetaData Data Array: ��Ƶ��Ϣ��:"+byte2Number(metaData,26,3)+" ��������");
		 for(int pos =spos+5;pos<fileHeadData.length-13;){
			int strlength =  byte2Number(metaData,pos,2);
			pos += 2;
			String paramNameStr = byte2String(metaData,pos,strlength);
			pos += strlength;
			int dataType = byte2Number(metaData,pos,1);
			pos += 1;
			int dataLen = 0;
			boolean isNumber = true;
			Object value = 0;
			//�ж���������
			switch(dataType){
			     case 0: dataLen = 8; value = new Double(readDouble(metaData,pos,8)); ; break;//Number    DOUBULE ;  
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
			} 
		
			System.out.println("MetaData array Data(len:"+strlength+") Name: "+paramNameStr +",Value:"+value+" [Type:"+dataType+"]");
			_metaArrayData.addData(paramNameStr, value);
			pos += dataLen;
		 }
		 _metadataTag = new MetadataTag(_metaArrayData);
		 _duration = (Double)_metadataTag.getParamValue("duration");
		 _filesize = (Double)_metadataTag.getParamValue("filesize");
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
    	tempOp = 0; //����
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
	 * ��ʾ����
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
	 * ��ʾ�ַ���
	 * @param b
	 * @param offLenght
	 * @param length
	 * @return
	 */
	protected static String byte2String(byte[] b,int offLenght,int length){
		String str = new String(b,offLenght,length);
		return str;
	}
	
	
	protected static String byte2DataType(byte[] b,int offLenght){
		String str = "";
	    str += byte2String(b,offLenght,1);
	    str += byte2String(b,offLenght+1,1);
	    str += byte2String(b,offLenght+2,1);
	    if(DEBUG)
		System.out.println("[debug]:byte2DataType:"+str);
		return str;
	}

	
	
	/**
	 * ��ȡ�ؼ�֡�ļ�
	 * @param videoData
	 * @param keyFrameName
	 * @param startPosition
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	protected static void fetchNewKeyFrameFile(byte[] videoData,String keyFrameName,int startPosition) throws Exception{
		
		File dir = new File(filePath+keyFrameDir);
		dir.mkdirs();
//		static int MetaDataOp = 0; //����metadata�����ݰ���С
//		static byte[] fileHeadData = null; //��ȡͷ�ļ�����
		
		int nlength = videoData.length-startPosition + MetaDataOp  ;
		byte[] nVideoData = new byte[nlength];
		System.out.println("�����µĹؼ�֡�ļ�..."+keyFrameName+" �ļ���СΪ��"+nlength);
		//����ͷ����
		System.arraycopy(fileHeadData,0,nVideoData,0,fileHeadData.length);
		//����Pre Tag Size + ��Ƶ����
		System.arraycopy(videoData, startPosition, nVideoData,MetaDataOp,nlength-MetaDataOp);
	    ByteArrayOutputStream out = new ByteArrayOutputStream(nlength);
	    out.write(nVideoData);
		out.writeTo(new FileOutputStream(new File(filePath+keyFrameDir+keyFrameName+"_"+file+fileSuffix)));
		out.close();
		
	}
	
	/**
	 * �ı�ͷ��Ϣ
	 * @param videoData
	 * @param keyFrameName
	 * @param startPosition
	 * @throws Exception
	 */
	protected static void fetchNewKeyFrameFile2(byte[] videoData,String keyFrameName,int startPosition) throws Exception{
		
		File dir = new File(filePath+keyFrameDir);
		dir.mkdirs();
//		keyFramePositions,keyFrameTimestamps 
		_metadataTag.get_metaArrayData().addData("duration", new Double(_duration-keyFrameTimestamps.get(keyFrameName)));
		_metadataTag.get_metaArrayData().addData("filesize", new Double(_filesize-startPosition+13+387+4));
 		byte[] _metaData = _metadataTag.getDatas();
		
 		int nlength = videoData.length-startPosition + 13 + _metaData.length  ;
		byte[] nVideoData = new byte[nlength];
		System.out.println("�����µĹؼ�֡�ļ�..."+keyFrameName+" �ļ���СΪ��"+nlength);
		//����ͷ����
		System.arraycopy(fileHeadData,0,nVideoData,0,13);
		//����meta����
		System.arraycopy(_metaData,0,nVideoData,13,_metaData.length);
		//������Ƶ����
		System.arraycopy(videoData, startPosition, nVideoData,_metaData.length+13,videoData.length-startPosition);
		
	    ByteArrayOutputStream out = new ByteArrayOutputStream(nlength);
	    out.write(nVideoData);
		out.writeTo(new FileOutputStream(new File(filePath+keyFrameDir+keyFrameName+"_"+file+"_m"+fileSuffix)));
		out.close();
		
	}
	
	
	
	/**
	 * ���ص�ǰ�α��λ��
	 * @return
	 */
	static String showPosition(){
		String lop = String.valueOf(Integer.toHexString(op & 0XFFFF));
		int hop = op/65535;
		String hopStr = String.valueOf(Integer.toHexString(hop & 0XFFFF));
		hopStr=hopStr+lop;
		String p = tips.substring(0, 10-hopStr.length());
		return p+hopStr;
	}
	
	
	/**
	 * ��һ��Tag��ƫ��ֵ
	 * @param b
	 * @param offLenght
	 * @return
	 */
	protected static int nextTagStartOp(byte[] b,int offLenght){
		String tagDataLength = byte2hexString(b[offLenght+1]);
		tagDataLength+=byte2hexString(b[offLenght+2]);
		tagDataLength+=byte2hexString(b[offLenght+3]);
//	    System.out.println("Tag data Size :"+tagDataLength);
		return Integer.parseInt(tagDataLength, 16)+11+4;
	}
	
	/**
	 *   ��5-7�ֽ�ΪUI24���͵�ֵ����ʾ��Tag��ʱ�������λΪms������һ��Tag��ʱ�������0��
	 * @param b
	 * @param offLenght
	 * @return
	 */
	protected static float timeMessage(byte[] b,int offLenght){
		String tagDataLength = byte2hexString(b[offLenght+4]);
		tagDataLength+=byte2hexString(b[offLenght+5]);
		tagDataLength+=byte2hexString(b[offLenght+6]);
//	    System.out.println("Tag data Size :"+tagDataLength);
		return Integer.parseInt(tagDataLength, 16)/1000;
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
	
	
	/**
	 * �������ݷ�ʽ
	 * @return
	 * @throws Exception
	 */
	public static byte[] loadFileByMappedByteBuffer() throws Exception{
		
		 
	       File f = new File(filePath+file+fileSuffix);
	       final int BUFFER_SIZE = 5*1024*1024;// ��������СΪ5M
	       /**
	       * map(FileChannel.MapMode mode,long position, long size)
	       * mode - �����ǰ�ֻ������ȡ/д���ר�ã�д��ʱ��������ӳ���ļ����ֱ�Ϊ FileChannel.MapMode ����������� READ_ONLY��READ_WRITE �� PRIVATE ֮һ
	       * position - �ļ��е�λ�ã�ӳ������Ӵ�λ�ÿ�ʼ������Ϊ�Ǹ���    
	       * size - Ҫӳ��������С������Ϊ�Ǹ����Ҳ����� Integer.MAX_VALUE
	       * ���������ȡ�ļ���벿�����ݣ���������д�������ȡ�ı���1/8���ݣ���Ҫ����дmap(FileChannel.MapMode.READ_ONLY, f.length()*7/8,f.length()/8)
	       * ���ȡ�ļ��������ݣ���Ҫ����дmap(FileChannel.MapMode.READ_ONLY, 0,f.length())
	       */
	       MappedByteBuffer inputBuffer = new RandomAccessFile(f, "r").getChannel().map(FileChannel.MapMode.READ_ONLY, 0,f.length());
	       byte[] dst = new byte[BUFFER_SIZE];// ÿ�ζ���3M������
	       long start = System.currentTimeMillis();
	       for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {
	           if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {
	              for (int i = 0; i < BUFFER_SIZE; i++)
	                  dst[i] = inputBuffer.get(offset + i);
	           } else {
	              for (int i = 0; i < inputBuffer.capacity() - offset; i++)
	                  dst[i] = inputBuffer.get(offset + i);
	           }
	           int length = (inputBuffer.capacity()%BUFFER_SIZE==0)?BUFFER_SIZE:inputBuffer.capacity()%BUFFER_SIZE;
	          
//	         System.out.println(new String(dst,0,length));//new String(dst,0,length)��������ȡ�����汣����ַ��������Զ�����в���
	       }
	       long end = System.currentTimeMillis();
	       System.out.println("��ȡ�ļ��ļ�һ�����ݻ��ѣ�"+(end-start)+"���� "+dst.length);
	       return dst;
	    }
		
	
	
	public static byte[] loadByByteBuffer() throws Exception{
		File _file = new File(filePath+file+fileSuffix);
		ByteBuffer byteBuf = ByteBuffer.allocate((int) _file.length());
        FileInputStream fis = new FileInputStream(_file);
//      FileOutputStream fos = new FileOutputStream("d://outFile.txt");
        FileChannel fc = fis.getChannel();
        long timeStar = System.currentTimeMillis();//�õ���ǰ��ʱ��
        fc.read(byteBuf);//1 ��ȡ
        long timeEnd = System.currentTimeMillis();//�õ���ǰ��ʱ��
       
        timeStar = System.currentTimeMillis();
//        fos.write(bbb);// д��
        timeEnd = System.currentTimeMillis();
//        fos.flush();
        fc.close();
        fis.close();
		return byteBuf.array();
	}
	
	
	
}
