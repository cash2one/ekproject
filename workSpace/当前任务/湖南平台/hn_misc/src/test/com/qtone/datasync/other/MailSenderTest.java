package com.qtone.datasync.other;

import org.testng.annotations.Test;

import cn.qtone.mm7.common.mail.MailUtils;

/**
 * @author ���ڷ�	2009-8-31 
 *
 */
public class MailSenderTest {
	@Test
	public void testMailSender(){
		MailUtils.sendMail("����", "Can you receive ? Ŷ�����õ���");
	}
}
