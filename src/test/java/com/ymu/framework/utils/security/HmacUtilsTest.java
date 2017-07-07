package com.ymu.framework.utils.security;

import org.junit.Test;

public class HmacUtilsTest {

	@Test
	public void test() {
		String keyStr = "4b995e39bab8247cfdae1f949ff2d3acc17f51d23e9b596754c17a66ba737749e41f9ec4ab65fbf76bc60f955460267181b26bd24f6297ac0a9427353e51d9e3";
		System.out.println(HmacUtils.getJdkHmacMD5(keyStr, "ddd"));
	}

}
