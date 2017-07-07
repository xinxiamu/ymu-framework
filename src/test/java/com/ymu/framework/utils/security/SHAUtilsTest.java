package com.ymu.framework.utils.security;

import org.junit.Test;

public class SHAUtilsTest {
	
	@Test
	public void test() {
		System.out.println(SHAUtils.getCCSHA1Str("哈哈"));
		System.out.println(SHAUtils.getJdkSHA1Str("哈哈"));
		System.out.println(SHAUtils.getCCSHA256Str("哈哈"));
	}

}
