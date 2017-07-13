package com.ymu.framework.utils.http;

public class ConstantMvc {
	
	/**
	 * APP请求参数key.
	 * jsonString={"parameters": {"currentPage": "1","pageSize": "10","brandType": "1"},"heads": {"metaCharset": "utf-8","msvalidate": "-michJB8aokthZhSsY3KIyd7TW9tQ2jSXI_87qveZpo","verification": "5532f353892ad86095cb538ab988fb55","version": "V1.0"}}
	 */
	public final static String REQUEST_KEY = "jsonString";
	
	/**
	 * app请求参数值的key。parameters
	 */
	public final static String REQUEST_PAR_KEY = "parameters";

	/**
	 * 成功与否的状态，1或-1
	 */
	public final static String STATUS = "status";
	
	public final static int STATUS_VALUE_SUCCESS = 1;
	
	public final static int STATUS_VALUE_FAILER = -1;
	
	/**
	 * 接口访问成功与否的原因简单描述。
	 */
	public final static String DESCRIPTION = "description";
	
	/**
	 * 服务端抛异常描述
	 */
	public final static String DESCRIPTION_ERROR = "服务端程序异常";
	
	/**
	 * 接口正常访问描述
	 */
	public final static String DESCRIPTION_SUCCESS = "成功";
	
	/**
	 * 客户端传递参数格式错误，或者缺少必传参数等错误。
	 */
	public final static String DESCRIPTION_ERROR_PAR = "传参错误";
	
	/**
	 * 前后台自定义协议检测不一致错误表示
	 */
	public final static String DESCRIPTION_ERROR_PROTOCOL = "协议错误";
}
