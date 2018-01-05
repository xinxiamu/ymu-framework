package com.test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TestDemo {

    /**
     * freemarker使用。
     * @throws IOException
     */
    @Test
    public void ftlTest() throws IOException {
        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */

        /* Create and adjust the configuration singleton */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);

        String path = getClass().getClassLoader().getResource("templates").getPath();
        cfg.setDirectoryForTemplateLoading(new File(path));

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        /* ------------------------------------------------------------------------ */

        Template temp = cfg.getTemplate("test.ftlh");
//        Writer out = new OutputStreamWriter(System.out);
        StringWriter out = new StringWriter();
        Map data = new HashMap();
        data.put("username","zmt");
        data.put("age",18);
        try {
            temp.process(data,out);
            String testFile = out.toString();
            System.out.println(testFile);
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
