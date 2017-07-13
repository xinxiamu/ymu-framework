package com.ymu.framework.utils.http;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类描述：返回各种json格式数据到客户端的工具类。
 *
 * 							@创建人：mt
 */
public class ReturnJsonUtil {

	/**
	 * 服务端抛异常表示
	 * 
	 * @return
	 */
	public static String outSystemError() {
		return outError(ConstantMvc.DESCRIPTION_ERROR);
	}

	/**
	 * 传参错误表示
	 * 
	 * @return
	 */
	public static String outSystemErrorPar() {
		return outError(ConstantMvc.DESCRIPTION_ERROR_PAR);
	}

	/**
	 * 自定义协议错误表示
	 * 
	 * @return
	 */
	public static String outSystemErrorProtocol() {
		return outError(ConstantMvc.DESCRIPTION_ERROR_PROTOCOL);
	}

	/**
	 * 服务端程序错误返回。
	 * 
	 * @param msg
	 * @return
	 */
	public static String outError(String msg) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConstantMvc.STATUS, ConstantMvc.STATUS_VALUE_FAILER);
		jsonObject.put(ConstantMvc.DESCRIPTION, msg);
		return jsonObject.toJSONString();
	}

	public static String outSuccess() {
		return null;
	}
}
