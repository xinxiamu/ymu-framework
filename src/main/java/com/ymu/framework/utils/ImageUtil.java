package com.ymu.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @类描述：图片处理工具类。
 *
 * @创建人：mt
 * @创建时间：2014年11月13日上午10:01:31
 * @修改人：Administrator
 * @修改时间：2014年11月13日上午10:01:31
 * @修改备注：
 * @version v1.0
 * @Copyright 
 * @mail 932852117@qq.com
 */
public class ImageUtil {

	/**
	 * 十六进制转换成图片。？？？？？？？？？？？？
	 * @param hexFilePath 图片十六进制文本。c:/work/today.txt 
	 * @param outImgPath	输出图片路径 "c:/work/dd.jpg"
	 */
	@SuppressWarnings("resource")
	public static void Hex2Image(String hexFilePath, String outImgPath) {
		try {
			InputStream is = new FileInputStream(hexFilePath);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String str = null;
			StringBuilder sb = new StringBuilder();
			while ((str = br.readLine()) != null) {
				System.out.println(str);
				sb.append(str);
			}
			saveToImgFile(sb.toString().toUpperCase(), outImgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveToImgFile(String src, String outputPath) {
		if (src == null || src.length() == 0) {
			return;
		}
		try {
			FileOutputStream out = new FileOutputStream(new File(outputPath));
			byte[] bytes = src.getBytes();
			for (int i = 0; i < bytes.length; i += 2) {
				out.write(charToInt(bytes[i]) * 16 + charToInt(bytes[i + 1]));
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int charToInt(byte ch) {
		int val = 0;
		if (ch >= 0x30 && ch <= 0x39) {
			val = ch - 0x30;
		} else if (ch >= 0x41 && ch <= 0x46) {
			val = ch - 0x41 + 10;
		}
		return val;
	}
	
	public static void main(String[] args) {
		String inputPath = "F:aa.txt";
		String outputPath = "F:bb.jpg";
		ImageUtil imageUtil = new ImageUtil();
//		Hex2Image(inputPath, outputPath);
		saveToImgFile("F077", outputPath);
	}
}
