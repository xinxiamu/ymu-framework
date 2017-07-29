package com.ymu.framework.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 条形码处理。
 * 
 * @author Administrator
 *
 */
public class BarcodeUtil {

	private BarcodeUtil() {
	}

	/**
	 * 生成条形码。
	 * 
	 * @param contents
	 *            条形码内容
	 * @param width
	 *            长度
	 * @param height
	 *            高度
	 * @param imgPath
	 *            生成图片存放路径
	 */
	public final static void encode(String contents, int width, int height, String imgPath) {
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
		try {
			// EAN_13 十三个数字长度
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.EAN_13, codeWidth, height,
					null);

			MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(imgPath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析条形码。
	 * 
	 * @param imgPath
	 *            条形码图片路径
	 * @return 条形码内容
	 */
	public final static String decode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				throw new NullPointerException("图片可能存在");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ------------------------ barcode4j ------------------//
	/**
	 * 生成文件
	 *
	 * @param msg
	 * @param path
	 * @return
	 */
	public static File generateFile(String msg, String path) {
		File file = new File(path);
		try {
			generate(msg, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return file;
	}

	/**
	 * 生成字节
	 *
	 * @param msg
	 * @return
	 */
	public static byte[] generate(String msg) {
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		generate(msg, ous);
		return ous.toByteArray();
	}

	/**
	 * 生成到流
	 *
	 * @param msg
	 * @param ous
	 */
	public static void generate(String msg, OutputStream ous) {
		if (StringUtil.isEmpty(msg) || ous == null) {
			return;
		}

		Code39Bean bean = new Code39Bean();

		// 精细度
		final int dpi = 150;
		// module宽度
		final double moduleWidth = UnitConv.in2mm(1.0f / dpi);

		// 配置对象
		bean.setModuleWidth(moduleWidth);
		bean.setWideFactor(3);
		bean.doQuietZone(false);

		String format = "image/png";
		try {

			// 输出到流
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY,
					false, 0);

			// 生成条形码
			bean.generateBarcode(canvas, msg);

			// 结束绘制
			canvas.finish();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
//	public static void main(String[] args) {
//        String msg = "1ztBc123456789";
//        String path = "G:/barcode.png";
//        generateFile(msg, path);
////        String content = decode("F:/bb.png");
////        System.out.println(content);
//    }

}
