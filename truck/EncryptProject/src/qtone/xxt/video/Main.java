package qtone.xxt.video;

import java.io.File;

import qtone.xxt.video.fetch.VideoFile;

/**
 * 
 * �������
 * @author Ethan.Lam  2012-5-22
 *
 */
public class Main {

	public static String Main_VIDEO_DIR_WEB_CONTEXT ="/";
	public static String Main_VIDEO_DIR_WEB_ROOT ="flv/test";
	public static String Main_VIDEO_DIR_ROOT ="E:/elam/my_jar_project/CodeGenerator/WebRoot/";
	public static int PieceDurationSet = 60;
	public static String ENCRYPT ="true";
	public static String FILE_TYPE = ".flv";
	public static String CRLF="\n";
	/**
	 * 
	 * @author Ethan.Lam  2012-5-22
	 * @param args
	 * @throws Exception 
	 * 
	 */
     public static void main(String...args) throws Exception{
    	 
       	 String subPath="flv/test";          // - sp
    	 String srcfileName = "case.flv";   // -f
    	 String encodeDirName = "_encode";  // -c
    	 int videDuration = 600;            // -t
    	 
        //  -rp E:/elam/my_jar_project/CodeGenerator/WebRoot/  -sp  flv/test -w http://192.168.4.39/code -f  case.flv  -pt 60
    	 // -rp 
    	 
    	 if(args==null||args.length==0){
    		 System.out.println("������Ҫָ�����µ����������");
    		 System.out.println(" -rp : ����web���ʵľ���·�� ����   /data/adobe/fms4/webroot/vod/");
    		 System.out.println(" -sp : ����web���ʵ����·�� ����    course/cp2/cs20059/video");
    		 System.out.println(" -f ��������ƵԴ�ļ�������  20059_1.flv ");
//    		 System.out.println(" -at ����ƵԴ�ļ����ܲ���ʱ�����룩���� 120 ");
    		 System.out.println(" -pt ����ƵԴ�ļ��ķֶβ���ʱ�����룩���� 10 ");
    		 System.out.println(" -c : (��ѡ��Ŀ)  ���ܺ��Ŀ¼��׺��");
    		 System.out.println(" -w : (��ѡ��Ŀ)  ����web���ʵľ���·�� ����   /v2t");
    		 return;
    	 }else{
    		 //���и��Ʋ���
    		 int paramsCount = 0;
    		 for(int i =0 ;i<args.length;i+=2){
    			 if("-rp".equals(args[i].trim())){
    				 if(paramCheck(args[i+1])){
	    				 Main_VIDEO_DIR_ROOT = args[i+1];
	    				 paramsCount+=10;
    				 }
    			 }
    			 if("-sp".equals(args[i].trim())){
    				 if(paramCheck(args[i+1])){
    				     subPath = args[i+1];
    				     Main_VIDEO_DIR_WEB_ROOT = subPath;
    					 paramsCount+=10;
    				 }
    			 }
    			 if("-f".equals(args[i].trim())){
    				 if(paramCheck(args[i+1])){
    				     srcfileName = args[i+1];
    					 paramsCount+=10;
    				 }
    			 }
    			 
    
    			 if("-pt".equals(args[i].trim())){
    				 if(paramCheck(args[i+1])){
    					 PieceDurationSet = Integer.parseInt(args[i+1]);
    					 paramsCount+=10;
    				 }
    			 }
    			 
    			
    			 ///�Ǳ������
    			 if("-c".equals(args[i].trim())){
    				 if(paramCheck(args[i+1])){
    					 encodeDirName = args[i+1];
    					 paramsCount++;
    				 }
    			 }

    			 if("-w".equals(args[i].trim())){
    				 if(paramCheck(args[i+1])){
    					 Main_VIDEO_DIR_WEB_CONTEXT = args[i+1];
    					 paramsCount++;
    				 }
    			 }
    		 }
    		 if(paramsCount<40){
    			 System.out.println("��������Ĳ����������飡");
    			 return;
    		 }
    	 }
    	 
        	 
    	
    	 
    	 String targetName = (srcfileName.indexOf(".")>0?srcfileName.substring(0,srcfileName.indexOf(".")):srcfileName);
    	 String fileSuffix = srcfileName.indexOf(".")>0?srcfileName.substring(srcfileName.indexOf(".")+1):FILE_TYPE;
    	 String outDescFileDir = "";
       	 
       	 //�����ļ�
       	 VideoFile.createFileDescription(subPath,targetName,fileSuffix,outDescFileDir,encodeDirName);
    	 
         String srcFilesPath = Main_VIDEO_DIR_ROOT+subPath+File.separator+targetName;
         outDescFileDir = Main_VIDEO_DIR_ROOT+subPath+File.separator+targetName+encodeDirName;
         
         //�����ļ�
         VideoEncrypt.encodeFiles(srcFilesPath,"",outDescFileDir);
         
     }
	
	 public  static boolean paramCheck(String inputStr){
		 if("-rp,-sp,-f,-c,-pt,-at,-w,".indexOf(inputStr+",")>=0)
			 return false;
		 else
			 return true;
	 }
     
	
}
