package qtone.xxt.video;

import java.io.File;

import qtone.xxt.video.fetch.VideoFile;

/**
 * 
 * 程序入口
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
    		 System.out.println("程序需要指定以下的输入参数：");
    		 System.out.println(" -rp : 设置web访问的绝对路径 ，如   /data/adobe/fms4/webroot/vod/");
    		 System.out.println(" -sp : 设置web访问的相对路径 ，如    course/cp2/cs20059/video");
    		 System.out.println(" -f ：设置视频源文件名，如  20059_1.flv ");
//    		 System.out.println(" -at ：视频源文件的总播放时长（秒），如 120 ");
    		 System.out.println(" -pt ：视频源文件的分段播放时长（秒），如 10 ");
    		 System.out.println(" -c : (可选项目)  加密后的目录后缀名");
    		 System.out.println(" -w : (可选项目)  设置web访问的绝对路径 ，如   /v2t");
    		 return;
    	 }else{
    		 //进行复制操作
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
    			 
    			
    			 ///非必须参数
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
    			 System.out.println("程序输入的参数有误，请检查！");
    			 return;
    		 }
    	 }
    	 
        	 
    	
    	 
    	 String targetName = (srcfileName.indexOf(".")>0?srcfileName.substring(0,srcfileName.indexOf(".")):srcfileName);
    	 String fileSuffix = srcfileName.indexOf(".")>0?srcfileName.substring(srcfileName.indexOf(".")+1):FILE_TYPE;
    	 String outDescFileDir = "";
       	 
       	 //解析文件
       	 VideoFile.createFileDescription(subPath,targetName,fileSuffix,outDescFileDir,encodeDirName);
    	 
         String srcFilesPath = Main_VIDEO_DIR_ROOT+subPath+File.separator+targetName;
         outDescFileDir = Main_VIDEO_DIR_ROOT+subPath+File.separator+targetName+encodeDirName;
         
         //加密文件
         VideoEncrypt.encodeFiles(srcFilesPath,"",outDescFileDir);
         
     }
	
	 public  static boolean paramCheck(String inputStr){
		 if("-rp,-sp,-f,-c,-pt,-at,-w,".indexOf(inputStr+",")>=0)
			 return false;
		 else
			 return true;
	 }
     
	
}
