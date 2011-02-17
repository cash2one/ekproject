package cn.elam.util.common;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * 
 * 邮件服务类
 * 
 * @author Ethan.Lam 2011-2-16
 * 
 */
public class MailBoxService {

	public static void main(String[] args) {
		MailBoxService src = new MailBoxService("ethanlamzs@sina.cn",
				"smtp.sina.cn", "ethanlamzs@sina.cn", "889276", "true",true);
		src.sendMail("ethanlamzs@gmail.com", "hello", "just a test <br /> testestest"+Math.random());
		
	}

	String mailAddress, smtpHost, userName, password, auth = "true";
	boolean isDebug = false;

	public MailBoxService(String mailAddress, String smtpHost, String userName,
			String password) {
		this.mailAddress = mailAddress;
		this.smtpHost = smtpHost;
		this.userName = userName;
		this.password = password;
	}

	
	public MailBoxService(String mailAddress, String smtpHost, String userName,
			String password, String auth) {
		this.mailAddress = mailAddress;
		this.smtpHost = smtpHost;
		this.userName = userName;
		this.auth = auth;
		this.password = password;
	}

	public MailBoxService(String mailAddress, String smtpHost, String userName,
			String password, String auth, boolean isDebug) {
		this.mailAddress = mailAddress;
		this.smtpHost = smtpHost;
		this.userName = userName;
		this.password = password;
		this.auth = auth;
		this.isDebug = isDebug;
	}

	/**
	 * 属性配置
	 * @param smtpHost
	 * @param auth
	 * @return
	 */
	Properties createProperties(String smtpHost, String auth) {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.auth", auth);
		return props;
	}

	
	/**
	 * 发送邮件
	 * @param receviceAddress
	 * @param title
	 * @param messageBody
	 */
	public void sendMail(String receviceAddress, String title,
			String messageBody) {
		Properties props = createProperties(smtpHost, auth);
		Session s = Session.getInstance(props);
		s.setDebug(isDebug);
		try {

			// 给消息对象设置发件人/收件人/主题/发信时间
			InternetAddress from = new InternetAddress(mailAddress);//

			MimeMessage message = new MimeMessage(s);
			message.setFrom(from);

			InternetAddress to = new InternetAddress(receviceAddress);

			message.setRecipient(Message.RecipientType.TO, to);
			message.setSubject(title);
			message.setSentDate(new Date());

			// 给消息对象设置内容
			BodyPart mdp = new MimeBodyPart();// 新建一个存放信件内容的BodyPart对象
			mdp.setContent(messageBody, "text/html;charset=gb2312");// 给BodyPart对象设置内容和格式/编码方式
			Multipart mm = new MimeMultipart();// 新建一个MimeMultipart对象用来存放BodyPart对
			// 象(事实上可以存放多个)

			mm.addBodyPart(mdp);// 将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
			message.setContent(mm);// 把mm作为消息对象的内容
			message.saveChanges();

			Transport transport = s.getTransport("smtp");
			transport.connect(smtpHost, userName, password);// 163邮箱的用户名和密码
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (MessagingException e) {
			System.out.println(e.toString());
		}

	}

	
	
	/**
	 * 测试方法
	 */
	private static void jmail2() {
		try {

			String tto = "ethanlamzs@gmail.com";
			String ttitle = "测试jmail";
			String tcontent = "测试成功";

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");// 选择163服务器
			props.put("mail.smtp.auth", "true");

			Session s = Session.getInstance(props);
			s.setDebug(true);
			MimeMessage message = new MimeMessage(s);

			// 给消息对象设置发件人/收件人/主题/发信时间
			InternetAddress from = new InternetAddress("alex86825@163.com");// 必须拥有163邮箱
			message.setFrom(from);

			InternetAddress to = new InternetAddress(tto);

			message.setRecipient(Message.RecipientType.TO, to);
			message.setSubject(ttitle);
			message.setSentDate(new Date());

			// 给消息对象设置内容
			BodyPart mdp = new MimeBodyPart();// 新建一个存放信件内容的BodyPart对象
			mdp.setContent(tcontent, "text/html;charset=gb2312");// 给BodyPart对象设置内容和格式/编码方式
			Multipart mm = new MimeMultipart();// 新建一个MimeMultipart对象用来存放BodyPart对
			// 象(事实上可以存放多个)
			mm.addBodyPart(mdp);// 将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
			message.setContent(mm);// 把mm作为消息对象的内容
			message.saveChanges();

			Transport transport = s.getTransport("smtp");
			transport.connect("smtp.163.com", "alex86825", "");// 163邮箱的用户名和密码
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (MessagingException e) {
			System.out.println(e.toString());
		}

	}
}
