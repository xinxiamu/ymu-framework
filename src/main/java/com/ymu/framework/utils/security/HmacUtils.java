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
public class HmacUtils {

	public static void main(String[] args) throws Exception {
		String keyStr = "4b995e39bab8247cfdae1f949ff2d3acc17f51d23e9b596754c17a66ba737749e41f9ec4ab65fbf76bc60f955460267181b26bd24f6297ac0a9427353e51d9e3";
		System.out.println(getJdkHmacMD5(keyStr,"ddd"));
	}

	public static String getJdkHmacMD5(String hexKey, String str) {
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
