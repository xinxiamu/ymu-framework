package com.ymu.framework.utils.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class FreemarkerUtils {

    private static Configuration cfg;

    private static FreemarkerUtils freemarkerUtils;

    /**
     *
     * @param templatesPath 模板目录
     * @return
     * @throws IOException
     */
    public static FreemarkerUtils newInstance(String templatesPath) throws IOException {

        if (freemarkerUtils == null) {
            freemarkerUtils = new FreemarkerUtils();
        }

        //初始化freemarker,单例，只创建一次
        if (freemarkerUtils.cfg == null) {
             cfg = new Configuration(Configuration.VERSION_2_3_27);

            cfg.setDirectoryForTemplateLoading(new File(templatesPath));

            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
        }

        return freemarkerUtils;
    }

    /**
     * 把数据结构渲染到模板页面。
     * @param ftlhFullName 模板目录下文件全名如：test.ftlh
     * @param data 数据结构。
     * @return 返回渲染后的静态文件。html,否则返回null
     */
    public final  String getFreemarkerDealText(String ftlhFullName,Object data)  {
        StringWriter out = new StringWriter();
        String text = null;
        try {
            //Template temp = cfg.getTemplate("test.ftlh");
            Template temp = cfg.getTemplate(ftlhFullName);
            temp.process(data,out);
            text = out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return text;
    }
}
