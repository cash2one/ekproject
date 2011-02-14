package cn.elamzs.common.base.files.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Date;
/**
 * 
 * 文件操作工具
 * 1、复制文件
 * 2、移动文件
 * 
 * @author Ethan.Lam  2011-2-14
 *
 */
public class FileOperateUtil {

	
	/**
	 * 传统IO的方式
	 * @param f1
	 * @param f2
	 * @return
	 * @throws Exception
	 */
	 static long forJava(File f1, File f2) throws Exception {
		long time = new Date().getTime();
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		byte[] buffer = new byte[length];
		while (true) {
			int ins = in.read(buffer);
			if (ins == -1) {
				in.close();
				out.flush();
				out.close();
				return new Date().getTime() - time;
			} else
				out.write(buffer, 0, ins);
		}
	}

	/**
	 * 管道的方式
	 * @param f1
	 * @param f2
	 * @return
	 * @throws Exception
	 */
	 static long forTransfer(File f1, File f2) throws Exception {
		long time = new Date().getTime();
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();
		int i = 0;
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
				return new Date().getTime() - time;
			}
			if ((inC.size() - inC.position()) < 20971520)
				length = (int) (inC.size() - inC.position());
			else
				length = 20971520;
			inC.transferTo(inC.position(), length, outC);
			inC.position(inC.position() + length);
			i++;
		}
	}

	
	/**
	 * 镜像的方式
	 * @param f1
	 * @param f2
	 * @return
	 * @throws Exception
	 */
	 static long forImage(File f1, File f2) throws Exception {
		long time = new Date().getTime();
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		RandomAccessFile out = new RandomAccessFile(f2, "rw");
		FileChannel inC = in.getChannel();
		MappedByteBuffer outC = null;
		MappedByteBuffer inbuffer = null;
		byte[] b = new byte[length];
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.force();
				out.close();
				return new Date().getTime() - time;
			}
			if ((inC.size() - inC.position()) < length) {
				length = (int) (inC.size() - inC.position());
			} else {
				length = 20971520;
			}
			b = new byte[length];
			inbuffer = inC.map(MapMode.READ_ONLY, inC.position(), length);
			inbuffer.load();
			inbuffer.get(b);
			outC = out.getChannel().map(MapMode.READ_WRITE, inC.position(),
					length);
			inC.position(b.length + inC.position());
			outC.put(b);
			outC.force();
		}
	}

	
	/**
	 * 管道对管道的方式
	 * @param f1
	 * @param f2
	 * @return
	 * @throws Exception
	 */
	 static long forChannel(File f1, File f2) throws Exception {
		long time = new Date().getTime();
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();
		ByteBuffer b = null;
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
				return new Date().getTime() - time;
			}
			if ((inC.size() - inC.position()) < length) {
				length = (int) (inC.size() - inC.position());
			} else
				length = 2097152;
			b = ByteBuffer.allocateDirect(length);
			inC.read(b);
			b.flip();
			outC.write(b);
			outC.force(false);
		}
	}
	

	/**
	 * 复制文件
	 * @param src
	 * @param dest
	 * @throws Exception
	 */
	public static void copyFile(File src,File dest) throws Exception{
			if(!src.exists())
			   return;
			new File(dest.getParent()).mkdirs();
			if(dest.exists())
				dest.createNewFile();
			forTransfer(src,dest);
	}
		 
	 
   /**
    * 复制文件	
    * @param src
    * @param dest
    * @throws Exception
    */
	public static void copyFile(String src,String dest) throws Exception{
		File _f1 = new File(src);
		if(!_f1.exists())
		   return;
		
		File _f2  = new File(dest);
		new File(_f2.getParent()).mkdirs();
		if(_f2.exists())
			_f2.createNewFile();
		
		forTransfer(_f1,_f2);
	}
	
	

	/**
	 * 移动文件
	 * @param src
	 * @param dest
	 * @throws Exception
	 */
	public static void moveFile(File src,File dest) throws Exception{
		if(src.exists()){
		   copyFile(src, dest);
		   src.delete();
		}
	}
		 
	 
   /**
    * 移动文件	
    * @param src
    * @param dest
    * @throws Exception
    */
	public static void moveFile(String src,String dest) throws Exception{
		File _src = new File(src);
		if(_src.exists()){
		    moveFile(_src,new File(dest));
		   _src.delete();
	    }
	}
	
	
}
