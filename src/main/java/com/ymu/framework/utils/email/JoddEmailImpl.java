package com.ymu.framework.utils.email;

import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;

public class JoddEmailImpl implements JoddEmail {

	@SuppressWarnings("rawtypes")
	@Override
	public void sendEmailText() {
		Email email = Email.create()
		        .from("932852117@qq.com")
		        .to("1979597540@qq.com")
		        .subject("test6")
		        .addText("Hello,word!");
		
		SmtpServer smtpServer = SmtpServer
                .create("smtp.qq.com", 8081)
                .authenticateWith("932852117@qq.com", "wnt*ZMT*701712%^")
                .timeout(10);
		
		SendMailSession session = smtpServer.createSession();
	    session.open();
	    session.sendMail(email);
	    session.close();
	}
	
	public static void main(String[] args) {
		JoddEmailImpl joddEmailImpl = new JoddEmailImpl();
		joddEmailImpl.sendEmailText();
	}
}
