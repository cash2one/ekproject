package cn.elamzs.common.eimport.inter;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 文件上传服务
 * 
 * @author Ethan.Lam 2011-2-10
 * 
 */
public class FileUploadService {

	private int maxMemorySize;   //MB
	private String tempDirectory;
	private int maxRequestSize;  //MB

	
	public FileUploadService(String tempDirectory,int maxMemorySize,int maxRequestSize) {
        this.tempDirectory = tempDirectory;
        this.maxMemorySize = maxMemorySize*1024*1024;
        this.maxRequestSize = maxRequestSize*1024*1024;
	}

	
	public void uploadFile(HttpServletRequest request, String dir)
			throws Exception {
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		DiskFileItemFactory factory = new DiskFileItemFactory();

		factory.setSizeThreshold(maxMemorySize);
		factory.setRepository(new File(tempDirectory));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxRequestSize);
		
		addProcessListener(upload);
		
		List /* FileItem */items = upload.parseRequest(request);
		
		Iterator iter = items.iterator();
		while (iter.hasNext()) {
		    FileItem item = (FileItem) iter.next();
            if(!item.isFormField()){
            	
            	String fieldName = item.getFieldName();
                String fileName = item.getName();
                String contentType = item.getContentType();
                boolean isInMemory = item.isInMemory();
                long sizeInBytes = item.getSize();
                
                if(fileName==null||"".equals(fileName))
                	continue;
                
                item.write(new File(tempDirectory+"/"+fileName)); 
               
                uploadFileFinished();
            }
		}
		
		
	}
	
	protected void addProcessListener(ServletFileUpload upload){
		ProgressListener progressListener = new ProgressListener(){
			   private long megaBytes = -1;
			   public void update(long pBytesRead, long pContentLength, int pItems) {
			       long mBytes = pBytesRead / 1000000;
			       if (megaBytes == mBytes) {
			           return;
			       }
			       megaBytes = mBytes;
			       System.out.println("We are currently reading item " + pItems);
			       if (pContentLength == -1) {
			           System.out.println("So far, " + pBytesRead + " bytes have been read.");
			       } else {
			           System.out.println("So far, " + pBytesRead + " of " + pContentLength
			                              + " bytes have been read.");
			       }
			   }
			};
			upload.setProgressListener(progressListener);
	}
	
	public void uploadFileFinished(){

		
	}

}



class FileInfos {

	private String name;
	private String id;
	private String path;

	public FileInfos() {

	}

}