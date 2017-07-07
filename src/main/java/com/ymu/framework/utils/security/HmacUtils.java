package com.ymu.framework.utils.security;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * 消息摘要算法-MAC。 防止消息被篡改。公布key。明文，摘要消息都发送给客户端，客户端比对校验，看内容是否被篡改。
 * 
 * @author xinxiamu
 */
public final class HmacUtils {

	public final static String getJdkHmacMD5(String hexKey, String str) {
		try {
			SecretKey restoreSecretKey = new SecretKeySpec(Hex.decodeHex(hexKey
					.toCharArray()), "HmacMD5");// 还原密钥
			Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
			mac.init(restoreSecretKey);
			return Hex.encodeHexString(mac.doFinal(str.getBytes()));// 执行消息摘要并转换成十六进制
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

}
