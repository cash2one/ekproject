package cn.elamzs.common.base.files;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * �ļ��ϴ���������
 * @author Ethan.Lam 2011-2-10
 * 
 */
public class FileUploadService implements FileUploadHandle{

	private int maxMemorySize;   //MB
	private String tempDirectory;
	private int maxRequestSize;  //MB

	private FileUploadHandle handle = null;
	
	public FileUploadService(String tempDirectory,int maxMemorySize,int maxRequestSize) {
        this.tempDirectory = tempDirectory;
        this.maxMemorySize = maxMemorySize*1024*1024;
        this.maxRequestSize = maxRequestSize*1024*1024;
        this.handle = null;
	}

	public FileUploadService(FileUploadHandle handle,String tempDirectory) {
        this.maxMemorySize = maxMemorySize*1024*1024;
        this.maxRequestSize = maxRequestSize*1024*1024;
        this.handle = handle;
	}
	
	
	public FileUploadService(FileUploadHandle handle,String tempDirectory,int maxMemorySize,int maxRequestSize) {
        this.tempDirectory = tempDirectory;
        this.maxMemorySize = maxMemorySize*1024*1024;
        this.maxRequestSize = maxRequestSize*1024*1024;
        this.handle = handle;
	}

	
	/**
	 * �����ϴ����¼�
	 * @param request
	 * @param dir
	 * @throws Exception
	 */
	public final void uploadFile(HttpServletRequest request, String dir)
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
                
                String newFileName = fileNamingRule(fileName);
                item.write(new File(tempDirectory+"/"+newFileName)); 
                
                //�������ļ��ϴ����ִ�е�ִ�еĶ���
                forEachUploadFinishedEvent(new File(tempDirectory+"/"+newFileName),fileName,newFileName);
            }
		}
		
		
	}
	
	
	/**
	 * �ļ�����������
	 * @param oldFileName
	 * @return
	 */
    public  String fileNamingRule(String oldFileName){
    	String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
    	return System.currentTimeMillis()+suffix;
    } 	 
  	
	
	
   /**
    * ������ʾ����
    * @param upload
    */
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
			           System.out.println("So far, " + pBytesRead + " bytes have been read. At "+new Date().toLocaleString());
			       } else {
			           System.out.println("So far, " + pBytesRead + " of " + pContentLength
			                              + " bytes have been read. At "+new Date().toLocaleString());
			       }
			   }
			};
			upload.setProgressListener(progressListener);
	}
	
	
	/**
	  * �������ļ��ϴ����ִ�е�ִ�еĶ���
	  * @param file
	  * @param oldFileName
	  * @param newFileName
	*/
	public void forEachUploadFinishedEvent(File file,String oldFileName,String newFileName){
		  if(handle!=null)
			  handle.forEachUploadFinishedEvent(file,oldFileName, newFileName);
		  else{
			  //Ĭ�ϵĴ���ʱ��
              System.out.println("src:"+oldFileName+"   new "+newFileName);
		  }
	}
	
	
    public static void main(String...args){
    	String tempDirectory = "d:/test";
    	
    	FileUploadService src  = new FileUploadService(new FileUploadHandle(){
 
    		private int t = 0;
    		
			@Override
			public void forEachUploadFinishedEvent(File file, String oldFileName,
					String newFileName) {
				
				
				
			}
			
    	}, tempDirectory);
    }
	
}
