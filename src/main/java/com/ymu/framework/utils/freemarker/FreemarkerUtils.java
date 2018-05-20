package com.ymu.framework.utils.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;

public final class FreemarkerUtils {

    private FreemarkerUtils(){};

    private static Configuration cfg;

    private static FreemarkerUtils freemarkerUtils;

    /*public static void  main(String args[]) {
        String a = FreemarkerUtils.class.getClassLoader().getResource("").getPath();
        System.out.println(a);
    }*/

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

    /**
     * 渲染数据到页面并输出新的html页面。
     * @param ftlhFullName 要渲染的模板文件，在模板目录下
     * @param targetHtmlPath 要输出的新的html文件路径
     * @param data 模板数据
     */
    public void createHTML(String ftlhFullName,String targetHtmlPath,Object data)  {
        try {
            Template template = cfg.getTemplate(ftlhFullName);
            File htmlFile = new File(targetHtmlPath);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
            //处理模版并开始输出静态页面
            template.process(data, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
