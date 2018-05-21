package com.ymu.framework.utils;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class HtmltopdfUtilsTest {

    @Test
    public void creatPdfTest() {
        String userDir = System.getProperty("user.dir");
        System.out.println(">>>>user.dir:" + userDir);
        HtmltopdfUtils obj = HtmltopdfUtils.newInstance(userDir);

        String html1 = HtmltopdfUtils.class.getClassLoader().getResource("").getPath().concat(File.separator)
                .concat("html2pdf").concat(File.separator).concat("html").concat(File.separator)
                .concat("Purchase-Order.html");
        ArrayList<String> htmlPaths = new ArrayList<>();
        htmlPaths.add(html1);
        htmlPaths.add(html1);
        htmlPaths.add(html1);

        //pdf路径
        String pdfFile = obj.getTempDir().concat(File.separator).concat("导出aa.pdf");

        boolean flg = obj.creatPdf(htmlPaths, pdfFile);
        System.out.println(">>>>结果：" + flg);

        if (flg) {
            System.out.println(">>>上传pdf到文件服务器");
        } else {
            System.out.println(">>>生成pdf失败");
        }
    }
}
