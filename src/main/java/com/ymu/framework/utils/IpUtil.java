package com.ymu.framework.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class IpUtil {

	/*public static void main(String[] args) {
		getCityNameByTBApi("14.145.49.207");
		IPBGelongsToWhere("14.145.49.207");
	}*/

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("WL-Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getRemoteAddr();  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("http_client_ip");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		}  
		// 如果是多级代理，那么取第一个ip为客户ip  
		if (ip != null && ip.indexOf(",") != -1) {  
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
		}  
		return ip;  
	}

	/*
	 * 根据ip获取城市
	 * 采用淘宝接口实现
	 * url:http://ip.taobao.com/service/getIpInfo.php
	 * 参数ip
	 * 返回值：城市名称
	 */
	public static String getCityNameByTBApi(String ip){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ip", ip);
		String json = getResult("http://ip.taobao.com/service/getIpInfo.php", map, "utf-8");

		ResultData data = null;
		try{
			if(json!=null){
				json = json.substring(json.indexOf("{", 1),json.length()-1);
				Gson gson = new Gson();
				data = gson.fromJson(json, ResultData.class);
			}
			if(data==null || data.getCity_id()==null || data.getCity_id().equals("")){
				data=new ResultData();
				data.setCity("广州");
			}
		}catch(Exception e){
			data=new ResultData();
			data.setCity("广州");
		}
		return data.getCity().replace("市", "");
	}


	public static String IPBGelongsToWhere(String ip){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ip", ip);
		String json = getResult("http://ip.taobao.com/service/getIpInfo.php", map, "utf-8");
		json = json.substring(json.indexOf("{", 1),json.length()-1);
		Gson gson = new Gson();
		ResultData data = gson.fromJson(json, ResultData.class);
		String result = "";
		result += data.getRegion()+data.getCity()+" "+data.getIsp();
		System.out.println(result);
		return result;
	}


	/*
	 * 获取url请求结果
	 * @param reqUrl 请求地址
	 * @param params 参数
	 * @param encoding 编码方式
	 */
	public static String getResult(String reqUrl, Map<String, String> params, String encoding) {
		HttpURLConnection connection=null;
		try {
			URL url = new URL(reqUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(10000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());

			StringBuffer paramSb = new StringBuffer();
			if (params != null) {
				for (Iterator<?> it = params.entrySet().iterator(); it.hasNext();) {
					Entry<?, ?> element=(Entry<?, ?>) it.next();
					paramSb.append(element.getKey());
					paramSb.append("=");
					paramSb.append(URLEncoder.encode(element.getValue().toString(), encoding));
					paramSb.append("&");
				}
				//去掉多余的&
				if (paramSb.length() > 0) {
					paramSb = paramSb.deleteCharAt(paramSb.length() - 1);
				}
			}
			out.write(paramSb.toString().getBytes());
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();// 关闭连接
		} 
		return null;
	}

	public static class ResultData implements Serializable {
		private String country;
		private String country_id;
		private String area;
		private String area_id;
		private String region;
		private String region_id;
		private String city;
		private String city_id;
		private String county;
		private String county_id;
		private String isp;
		private String isp_id;
		private String ip;

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getCountry_id() {
			return country_id;
		}

		public void setCountry__id(String country_id) {
			this.country_id = country_id;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getArea_id() {
			return area_id;
		}

		public void setArea_id(String area_id) {
			this.area_id = area_id;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getRegion_id() {
			return region_id;
		}

		public void setRegion_id(String region_id) {
			this.region_id = region_id;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getCity_id() {
			return city_id;
		}

		public void setCity_id(String city_id) {
			this.city_id = city_id;
		}

		public String getCounty() {
			return county;
		}

		public void setCounty(String county) {
			this.county = county;
		}

		public String getCounty_id() {
			return county_id;
		}

		public void setCounty_id(String county_id) {
			this.county_id = county_id;
		}

		public String getIsp() {
			return isp;
		}

		public void setIsp(String isp) {
			this.isp = isp;
		}

		public String getIsp_id() {
			return isp_id;
		}

		public void setIsp_id(String isp_id) {
			this.isp_id = isp_id;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

	}

}

