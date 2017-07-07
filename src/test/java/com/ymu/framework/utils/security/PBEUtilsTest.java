package com.ymu.framework.utils.security;

import org.junit.Test;

public class PBEUtilsTest {

	@Test
	public  void test() {
		// String salt = generateSalt();
		// System.out.println(salt);

		String salt = "edeaf8effe683998";
		String pwd = "701712";
		String src = "我是中国人";
		String encodeStr = PBEUtils.jdkPBEWITHMD5andDES(salt, pwd, src,1);
		System.out.println(encodeStr);
		System.out.println(PBEUtils.jdkPBEWITHMD5andDES(salt, pwd, encodeStr, 2));
	}
}
