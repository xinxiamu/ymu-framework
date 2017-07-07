package com.ymu.framework.utils.security;

import org.junit.Test;

public class Md5UtilsTest {
	
	@Test
	public  void test() {
		System.out.println(Md5Utils.getCCMD5Str("a1234567"));
		System.out.println(Md5Utils.getJdkMD5Str("a1234567"));
	}

}
