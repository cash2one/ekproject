package web;

import java.io.UnsupportedEncodingException;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
/** 
 * 
 * @author yunchang Yuan 
 */  
public class XmlSocketHandler extends  IoHandlerAdapter{  
	
    static Logger logger = LoggerFactory.getLogger(XmlSocketHandler.class);  
    static String security_req = "<policy-file-request/>";  
    static String allow_all =  "<cross-domain-policy>\n"+  
                              "  <allow-access-from  domain=\"*\"  to-ports=\"*\"  />\n"+  
                              "</cross-domain-policy>";  
      
    @Override  
    public void exceptionCaught(IoSession arg0, Throwable arg1) throws Exception {  
        super.exceptionCaught(arg0, arg1);  
    }  
  
    @Override  
    public void messageReceived(IoSession arg0, Object arg1) throws Exception {  
    	
    	
        IoBuffer buf = (IoBuffer)arg1;  
        IoBuffer processBuf = (IoBuffer)arg0.getAttribute("processBuf");  
          
        processBuf.put(buf);  
        processBuf.flip();  
        String req = getReq(processBuf);  
        System.out.println("messageReceived---->"+req+" Requset By remoteAddress:"+arg0.getRemoteAddress());
        if(req!=null){  
            if(security_req.equals(req)){  
                logger.info("get security req,now send policy file");   
                byte[] reps = allow_all.getBytes("UTF-8");  
                IoBuffer wb = IoBuffer.allocate(reps.length+1);  
                wb.put(reps);  
                wb.put((byte)0x0);  
                wb.flip();  
                arg0.write(wb);  
                arg0.setAttribute("policySend", true)    ;
                System.out.println("已经把策略文件送出去...."+arg0.getRemoteAddress());
            }  
        }  
          
    }  
  
    @Override  
    public void messageSent(IoSession arg0, Object arg1) throws Exception {  
        logger.info("messageSent");  
        arg0.close();  
    }  
  
    @Override  
    public void sessionClosed(IoSession arg0) throws Exception {          
        logger.info("sessionClosed");      
        super.sessionClosed(arg0);  
        arg0.removeAttribute("processBuf");  
    }  
  
    @Override  
    public void sessionCreated(IoSession arg0) throws Exception {  
        super.sessionCreated(arg0);  
        IoBuffer processBuf = IoBuffer.allocate(64);  
        arg0.setAttribute("processBuf", processBuf);  
    }  
  
    @Override  
    public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {  
        super.sessionIdle(arg0, arg1);  
    }  
  
    @Override  
    public void sessionOpened(IoSession arg0) throws Exception {  
        super.sessionOpened(arg0);  
    }  
  
    private String getReq(IoBuffer buf) {  
        IoBuffer reqbuf = IoBuffer.allocate(64);  
        boolean found = false;  
        for(int i=0;i<buf.limit();i++){  
            byte data = buf.get();  
            if(data!=0){  
                reqbuf.put(data);  
            }else{  
                found = true;  
                break;  
            }  
        }  
        if(found){  
            logger.info("get xml document");  
            reqbuf.flip();  
            buf.compact();  
            try {  
                byte[] strbuf = new byte[reqbuf.limit()];  
                reqbuf.get(strbuf,0,reqbuf.limit());  
                String req = new String(strbuf, "UTF-8");  
                logger.info("req is :"+req);  
                return req;  
            } catch (UnsupportedEncodingException ex) {  
                logger.error("encoding error");  
                return null;  
            }  
              
        }else{  
              
            return null;  
        }  
          
    }  
      
}  