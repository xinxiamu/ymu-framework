package com.ymu.framework.utils.security;

import org.junit.Test;

public class Base64UtilsTest {

	@Test
	public void test() {
		String str = "我是中国人";
		String encode = Base64Utils.base64Encode(str);
		System.out.println(encode);
		System.out.println("-------------");
		System.out.println(Base64Utils.base64Decode(encode));
	}

	@Test
	public void testB64ToStr() {
		String b64Str = "eyJuYW1lIjoic2Rmc2RzZiIsInBhc3N3b3JkIjoiZHNmc2FkZiIsInN5c3RlbVNvdXJjZUlkIjoiNDAyODgxZmU2MjIzZTM4NTAxNjIyM2U5MDNiYjAwMDAiLCJjYWNoZSI6ZmFsc2UsInRva2VuVXNlcklkIjoyLCJ0b2tlbiI6IjE4NmY1YTEyZmY5ZDY1NjNlZmNjNjExNjlkMmJjODk0IiwicmFuZCI6IjEwOjQ5OjA2IEFNIn0%3D";
		System.out.println(">>>>>>str:" + Base64Utils.base64Decode(b64Str));
	}
	
	@Test
	public void generateImgByBase64StrTest() {
		String img = "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";
		String outputPath = "/home/mutian/Desktop/hh.png";
		Base64Utils.generateFileByBase64Str(img, outputPath);
	}
	
//	@Test
	public void getImageBase64StrTest() {
		String str = Base64Utils.getImageBase64Str("D:/用户目录/我的图片/11.png");
		System.out.println(str);
	}
}
