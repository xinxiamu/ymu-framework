package com.ymu.framework.utils.security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Hex;

/**
 * 基于口令的对称加密算法-PBE.
 * 
 * @author xinxiamu
 *
 */
public class PBEUtils {

	public static void main(String[] args) {
		// String salt = generateSalt();
		// System.out.println(salt);

		String salt = "edeaf8effe683998";
		String pwd = "701712";
		String src = "我是中国人";
		String encodeStr = jdkPBEWITHMD5andDES(salt, pwd, src,1);
		System.out.println(encodeStr);
		System.out.println(jdkPBEWITHMD5andDES(salt, pwd, encodeStr, 2));
	}

	/**
	 * 生成盐。
	 * 
	 * @return
	 */
	private static String generateSalt() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = secureRandom.generateSeed(8);
		return Hex.encodeHexString(salt);
	}

	/**
	 * PBE加解密。
	 * 
	 * @param saltHexStr
	 *            盐，十六进制字符串。
	 * @param pwd
	 *            口令，即密码。
	 * @param src
	 *            需要加密数据。
	 * @param mode 1加密，其它解密。
	 * @return
	 */
	public static String jdkPBEWITHMD5andDES(String saltHexStr,
			String pwd, String src, int mode) {
		try {
			// 根据口令生成密钥
			PBEKeySpec pbeKeySpec = new PBEKeySpec(pwd.toCharArray());
			SecretKeyFactory secretKeyFactory = SecretKeyFactory
					.getInstance("PBEWITHMD5andDES");
			Key key = secretKeyFactory.generateSecret(pbeKeySpec);

			// 加密
			PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(
					Hex.decodeHex(saltHexStr.toCharArray()), 100);// 100迭代次数
			Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
			byte[] result= null;
			if (mode == 1) {
				cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
				result = cipher.doFinal(src.getBytes());
				return Hex.encodeHexString(result);
			} else {
				cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
				result = cipher.doFinal(Hex.decodeHex(src.toCharArray()));
				return new String(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

}
