package com.ymu.framework.utils;

import java.util.Map;

import org.junit.Test;

import com.ymu.framework.vo.UserEntity;

import junit.framework.Assert;

public class BeanUtilTest {

	@Test
	public void beanToMapTest() {
		UserEntity user = new UserEntity();
		// user.setAge(10);
		user.setUserName("张");
		user.setSex("男");
		try {
			Map<String, Object> result = BeanUtil.beanToMap(user);
			System.out.println("==result:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
