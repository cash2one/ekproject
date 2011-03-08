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
 * 
 * 文件上传服务处理类
 * @author Ethan.Lam 2011-2-10
 * 
 */
public class FileUploadService implements FileUploadHandle{

	private int maxMemorySize;   //MB
	private String tempDirectory;
	private int maxRequestSize;  //MB

	private FileUploadHandle handle = null;
	
	/**
	 * 
	 * @param tempDirectory
	 * @param maxMemorySize
	 * @param maxRequestSize
	 */
	public FileUploadService(String tempDirectory,int maxMemorySize,int maxRequestSize) {
		this(null,tempDirectory,maxMemorySize, maxRequestSize);
	}

	/**
	 * 
	 * @param handle
	 * @param tempDirectory
	 */
	public FileUploadService(FileUploadHandle handle,String tempDirectory) {
        this(handle,tempDirectory,12,10);
	}
	
	/**
	 * 
	 * @param handle
	 * @param tempDirectory
	 * @param maxMemorySize
	 * @param maxRequestSize
	 */
	public FileUploadService(FileUploadHandle handle,String tempDirectory,int maxMemorySize,int maxRequestSize) {
        this.tempDirectory = tempDirectory;
        File _dir = new File(tempDirectory);
        if(!_dir.exists())
            _dir.mkdirs();
        
        this.maxMemorySize = maxMemorySize*1024*1024;
        this.maxRequestSize = maxRequestSize*1024*1024;
        this.handle = handle;
	}

	
	/**
	 * 开始处理文件上传
	 * @param request
	 * @param dir
	 * @throws Exception
	 */
	public final void uploadFile(HttpServletRequest request)
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
                
                //当单个文件上传完后执行的执行的动作
                forEachFileUploadFinishedEvent(new File(tempDirectory+"/"+newFileName),fileName,newFileName);
                
            }
		}
		
		
	}
	
	
	/**
	 * 文件重命名规则
	 * @param oldFileName
	 * @return
	 */
    public  String fileNamingRule(String oldFileName){
    	String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
    	return System.currentTimeMillis()+Math.round(Math.random()*100)+suffix;
    } 	 
  	
	
	
   /**
    * 进度提示函数
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
			           System.out.println("So far, " + pBytesRead + " bytes have been read 100% . At "+new Date().toLocaleString());
			       } else {
			           System.out.println("So far, " + pBytesRead + " of " + pContentLength
			                              + " bytes have been read."+(float)((pBytesRead/pContentLength)*100)+"% At "+new Date().toLocaleString());
			       }
			   }
			};
			upload.setProgressListener(progressListener);
	}
	
	
	/**
	  * 当单个文件上传完后执行的执行的动作
	  * @param file
	  * @param oldFileName
	  * @param newFileName
	*/
	public void forEachFileUploadFinishedEvent(File file,String oldFileName,String newFileName){
		  if(handle!=null)
			  handle.forEachFileUploadFinishedEvent(file,oldFileName, newFileName);
		  else{
			  //默认的处理时间
              System.out.println("src:"+oldFileName+"   new "+newFileName);
		  }
	}
	
	
    public static void main(String...args){
    	String tempDirectory = "d:/test";
    	
    	FileUploadService src  = new FileUploadService(new FileUploadHandle(){
 
    		private int t = 0;
    		
			@Override
			public void forEachFileUploadFinishedEvent(File file, String oldFileName,
					String newFileName) {
				
				
				
			}
			
    	}, tempDirectory);
    }
	
}
