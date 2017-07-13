package com.ymu.framework.utils.http;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @类描述：HttpServletRequest请求工具类。
 * 
 * @创建人：mt
 * @创建时间：2014年10月21日上午10:30:36
 */
public class RequestUtil {
	
	private RequestUtil(){}
	
	/**
	 * 是否为ajax请求。
	 * @param request
	 * @return 是返回true，否则返回false
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return false;
	}
	
	/**
	 * 获取请求参数。简单类型请求参数，即不能包含文件。
	 * 
	 * @param request
	 * @return
	 * @author mutian
	 * @throws Exception
	 */
	public static Map<String, String> setPar2Map(HttpServletRequest request) {
		Map<String, String> param = new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = request.getParameter(name);
			param.put(name, value);
		}

		return param;
	}
	
	/**
	 * 检测请求是否是复合（有文件请求）请求。
	 * 
	 * @param request
	 * @return 是复合请求，返回true
	 */
	public static boolean isMultipartRequest(HttpServletRequest request) {
		boolean flg = false;
		String contentType = request.getContentType();
		if (contentType != null
				&& contentType.toLowerCase().startsWith("multipart/")) { // 有上传文件
			flg = true;
		}
		return flg;
	}

	/**
	 * 获取项目访问根路径
	 * 
	 * @param request
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getBasePath(HttpServletRequest request)
			throws UnknownHostException {
		Map<String, Object> systemInfoMap = getSystemMessage(request);
		String basePath = "http://" + systemInfoMap.get("ip").toString() + ":"
				+ systemInfoMap.get("localPort")
				+ systemInfoMap.get("contextPath") + "/";
		return basePath;
	}

	/**
	 * 获取项目真实路径。
	 * 
	 * @param request
	 * @return
	 * @author mutian
	 */
	public static String getContextRealPath(HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		String realPath = context.getRealPath("/");
		return realPath;
	}

	/**
	 * 获取一些系统信息
	 * 
	 * @param request
	 * @return
	 * @throws UnknownHostException
	 */
	public static Map<String, Object> getSystemMessage(
			HttpServletRequest request) throws UnknownHostException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAgent", request.getHeader("User-Agent")); // 就是取得客户端的系统版本
		map.put("remoteAddr", request.getRemoteAddr()); // 取得客户端的IP
		map.put("remoteHost", request.getRemoteHost()); // 取得客户端的主机名
		map.put("remotePort", request.getRemotePort()); // 取得客户端的端口
		map.put("remoteUser", request.getRemoteUser()); // 取得客户端的用户
		map.put("localAddr", request.getLocalAddr()); // 取得本地IP
		map.put("localPort", request.getLocalPort()); // 取得本地端口
		map.put("contextPath", request.getContextPath()); // 项目名称
		InetAddress inet = InetAddress.getLocalHost();
		map.put("ip", inet.getHostAddress());
		return map;
	}

}
