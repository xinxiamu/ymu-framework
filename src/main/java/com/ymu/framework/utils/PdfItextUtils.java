package com.ymu.framework.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.ymu.framework.utils.freemarker.FreemarkerUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Profile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public final class PdfItextUtils {

    /*public static void main(String[] args) throws IOException, DocumentException {
        testFont();
    }*/

    public static  void testFont() throws IOException, DocumentException {

        String templates = "/home/mutian/dev/java/github/ymu-framework/src/main/resources/templates";
        Map data = new HashMap();
        data.put("username", "鄂州市吴穷建筑工程有限公司");
        data.put("age", 18);
//       String htmlText = FreemarkerUtils.newInstance(templates).getFreemarkerDealText("test.ftlh",data);
        String htmlText = FreemarkerUtils.newInstance(templates).getFreemarkerDealText("test2.ftlh", data);
        creatHtmlToPdf(htmlText, "/home/mutian/dev/java/github/ymu-framework/src/main/resources/bb.pdf");
    }

    public static final String creatHtmlToPdf(String htmlText, String creatPdfPath) {
        try {
            File file = new File(creatPdfPath);

//            BaseFont baseFont =com.itextpdf.text.pdf.BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",com.itextpdf.text.pdf.BaseFont.NOT_EMBEDDED);
//            MyFontProvider myFontProvider = new MyFontProvider(BaseColor.BLACK, "", "", false, false, 16, 1, baseFont);


            // 创建一个document对象实例
            Document document = new Document();
            // 为该Document创建一个Writer实例
            PdfWriter pdfwriter = null;
            pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(file));
            pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
            // 打开当前的document
            document.open();
            InputStream ste = IOUtils.toInputStream(htmlText,"UTF-8");

            XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, ste, Charset.defaultCharset(), new AsianFontProvider());
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static class AsianFontProvider extends XMLWorkerFontProvider {

        public Font getFont(final String fontname, final String encoding,
                            final boolean embedded, final float size, final int style,
                            final BaseColor color) {
            BaseFont bf = null;
            try {
                bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Font font = new Font(bf, size, style, color);
            font.setColor(color);
            return font;
        }
    }
}
