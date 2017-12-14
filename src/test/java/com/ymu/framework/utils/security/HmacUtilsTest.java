package com.ymu.framework.utils.security;

import org.junit.Test;

public class HmacUtilsTest {

	@Test
	public void test() {
		String keyStr = "4b995e39bab8247cfdae1f949ff2d3acc17f51d23e9b596754c17a66ba737749e41f9ec4ab65fbf76bc60f955460267181b26bd24f6297ac0a9427353e51d9e3";
		System.out.println(HmacUtils.getJdkHmacMD5(keyStr, "ddd"));
	}

	@Test
	public void hmacSha256() {
		String keyStr = "95439b0863c241c63a861b87d1e647b7";
		System.out.println(HmacUtils.getJdkHmacSHA256(keyStr,"{\"mobile\":\"13717899496\",\"name\":\"王呈祥\",\"idno\":\"130929198309068490\"}"));

//		byte[] aa = HmacUtils.getJdkHmacSHA256("{\"mobile\":\"13717899496\",\"name\":\"王呈祥\",\"idno\":\"130929198309068490\"}".getBytes(), keyStr);
//		System.out.println(Base64Utils.base64Encode(aa));
	}

}
