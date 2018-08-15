package com.ymu.framework.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ymu.framework.utils.security.Base64Utils;
import org.apache.commons.io.IOUtils;

/**
 * 二维码处理
 * 
 * @author Administrator
 *
 */
public final class QRCodeUtil {

	private QRCodeUtil() {
	}

	/**
	 * 生成二维码。
	 * 
	 * @param contents
	 *            二维码内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param imgPath
	 *            生成的二维码图片的路径
	 */
	public final static void encode(String contents, int width, int height, String imgPath) {
		Map<EncodeHintType, Object> hints = new Hashtable<>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);

			MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(imgPath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static String encode(String contents, int width, int height) {
		Map<EncodeHintType, Object> hints = new Hashtable<>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);

			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);

			MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);

			byte[] bytes = outputStream.toByteArray();
			String b64Str = Base64Utils.base64Encode(bytes);
			System.out.println(">>>二维码base64字符串：" + b64Str);

			return b64Str;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * 解析二维码。
	 * 
	 * @param imgPath
	 *            待解析二维码图片的路径。
	 * @return 二维码内容，否则null
	 */
	public final static String decode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Map<DecodeHintType, Object> hints = new Hashtable<>();
			hints.put(DecodeHintType.CHARACTER_SET, "GBK");

			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
