package com.ymu.framework.spring.mvc.api;

import com.alibaba.fastjson.JSON;
import com.ymu.framework.base.VBase;

public class ApiResult<T> extends VBase {
	
	private static final long serialVersionUID = -1397589752578789670L;

	private boolean isSuccess = true;
	
	private int code = 200;
	
	private String description = "";
	
	private T data = null;

	public ApiResult() {
	}

	public ApiResult(T result) {
		description = "";
		isSuccess = true;
		data = result;
	}

	public void respFailure(int code, String message) {
		this.code = code;
		description = message;
		isSuccess = false;
//		data = "";
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean success) {
		isSuccess = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	/*public static void main(String[] args) {
		ApiResult<String> vApiResult = new ApiResult<>();
		vApiResult.setData("abc");
		System.out.println(JSON.toJSONString(vApiResult));
	}*/
	
}
