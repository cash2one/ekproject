package qtone.xxt.video;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class VideoEncrypt {

	public static void main(String... arg) throws Exception {
//		byte[] x = new byte[] { 53 };
//		int temp = ((int) x[0]) & 0xff;
//		temp = temp ^ 2;
//		byte bx = (byte) temp;
//
//		temp = (int) bx & 0xff;
//		temp = temp ^ 2;
//		byte bx2 = (byte) temp;
//		System.out.println(bx2);
//		System.out.println(x[0]);
		encodeFile( );
		deCodeFile( );
	}
	
//	private static  String filePath = "D:/Workspaces/google/ekproject/truck/HTML5/WebContent/flv/06_1/";
	private static  String filePath = "E:/elam/my_jar_project/CodeGenerator/WebRoot/flv/06_1/";
	private static  String file = "3";
	private static  String fileSuffix = ".flv";
	private static  String enCodeName ="_e";
	private static  String deCodeName = "_d";
	
	
	public static void encodeFile( ) throws Exception {
//		String filePath = "D:/Workspaces/google/ekproject/truck/HTML5/WebContent/flv/06_1/";
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath+file+fileSuffix));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		System.out.println("Available bytes:" + in.available());
		
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			out.write(enCodeDatas(temp), 0, size);
		}
		in.close();
		
		byte[] content = out.toByteArray();
		out.writeTo(new FileOutputStream(new File(filePath+file+enCodeName+fileSuffix)));
		out.close();
		System.out.println("Readed bytes count:" + content.length);
	}
	
	
	public static void deCodeFile( ) throws Exception {
//		String filePath = "D:/Workspaces/google/ekproject/truck/HTML5/WebContent/flv/06_1/";
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath+file+enCodeName+fileSuffix));
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		System.out.println("Available bytes:" + in.available());
		
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			out.write(deCodeDatas(temp), 0, size);
		}
		in.close();
		
		byte[] content = out.toByteArray();
		out.writeTo(new FileOutputStream(new File(filePath+file+deCodeName+fileSuffix)));
		out.close();
		System.out.println("Readed bytes count:" + content.length);
	}
	
	
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
