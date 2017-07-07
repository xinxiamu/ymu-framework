package com.ymu.framework.utils.security;

import org.junit.Test;

public class AESUtilsTest {

	@Test
	public void test() {
		String keyStr = "4F979A45A894F20C0A3286593780C869";
		String encodeStr = AESUtils.jdkAESEncode(keyStr, "123456");
		System.out.println(encodeStr);
		System.out.println(AESUtils.jdkAESDecode(keyStr, encodeStr));
	}
	
}
