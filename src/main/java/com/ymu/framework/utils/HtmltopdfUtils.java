package com.ymu.framework.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class HtmltopdfUtils {

    private static final Logger logger = LogManager.getLogger(HtmltopdfUtils.class);

    private HtmltopdfUtils() {
    }

    private static HtmltopdfUtils htmltopdfUtils;

    private static String tempDir;

    public static HtmltopdfUtils newInstance(String userDir) {
        if (htmltopdfUtils == null) {
            htmltopdfUtils = new HtmltopdfUtils();
        }
        tempDir = createTempDir(userDir);
        return htmltopdfUtils;
    }

    /**
     * 创建临时文件夹。
     *
     * @param userDir
     *            一般是。@Value("${user.dir}")
     * @return 临时文件夹temp所在路径
     */
    private static String createTempDir(String userDir) {
        // 创建临时文件夹
//		URI uri = URI.create(userDir);
        String tempDirPath = userDir.concat(File.separator).concat("temp");
        File file = new File(tempDirPath);
        if (!file.exists()) {
            file.mkdir();
            logger.info(">>>成功创建临时文件夹temp：" + tempDirPath);
        }

        return tempDirPath;
    }

    /**
     * 获取wkhtmltopdf可执行文件。在linux系统下有效
     *
     * @return
     */
    private String getWkhtmltopdfPathOnLinux() {
        try {
            // 拷贝wkhtmltopdf可执行文件到临时目录temp中
            String wkhtmltopdfPath = tempDir.concat(File.separator).concat("wkhtmltopdf");
            File wkhtmltopdf = new File(wkhtmltopdfPath);
            if (!wkhtmltopdf.exists()) {
                wkhtmltopdf.createNewFile();
                InputStream inputStream = HtmltopdfUtils.class.getClassLoader().getResourceAsStream("bin/wkhtmltopdf");
                FileUtils.copyToFile(inputStream, wkhtmltopdf);
                CmdExecUtils.enableExe(wkhtmltopdfPath);


                File fontsFile = new File("/usr/share/fonts/");
                if (!fontsFile.exists()) {
                    CmdExecUtils.execCommond("mkdir /usr/share/fonts/");
                }
            }

            File fontFile = new File(tempDir.concat(File.separator).concat("simsun.ttc"));
            if (!fontFile.exists()) {
                fontFile.createNewFile();

                InputStream inputStream1 = HtmltopdfUtils.class.getClassLoader().getResourceAsStream("bin/simsun.ttc");
                FileUtils.copyToFile(inputStream1, fontFile);
                String script = "mv " + fontFile.getAbsolutePath() + " /usr/share/fonts/";
                System.out.println(">>>mv script:" + script);
                CmdExecUtils.execCommond(script);

                CmdExecUtils.chownR("/usr/share/fonts/simsun.ttc");
            }

            return wkhtmltopdfPath;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取临时文件夹路径
     *
     * @return
     */
    public String getTempDir() {
        return tempDir;
    }

    /**
     * wkhtmltopdf安装在：C:\\Program Files\\wkhtmltopdf\\bin
     *
     * @return
     */
    private String getWkhtmltopdfPathOnWindows() {
        return "C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe"; //改成自己的安装路径
    }

    /**
     * 创建pdf文件。
     *
     * @param args
     * @return
     */
    private boolean createPdf(String... args) {
        return CmdExecUtils.execCommond(args);
    }

    public boolean creatPdf(ArrayList<String> htmlFilePaths, String pdfFileName) {
        if (htmlFilePaths == null || htmlFilePaths.isEmpty()) {
            throw new NullPointerException("没有html文件");
        }
        if (pdfFileName == null || "".equals(pdfFileName)) {
            throw new NullPointerException("生成的pdf文件名不能空");
        }

        // 执行并输出pdf
        StringBuilder sb = new StringBuilder();
        if (SystemUtils.OsType.LINUX.equals(SystemUtils.showSysType())) {
            sb.append(getWkhtmltopdfPathOnLinux()).append(" ");
        } else {
            sb.append(getWkhtmltopdfPathOnWindows()).append(" ");
        }
        for (String htmlFilePath : htmlFilePaths) {
            sb.append(htmlFilePath).append(" ");
        }
        sb.append(pdfFileName);
        return createPdf(sb.toString());
    }

}
