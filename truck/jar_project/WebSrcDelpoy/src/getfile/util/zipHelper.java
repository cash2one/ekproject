package getfile.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.tools.zip.ZipOutputStream;


//����bzip2.jar��jzlib���java�ļ�

 
/**
 * zip
 * @author ethan copy baidu
 *
 */
public class zipHelper {

	
	/**
	 * ѹ���ļ�
	 * @param zipFileName    ѹ������ļ�����
	 * @param inputFileName  �����ļ�����Ŀ¼����
	 * @throws Exception
	 * 
	 */
	public static void zip(String zipFileName, String inputFileName) throws Exception{
		File zipFile = new File(zipFileName);
	    zipFile.deleteOnExit();
		File inputFile = new File(inputFileName);
	    if(!inputFile.exists()){
	    	System.out.println("Ŀ���ļ������ڣ�����ִ��ѹ�����������");
	    	return;
	    }
		zip(zipFileName,inputFile);
	}
	
	/**
	 * ִ��ѹ������
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
	 * ����ѹ���ļ�
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
		zipHelper.zip("C:/Users/Solosus/Desktop/��ʦƽ̨�ĵ�/deploy/test.zip", "C:/Users/Solosus/Desktop/��ʦƽ̨�ĵ�/deploy/1123_2");
	}
}
