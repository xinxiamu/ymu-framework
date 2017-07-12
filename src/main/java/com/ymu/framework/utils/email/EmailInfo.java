package com.ymu.framework.utils.email;

public class EmailInfo {

	/**发送email人*/
	private String from;
	
	/**接收人*/
	private String to;
	
	/**email主题*/
	private String subject;
	
	/**email内容*/
	private String content;
	
	/**文本格式，true：html格式；false：文本格式*/
	private boolean textType = false;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isTextType() {
		return textType;
	}

	public void setTextType(boolean textType) {
		this.textType = textType;
	}
	
}
