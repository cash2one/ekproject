package getfile.util;


//导入bzip2.jar和jzlib相关java文件

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tools.bzip2.CBZip2InputStream;
import org.apache.tools.bzip2.CBZip2OutputStream;

import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;
/**
 * zip,gzip,bzip2,jzlib,
 * @author wing copy baidu
 *
 */
public class CopyOfzipHelper {

	/***************************************************************************
	 * 压缩GZip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] gZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data);
			gzip.finish();
			gzip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/***************************************************************************
	 * 解压GZip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unGZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
			baos.close();
			gzip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/***************************************************************************
	 * 压缩Zip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] zip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(bos);
			ZipEntry entry = new ZipEntry("zip");
			entry.setSize(data.length);
			zip.putNextEntry(entry);
			zip.write(data);
			zip.closeEntry();
			zip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/***************************************************************************
	 * 解压Zip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ZipInputStream zip = new ZipInputStream(bis);
			while (zip.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = zip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
				baos.close();
			}
			zip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/***************************************************************************
	 * 压缩BZip2
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] bZip2(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			CBZip2OutputStream bzip2 = new CBZip2OutputStream(bos);
			bzip2.write(data);
			bzip2.flush();
			bzip2.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/***************************************************************************
	 * 解压BZip2
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unBZip2(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			CBZip2InputStream bzip2 = new CBZip2InputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = bzip2.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
			baos.close();
			bzip2.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	public static String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase()+" ");
		}
		return sb.toString();
	}

	/**
	 * 压缩数据
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] jzlib(byte[] object) {

		byte[] data = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ZOutputStream zOut = new ZOutputStream(out, JZlib.Z_DEFAULT_COMPRESSION);
			DataOutputStream objOut = new DataOutputStream(zOut);
			objOut.write(object);
			objOut.flush();
			zOut.close();
			data = out.toByteArray();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 解压被压缩的数据
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] unjzlib(byte[] object) {

		byte[] data = null;
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(object);
			ZInputStream zIn = new ZInputStream(in);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = zIn.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			data = baos.toByteArray();
			baos.flush();
			baos.close();
			zIn.close();
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	
	/**
	 * 压缩文件
	 * @param zipFileName    压缩后的文件名称
	 * @param inputFileName  输入文件名或目录名称
	 * @throws Exception
	 * 
	 */
	public static void zip(String zipFileName, String inputFileName) throws Exception{
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
	
	
	
	public static void main(String[] args) {
		String s = "MDEzMW5iIDEwNjU3MDYxICAgIDEzODAwMTM4MDE5IDQ0NCAgICAgICAgINfwvrS1xNCj0bbNqNPDu6ejrMT6usOjrMT6tcTVy7rFbmJ5b3Vlcnl1YW7T2jIwMTAtMDYtMjEgMDk6MjI6MTi1x8K90KPRts2ovNLQo7ultq/GvcyooaMwMTMwamggMTA2NTcwNjEgICAgMTM4MjQ3OTg1NTggNDk5ICAgICAgICAg1/C+tLXE0KPRts2o08O7p6OsxPq6w6OsxPq1xNXLusVqaHpoYW5nc2Fu09oyMDEwLTA2LTIyIDE1OjAyOjMwtcfCvdCj0bbNqLzS0KO7pbavxr3MqKGjMDEzMHpzIDEwNjU3MDYxICAgIDEzODI0Nzk4NTYwIDUwMiAgICAgICAgINfwvrS1xNCj0bbNqNPDu6ejrMT6usOjrMT6tcTVy7rFenN6aGFuZ3NhbtPaMjAxMC0wNi0yMiAxNTo0Nzo1NrXHwr3Qo9G2zai80tCju6W2r8a9zKihozAxMzF6cyAxMDY1NzA2MSAgICAxMzgwMDEzODAyNSA1MDMgICAgICAgICDX8L60tcTQo9G2zajTw7uno6zE+rrDo6zE+rXE1cu6xXpzeW91ZXJ5dWFu09oyMDEwLTA2LTIyIDE1OjQ4OjI3tcfCvdCj0bbNqLzS0KO7pbavxr3MqKGjMDEzMXpzIDEwNjU3MDYxICAgIDEzODAwMTM4MDI1IDUwNCAgICAgICAgINfwvrS1xNCj0bbNqNPDu6ejrMT6usOjrMT6tcTVy7rFenN5b3Vlcnl1YW7T2jIwMTAtMDYtMjIgMTU6NDg6Mje1x8K90KPRts2ovNLQo7ultq/GvcyooaMwMTMxenMgMTA2NTcwNjEgICAgMTM4MDAxMzgwMjUgNTA3ICAgICAgICAg1/C+tLXE0KPRts2o08O7p6OsxPq6w6OsxPq1xNXLusV6c3lvdWVyeXVhbtPaMjAxMC0wNi0yMiAxNTo0OTozNbXHwr3Qo9G2zai80tCju6W2r8a9zKihozAxMzF6cyAxMDY1NzA2MSAgICAxMzgwMDEzODAyNSA1MDggICAgICAgICDX8L60tcTQo9G2zajTw7uno6zE+rrDo6zE+rXE1cu6xXpzeW91ZXJ5dWFu09oyMDEwLTA2LTIyIDE1OjQ5OjU5tcfCvdCj0bbNqLzS0KO7pbavxr3MqKGjMDEzMHpzIDEwNjU3MDYxICAgIDEzODAwMTM4MDM2IDUwOSAgICAgICAgINfwvrS1xNCj0bbNqNPDu6ejrMT6usOjrMT6tcTVy7rFenN6aG9uZ3h1ZdPaMjAxMC0wNi0yMiAxNTo1MDo0MrXHwr3Qo9G2zai80tCju6W2r8a9zKihozAxMzB6cyAxMDY1NzA2MSAgICAxMzgwMDEzODAzNiA1MTAgICAgICAgICDX8L60tcTQo9G2zajTw7uno6zE+rrDo6zE+rXE1cu6xXpzemhvbmd4dWXT2jIwMTAtMDYtMjIgMTU6NTA6NDK1x8K90KPRts2ovNLQo7ultq/GvcyooaM=";	
//		String Base64DataAscii;
//		try {
//			Base64DataAscii = new String(zipHelper.unjzlib(Base64.decode(s.getBytes())), "GB2312");
//			System.out.println("原文:"+Base64DataAscii);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (RuntimeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		byte[] b1 = zip(s.getBytes());
//		System.out.println("zip:" + bytesToHexString(b1));
//		byte[] b2 = unZip(b1);
//		System.out.println("unZip:" + new String(b2));
//
//		byte[] b3 = bZip2(s.getBytes());
//		System.out.println("bZip2:" + bytesToHexString(b3));
//		byte[] b4 = unBZip2(b3);
//		System.out.println("unBZip2:" + new String(b4));
//
//		byte[] b5 = gZip(s.getBytes());
//		System.out.println("bZip2:" + bytesToHexString(b5));
//		byte[] b6 = unGZip(b5);
//		System.out.println("unBZip2:" + new String(b6));
//
//		byte[] b7 = jzlib(s.getBytes());
//		System.out.println("jzlib:" + bytesToHexString(b7));
//		byte[] b8 = unjzlib(b7);
//		System.out.println("unjzlib:" + new String(b8));
	}
}
