package com.ymu.framework.utils.security;

import org.junit.Test;

public class KeyToolsTest {

	@Test
	public void mainTest() {
//		String keyStr = KeyTools.generateKey(KeyEnum.AES.getValue(), 128);
//		System.out.println(keyStr.toUpperCase());
		
//		String keyStr2 = KeyTools.generateKey(KeyEnum.HMAC_MD5.getValue(), 0);
//		System.out.println(keyStr2);


		String keyStr = KeyTools.generateKey(KeyEnum.HmacSHA256.getValue(), 256);
		System.out.println(keyStr.toUpperCase());
	}
}
