package com.ymu.framework.web;
import java.io.Serializable;

public class ApiRspResultVO implements Serializable {
	
	private static final long serialVersionUID = -1397589752578789670L;

	private Boolean isSuccess = true;
	
	private int code = 200;
	
	private String description = "";
	
	private Object data = "";

	public ApiRspResultVO() {
	}

	public ApiRspResultVO(Object result) {
		description = "";
		isSuccess = true;
		data = result;
	}

	public void respFailure(int code, String message) {
		this.code = code;
		description = message;
		isSuccess = false;
		data = "";
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "ApiRspResultVO [isSuccess=" + isSuccess + ", code=" + code
				+ ", description=" + description + ", data=" + data + "]";
	}
	
}
