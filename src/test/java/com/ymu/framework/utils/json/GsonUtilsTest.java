package com.ymu.framework.utils.json;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.google.gson.reflect.TypeToken;
import com.ymu.framework.utils.PrintUtil;

public class GsonUtilsTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
	public void toJson() {
		PrintUtil
				.println("**************************************bean对象转化成json字符串 ");
		User jack = new User("张三", "123456", "已婚", "男");
		User marry = new User("李四", "888888", "未婚", "女");

		PrintUtil
				.println("---------------------------------------------jsonArray");
		List userList = new LinkedList();
		userList.add(jack);
		userList.add(marry);
		Type targetType = new TypeToken<List<User>>() {
		}.getType();
		String sUserList1 = GsonUtils.toJson(userList, targetType);
		String sUserList2 = GsonUtils.toJson(userList, targetType, false);
		String sUserList3 = GsonUtils.toJson(userList, targetType, 1.1d, true);
		PrintUtil.println(sUserList1);
		PrintUtil.println(sUserList2);
		PrintUtil.println(sUserList3);

		PrintUtil
				.println("---------------------------------------------json对象");
		Type targetType1 = new TypeToken<User>() {
		}.getType();
		String userJsonStr1 = GsonUtils.toJson(jack, targetType1);
		String userJsonStr11 = GsonUtils.toJson(jack);
		String userJsonStr2 = GsonUtils.toJson(jack, true);
		String userJsonStr3 = GsonUtils.toJson(jack, 1.1d, false);
		jack.setBornDate(new Date());
		String userJsonStr12 = GsonUtils.toJson(jack, "yyyy-MM-dd");
		PrintUtil.println(userJsonStr1);
		PrintUtil.println(userJsonStr11);
		PrintUtil.println(userJsonStr12);
		PrintUtil.println(userJsonStr2);
		PrintUtil.println(userJsonStr3);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
	public void formJson() {
		PrintUtil
				.println("******************************** json字符串转对象 ******************");
		User jack = new User("张三", "123456", "已婚", "男");
		User marry = new User("李四", "888888", "未婚", "女");
		List userList = new LinkedList();
		userList.add(jack);
		userList.add(marry);
		jack.setBornDate(new Date());
		String jsonArrayStr = GsonUtils.toJson(userList);
		String jsonObjectStr = GsonUtils.toJson(jack);
		PrintUtil.println(jsonArrayStr);
		PrintUtil.println(jsonObjectStr);
		
		User user = GsonUtils.fromJson(jsonObjectStr,User.class);
		PrintUtil.println(user.toString());
		List<User> list = GsonUtils.fromJson(jsonArrayStr, List.class);
		PrintUtil.println(list.toString());
	}
}
