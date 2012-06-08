package qtone.xxt.video;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 
 * 视频加密
 * @author Ethan.Lam  2012-5-17
 *
 */
public class VideoEncrypt {

	
//	private static  String filePath = "D:/Workspaces/google/ekproject/truck/HTML5/WebContent/flv/06_1/";
	private static  String filePath = "E:/elam/my_jar_project/CodeGenerator/WebRoot/flv/test/case/";
	private static  String file = "3";
	private static  String fileSuffix = ".flv";
	private static  String enCodeName ="_e";
	private static  String deCodeName = "_d";
	private static  int _encryptHeadSize = 100;
	
	public static void main(String...arg) throws Exception{
		
		File _fileDir = new File(filePath);
		for(File _file:_fileDir.listFiles()){
		   if(_file.getName().substring(_file.getName().indexOf(".")).equals(fileSuffix)){
			   file = _file.getName().substring(0, _file.getName().indexOf("."));
			   encodeFile(filePath+"/nencrypt");
			   break;
		   }
		}
		System.out.println("视频文件夹中的文件已被加密.....");
		
	}
	
	
	public static void encodeFiles(String srcFilesPath,String enCodeFileName,String outPutFilesDir) throws Exception {
		enCodeName = enCodeFileName;
		filePath = srcFilesPath+File.separator;
		File _fileDir = new File(srcFilesPath);
		for(File _file:_fileDir.listFiles()){
		   if(_file.getName().substring(_file.getName().indexOf(".")).equals(fileSuffix)){
			   file = _file.getName().substring(0, _file.getName().indexOf("."));
			   encodeFile(outPutFilesDir);
		   }
		}
		System.out.println("视频文件夹中的文件已被加密.....");
//		deCodeFile( );
	}
	
	
	
	private static void encodeFile(String outputFileDir) throws Exception {
//		String filePath = "D:/Workspaces/google/ekproject/truck/HTML5/WebContent/flv/06_1/";
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath+file+fileSuffix));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
//		System.out.println("Available bytes:" + in.available());
		
        isHeadEncrypt = false;
        
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
//			System.out.println("本次加密的长度..."+size+" ");
//			out.write(enCodeDatas(temp), 0, size);
			out.write(enCodeDatasPrePart(temp), 0, size);
		}
		in.close();
		
		outputFileDir = outputFileDir==null||"".equals(outputFileDir)?filePath:outputFileDir;
		
		byte[] content = out.toByteArray();
//		System.out.println(outputFileDir+File.separator+file+enCodeName+fileSuffix);
		
		File _file = new File(outputFileDir+File.separator+file+enCodeName+fileSuffix);
		if(!_file.exists()){
			new File(outputFileDir).mkdirs();
		}
		_file.createNewFile();
		
		out.writeTo(new FileOutputStream(_file));
		out.close();
		System.out.println("生成视频加密文件 ["+file+fileSuffix+"] path:"+(outputFileDir+File.separator+file+enCodeName+fileSuffix)+"。");
//		System.out.println("Readed bytes count:" + content.length);
		
	}
	
	
	private static void deCodeFile( ) throws Exception {
//		String filePath = "D:/Workspaces/google/ekproject/truck/HTML5/WebContent/flv/06_1/";
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath+file+enCodeName+fileSuffix));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
//		System.out.println("Available bytes:" + in.available());
		
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			out.write(deCodeDatas(temp), 0, size);
		}
		in.close();
		
		byte[] content = out.toByteArray();
		out.writeTo(new FileOutputStream(new File(filePath+file+deCodeName+fileSuffix)));
		out.close();
//		System.out.println("Readed bytes count:" + content.length);
	}
	
	//整个文件加密
	private static byte [] enCodeDatas(byte [] src){
		if(src==null || src.length<=0)
			return null;
		
		for(int i = 0;i<src.length;i++){
			int temp = ((int) src[i]) & 0xff;
			temp = temp ^ 2;
			src[i] = (byte) temp;
		}
		return src;
	}
	
	static boolean isHeadEncrypt = false; //头部信息已经加密处理了
	
	//只加密头部
	private static byte [] enCodeDatasPrePart(byte [] src){
		
		if(src==null || src.length<=0)
			return null;
		
		if(isHeadEncrypt)
			return src;
		
		for(int i = 0;i<src.length;i++){
			if(!isHeadEncrypt && i < _encryptHeadSize){
				int temp = ((int) src[i]) & 0xff;
				temp = temp ^ 2;
				src[i] = (byte) temp;
			}else
				isHeadEncrypt = true;
		}
		
		return src;
	}
	
	
    private static byte[] deCodeDatas(byte [] src){
		if(src==null || src.length<=0)
			return null;
		
		for(int i = 0;i<src.length;i++){
			int temp = (int) src[i] & 0xff;
			temp = temp ^ 2;
			src[i] = (byte) temp;
		}
		return src;
    }

}
