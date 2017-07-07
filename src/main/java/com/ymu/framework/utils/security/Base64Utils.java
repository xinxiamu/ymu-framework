package com.ymu.framework.utils.security;

import org.apache.commons.codec.binary.Base64;

/**
 *  Base64工具类。基于apche common code库。
 * @author xinxiamu
 *
 */
public final class Base64Utils {

	private Base64Utils() {
	}

	/**
	 * base64加密。
	 * 
	 * @param encodeStr
	 * @return
	 */
	public final static String base64Encode(String encodeStr) {
		nullOrEmptyException(encodeStr);
		byte[] encodeBytes = Base64.encodeBase64(encodeStr.getBytes());
		return new String(encodeBytes);
	}

	/**
	 * base64解密为明文。
	 * 
	 * @param base64EncodeStr
	 * @return
	 */
	public final static String base64Decode(String base64EncodeStr) {
		nullOrEmptyException(base64EncodeStr);
		return new String(Base64.decodeBase64(base64EncodeStr.getBytes()));
	}

	private static void nullOrEmptyException(String str) {
		if (null == str || "".equals(str)) {
			throw new NullPointerException("加密字符串不能为null或者空");
		}
	}
}
