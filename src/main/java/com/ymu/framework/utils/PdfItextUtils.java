package com.ymu.framework.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.ymu.framework.utils.freemarker.FreemarkerUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class PdfItextUtils {

    public static final String creatHtmlToPdf(String htmlText,String creatPdfPath) {
        try {
            File file = new File(creatPdfPath);
            // 创建一个document对象实例
            Document document = new Document();
            // 为该Document创建一个Writer实例
            PdfWriter pdfwriter = null;
                pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(file));
            pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
            // 打开当前的document
            document.open();
            InputStreamReader isr = new InputStreamReader(IOUtils.toInputStream(htmlText, "UTF-8"), "UTF-8");
            XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, isr);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        String templates = "/home/mutian/dev/java/github/ymu-framework/src/main/resources/templates";
        Map data = new HashMap();
        data.put("username","zmt");
        data.put("age",18);
       String htmlText = FreemarkerUtils.newInstance(templates).getFreemarkerDealText("test.ftlh",data);
        creatHtmlToPdf(htmlText,"/home/mutian/dev/java/github/ymu-framework/src/main/resources/test.pdf");
    }

}
