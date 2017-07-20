package com.ymu.framework.utils.json;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ymu.framework.utils.StringUtils;

/**
 * 
 * @类描述：阿里巴巴fastJson库工具类
 * 
 * @创建人：mt
 * @创建时间：2014年9月6日下午10:33:48
 * @修改人：Administrator
 * @修改时间：2014年9月6日下午10:33:48
 * @修改备注：
 * @version v1.0
 * @Copyright
 * @mail 932852117@qq.com
 */
public class FastJsonUtils {

	/**
	 * 查看json是否可以拿值
	 * 
	 * @param json
	 * @param key
	 * @return 可以拿返回true，否则返回false
	 */
	private static boolean isJsonCanGetValue(JSONObject json, String key) {
		return (json == null || StringUtils.isEmpty(key) || !json
				.containsKey(key)) ? false : true;
	}

	/**
	 * 把json对象字符串转化成json对象。jsonObject或者jsonArray
	 * 
	 * @param <T>
	 * 
	 * @param jsonStr
	 * @return 转化失败返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jSONStr2Json(String jsonStr) {
		T t = null;
		if (StringUtils.isEmpty(jsonStr)) {
			return null;
		}
		String flgStr = jsonStr.trim().substring(0,1);
		if (flgStr.equals("{")) {
			t = (T) JSONObject.parseObject(jsonStr);
		} else if (flgStr.equals("[")) {
			t = (T) JSONArray.parseArray(jsonStr);
		}
		return t;
	}

	/**
	 * java对象转化为json字符串。
	 * 
	 * @param object
	 *            json对象或者其它集合对象。
	 * @return
	 */
	public static String obj2JSONStr(Object object) {
		return object != null ? JSONObject.toJSONString(object) : "";
	}

	/**
	 * 获取字符串值。
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultStr
	 *            默认值
	 * @return 值为null，NULL,""字符串的时候返回defaultStr
	 */
	public static String getJValueStr(JSONObject jsonObject, String key,
			String defaultStr) {
		if (!isJsonCanGetValue(jsonObject, key)) {
			return defaultStr;
		}
		String value = jsonObject.getString(key);
		return (StringUtils.isEmpty(value) || value.equals("null") || value
				.equals("NULL")) ? defaultStr : value;
	}

	/**
	 * 重载
	 * 
	 * @param jsonObjStr
	 * @param key
	 * @param defaultStr
	 * @return
	 */
	public static String getJValueStr(String jsonObjStr, String key,
			String defaultStr) {
		if (StringUtils.isEmpty(jsonObjStr)) {
			return defaultStr;
		}
		JSONObject jsonObject = jSONStr2Json(jsonObjStr);
		if (!isJsonCanGetValue(jsonObject, key)) {
			return defaultStr;
		}
		String value = jsonObject.getString(key);
		return (StringUtils.isEmpty(value) || value.equals("null") || value
				.equals("NULL")) ? defaultStr : value.trim();
	}

	/**
	 * 获取json对象中key对应的值对象。如果是String类型且值为null，NULL,""，则返回默认值，否则原值返回。
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaults
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getJValueObj(JSONObject jsonObject, String key,
			T defaults) {
		if (!isJsonCanGetValue(jsonObject, key)) {
			return defaults;
		}
		T value = (T) jsonObject.get(key);
		if (value == null) {
			return defaults;
		}
		if (value.getClass() == String.class) {
			return (T) getJValueStr(jsonObject, key, (String) defaults);
		}
		return value;
	}

	/**
	 * 重载方法
	 * 
	 * @param jsonObjStr
	 * @param key
	 * @param defaults
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getJValueObj(String jsonObjStr, String key, T defaults) {
		if (StringUtils.isEmpty(jsonObjStr)) {
			return defaults;
		}
		JSONObject jsonObject = jSONStr2Json(jsonObjStr);
		if (!isJsonCanGetValue(jsonObject, key)) {
			return defaults;
		}
		T value = (T) jsonObject.get(key);
		if (value == null) {
			return defaults;
		}
		if (value.getClass() == String.class) {
			return (T) getJValueStr(jsonObject, key, (String) defaults);
		}
		return value;
	}

	/**
	 * 把json对象转化为map或者把jsonArray转化为list
	 * 
	 * @param jsonOrJsonArray
	 *            json或者jsonArray
	 * @param javaObj
	 * @return 失败返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toJavaObje(String jsonOrJsonArrayStr, Class<T> javaObj) {
		T t = null;
		if (StringUtils.isEmpty(jsonOrJsonArrayStr)) {
			return t;
		}
		Object jsonOrJsonArray = jSONStr2Json(jsonOrJsonArrayStr);
		if (jsonOrJsonArray.getClass() == JSONObject.class) { // json
			t = (T) JSONObject.toJavaObject((JSONObject) jsonOrJsonArray,
					Map.class);
		} else if (jsonOrJsonArray.getClass() == JSONArray.class) {// jsonArray
			t = (T) JSONArray.toJavaObject((JSONObject) jsonOrJsonArray,
					List.class);
		}
		return t;
	}

	/*public static void main(String[] args) {
		// 测jsonObj
		String jsonStr = "{'objNull': null,'str': '木木','str2':'','str3':'null','str4':'NULL','int': 1,'float':65632.5,"
				+ "'jsonObj': {'bolean': false,'double': 2},"
				+ "'jsonArray':[{'int2':6,'strI1':'NULL','strI2':''},{'int2':6,'strI1':'海','strI2':'null'}]}";
		JSONObject jsonObject = jSONStr2Json(jsonStr);
		String str = getJValueObj(jsonObject, "str", "天空");
		String str2 = getJValueStr(jsonObject, "str2", "ddd");
		String str3 = getJValueStr(jsonObject, "str2", "");
		String str4 = getJValueStr(jsonObject, "str2", "");
		int i = getJValueObj(jsonObject, "int", -1);
		BigDecimal f = getJValueObj(jsonObject, "float", new BigDecimal(1));
		double d = f.doubleValue();
		int di = f.intValue();
		JSONObject jsonObject2 = getJValueObj(jsonObject, "jsonObj", null);
		Map<String, Object> map = getJValueObj(jsonObject, "jsonObj", null);
		JSONArray jsonArray = getJValueObj(jsonObject, "jsonArray", null);
		List<Object> list = getJValueObj(jsonObject, "jsonArray", null);
		List<Map<String, Object>> list1 = getJValueObj(jsonObject, "jsonArray",
				null);
		for (Map<String, Object> map2 : list1) {
			int a = (Integer) map2.get("int2");
			String b = (String) map2.get("strI1");
			Log.d(FastJsonUtils.class, "ddddddddddddd");
			Log.i(FastJsonUtils.class, "iffffff");
			Log.println("" + a);
			Log.println(b);
		}

		// 测jsonArray
	}*/

}
