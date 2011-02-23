package cn.elam.testcases.db;

import cn.elam.util.common.MailBoxService;

public class TestSendMail {

	public static void main(String[] args) {
		MailBoxService src = new MailBoxService("ethanlamzs@sina.cn",
				"smtp.sina.cn", "ethanlamzs@sina.cn", "889276", "true",true);
		src.sendMail("ethanlamzs@gmail.com", "hello", "just a test <br /> test.send email"+Math.random());
	}

}
