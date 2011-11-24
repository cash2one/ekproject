package getfile.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.tools.zip.ZipOutputStream;


//导入bzip2.jar和jzlib相关java文件

 
/**
 * zip
 * @author ethan copy baidu
 *
 */
public class zipHelper {

	
	/**
	 * 压缩文件
	 * @param zipFileName    压缩后的文件名称
	 * @param inputFileName  输入文件名或目录名称
	 * @throws Exception
	 * 
	 */
	public static void zip(String zipFileName, String inputFileName) throws Exception{
		File zipFile = new File(zipFileName);
	    zipFile.deleteOnExit();
		File inputFile = new File(inputFileName);
	    if(!inputFile.exists()){
	    	System.out.println("目标文件不存在，不能执行压缩打包操作！");
	    	return;
	    }
		zip(zipFileName,inputFile);
	}
	
	/**
	 * 执行压缩操作
	 * @param zipFileName
	 * @param file
	 * @throws Exception 
	 */
	static void zip(String zipFileName, File inputFile) throws Exception{
		 ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
	     zip(out, inputFile, "");
	     System.out.println("zip done");
	     out.close();
	}
	
	/**
	 * 
	 * 迭代压缩文件
	 * @param out
	 * @param f
	 * @param base
	 * @throws Exception
	 */
	static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
           zip(out, fl[i], base + fl[i].getName());
         }
        }else {
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           int b;
           System.out.println(base);
           while ( (b = in.read()) != -1) {
            out.write(b);
         }
         in.close();
       }
    }
	
	
	
	public static void main(String[] args) throws Exception {
		zipHelper.zip("C:/Users/Solosus/Desktop/教师平台文档/deploy/test.zip", "C:/Users/Solosus/Desktop/教师平台文档/deploy/1123_2");
	}
}
