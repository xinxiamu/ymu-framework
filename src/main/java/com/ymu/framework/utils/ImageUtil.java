package com.ymu.framework.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

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
     * 智能剪裁，在最符合剪裁尺寸的基础上确保图片不变形
     *
     * @param image
     *            原始图片对象
     * @param width
     *            剪裁宽度
     * @param height
     *            剪裁高度
     * @param quality
     *            图片质量
     * @return 剪裁后的图片
     */
    public static BufferedImage smartCut(BufferedImage image, int width, int height, float quality) {
        if (width > 0 || height > 0) {
            if (width <= 0) {
                width = image.getWidth() * height / image.getHeight();
            } else if (height <= 0) {
                height = image.getHeight() * width / image.getWidth();
            }
        } else {
            width = image.getWidth();
            height = image.getHeight();
        }
        Scalr.Method method = Scalr.Method.AUTOMATIC;
        if (quality > 0) {
            if (quality <= 0.5) {
                method = Scalr.Method.SPEED;
            } else if (quality <= 0.75) {
                method = Scalr.Method.BALANCED;
            } else if (quality <= 0.9) {
                method = Scalr.Method.QUALITY;
            } else {
                method = Scalr.Method.ULTRA_QUALITY;
            }
        }
        BufferedImage newImage;
        if ((width - image.getWidth()) * 1.0 / image.getWidth() > (height - image.getHeight()) * 1.0
                / image.getHeight()) {
            Scalr.Mode mode = Scalr.Mode.FIT_TO_WIDTH;
            image = Scalr.resize(image, method, mode, width, height);
            if (image.getHeight() > height) {
                newImage = Scalr.crop(image, 0, (image.getHeight() - height) / 2, width, height);
            } else {
                newImage = image;
            }
        } else {
            Scalr.Mode mode = Scalr.Mode.FIT_TO_HEIGHT;
            image = Scalr.resize(image, method, mode, width, height);
            if (image.getWidth() > width) {
                newImage = Scalr.crop(image, (image.getWidth() - width) / 2, 0, width, height);
            } else {
                newImage = image;
            }
        }
        return newImage;
    }

    /**
     * 自动翻转图片
     *
     * @param source
     *            源图片文件
     * @param target
     *            目标图片文件
     * @throws ImageProcessingException
     * @throws IOException
     * @throws MetadataException
     */
    public static void autoRotate(File source, File target)
            throws ImageProcessingException, IOException, MetadataException {
        Metadata metadata = ImageMetadataReader.readMetadata(source);
        Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if (directory != null) {
            int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            BufferedImage image = ImageIO.read(source);
            switch (orientation) {
                case 3:
                    image = Scalr.rotate(image, Scalr.Rotation.CW_180, Scalr.OP_ANTIALIAS);
                    break;
                case 6:
                    image = Scalr.rotate(image, Scalr.Rotation.CW_90, Scalr.OP_ANTIALIAS);
                    break;
                case 8:
                    image = Scalr.rotate(image, Scalr.Rotation.CW_270, Scalr.OP_ANTIALIAS);
                    break;
                default:
                    break;
            }
            ImageIO.write(image, FilenameUtils.getExtension(source.getName()), target);
        } else {
            throw new IOException("Image does not contain information of ExifIFD0");
        }
    }

	/**
	 * 十六进制转换成图片。
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
	
//	public static void main(String[] args) {
//		String inputPath = "F:aa.txt";
//		String outputPath = "F:/bb.jpg";
//		ImageUtil imageUtil = new ImageUtil();  
////		Hex2Image(inputPath, outputPath);
//		saveToImgFile("F077", outputPath);
//	}
}
