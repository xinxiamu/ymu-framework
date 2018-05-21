package com.ymu.framework.utils;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HtmltopdfUtilsTest {

    @Test
    public void creatPdfTest() throws FileNotFoundException {
        String userDir = System.getProperty("user.dir");
        System.out.println(">>>>user.dir:" + userDir);
        HtmltopdfUtils obj = HtmltopdfUtils.newInstance(userDir);

        ArrayList<String> htmlPaths = new ArrayList<>();
        if (SystemUtils.showSysType().equals(SystemUtils.OsType.LINUX)) {
            String classPath = getClass().getClassLoader().getResource("html2pdf").getPath();
            String html1 = classPath.concat(File.separator).concat("html").concat(File.separator)
                    .concat("Purchase-Order.html");
            htmlPaths.add(html1);
            htmlPaths.add(html1);
        } else {
            htmlPaths.add("E:\\java\\github\\ymu-framework\\src\\test\\resources\\html2pdf\\html\\Purchase-Order.html");
        }

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
