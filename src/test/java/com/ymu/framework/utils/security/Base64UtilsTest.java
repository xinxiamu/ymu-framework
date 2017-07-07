package com.ymu.framework.utils.security;

import org.junit.Test;

public class Base64UtilsTest {

	@Test
	public void test() {
		String str = "我是中国人";
		String encode = Base64Utils.base64Encode(str);
		System.out.println(encode);
		System.out.println("-------------");
		System.out.println(Base64Utils.base64Decode(encode));
	}
}
