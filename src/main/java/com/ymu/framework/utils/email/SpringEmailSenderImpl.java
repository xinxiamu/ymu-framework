package com.ymu.framework.utils.email;

import java.io.File;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

/**
 * 
 * @类描述：邮件发送实现类
 *
 * @创建人：mt
 * @创建时间：2014年11月11日上午9:09:08
 * @修改人：Administrator
 * @修改时间：2014年11月11日上午9:09:08
 * @修改备注：
 * @version v1.0
 * @Copyright 
 * @mail 932852117@qq.com
 */
@Component
public class SpringEmailSenderImpl implements EmailSender {

	// 在spring中注入
	public JavaMailSender javaMailSender;

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendEmail(EmailInfo emailInfo) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
					true, "UTF-8");
			message.setFrom(emailInfo.getFrom()); // 发送人
			message.setTo(emailInfo.getTo()); // 接收人
			message.setSubject(emailInfo.getSubject()); // 主题
			message.setText(emailInfo.getContent(), emailInfo.isTextType()); // 内容
			message.setSentDate(new Date()); // 发送日期
			javaMailSender.send(mimeMessage); // 发送邮件
		} catch (MessagingException e) {
			throw new RuntimeException("发送邮件时出现异常！", e);
		}
	}

	@Override
	public void sendEmailMime(final EmailInfo emailInfo) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {

				mimeMessage.setRecipient(Message.RecipientType.TO,
						new InternetAddress(emailInfo.getTo()));
				mimeMessage.setFrom(new InternetAddress(emailInfo.getFrom()));
				mimeMessage.setText(emailInfo.getContent());
				mimeMessage.setSubject(emailInfo.getSubject());
			}
		};

		try {
			javaMailSender.send(preparator);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@Override
	public void sendEmailWithFile(EmailInfo emailInfo, String filePath) {
		try {
			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost(emailInfo.getFrom());

			MimeMessage message = sender.createMimeMessage();

			// use the true flag to indicate you need a multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(emailInfo.getTo());
			helper.setText(emailInfo.getContent());
			helper.setSubject(emailInfo.getSubject());

			// 添加文件
			FileSystemResource file = new FileSystemResource(new File(filePath));
			helper.addAttachment("文件", file);

			sender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
