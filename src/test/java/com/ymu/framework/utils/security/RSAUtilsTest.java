package com.ymu.framework.utils.security;

import org.junit.Test;

public class RSAUtilsTest {
	
	@Test
	public void test() {
		// generateKey();

		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIgkT6x+mTdkQyX1ED++1DKcy5T2pL2XMI5utGu63P6teItkdkPYI/TbNEyR4L3RM5vJbHyB3JGP/6ZnjktfL5fRS0t83HINhOXuLzN6bU8MpJpjGezyxvnbYLN2YnrK083tiuIqu1NJZrkvUeqG+uiKkZIVt5s88rpCJd2bytS3AgMBAAECgYB93pSLNsERpXEvttO/G/+0i6LtptvJLe+GwHOu5qVMEbZRqiBtuXgE+mXwJS9UUAcXEbNrqeTz7969SyEULn0g1vdhBpK9zyT/KQjlqTC0nY8njPjGChz8u93EQbVlUegYh7/TDed0NdFv/xtUiqp/XdwUO3RHdEq5upx3/8rjgQJBALxr5iTNqC+zJyoLWqhE432Eqz+yMlFTUvx14WG3xYu41TUJghspmdSr+17J4QnA/Sq1lt1b+sO+17zPhICXh8sCQQC4+E5vs6VJfmblGDmutXE+qkoCNl5YS86DrlGCIzVieI2TbOob5UTKWrmwTlvveUkZkyrp6ATj7vEIQG9LdFFFAkEAqrDsmqacGCop7pK+m/VcSNco89kQcFAVu5Nmi8mZcgwNSbDFZqn3K4xpeeNUtCtarZg3hWzP7Qg0FF3B4WRkpQJAUytzOU6xl2Y6pfBKn3/+N4siU/RWX6VWamdLRxTFwE0se4mRipGAaOx6aggR1o/WiqVdumcVK9gYkPlIEclvcQJAXyGOYTASlqE80i4+bX8ae3XrRm79z5E/qY27rTos18IYjU+WRX0//4hFgZbhZz0vBTVvhFiY/0uI2d00n8+kNA==";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIJE+sfpk3ZEMl9RA/vtQynMuU9qS9lzCObrRrutz+rXiLZHZD2CP02zRMkeC90TObyWx8gdyRj/+mZ45LXy+X0UtLfNxyDYTl7i8zem1PDKSaYxns8sb522CzdmJ6ytPN7YriKrtTSWa5L1HqhvroipGSFbebPPK6QiXdm8rUtwIDAQAB";

		String src = "我是一名程序猿";
		
		System.out.println("---------私钥加密，公钥解密");
		String rsaEncodeStr = RSAUtils.jdkRSAEncodeByPriKey(privateKey, src);
		System.out.println(rsaEncodeStr);
		System.out.println(RSAUtils.jdkRSADecodeByPubKey(publicKey, rsaEncodeStr));
		
		System.out.println("---------公钥加密，私钥解密");
		String rsaCodeStr = RSAUtils.jdkRSAEncodeByPubKey(publicKey, src);
		System.out.println(rsaCodeStr);
		System.out.println(RSAUtils.jdkRSADecodeByPriKey(privateKey, rsaCodeStr));
		
		//签名
		String signStr = RSAUtils.jdkRSASign(privateKey, src);
		System.out.println("---签名：" + signStr);
		//验证
		boolean flg = RSAUtils.jdkRSAVerify(publicKey, src, signStr);
		System.out.println("----验证结果：" + flg);
		
	}

}
