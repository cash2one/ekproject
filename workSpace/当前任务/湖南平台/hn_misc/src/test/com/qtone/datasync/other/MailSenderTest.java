package com.qtone.datasync.other;

import org.testng.annotations.Test;

import cn.qtone.mm7.common.mail.MailUtils;

/**
 * @author ÑîÌÚ·É	2009-8-31 
 *
 */
public class MailSenderTest {
	@Test
	public void testMailSender(){
		MailUtils.sendMail("²âÊÔ", "Can you receive ? Å¶£¬¿´µÃµ½Âğ£¿");
	}
}
