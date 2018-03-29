package com.ymu.framework.spring.mvc.sensitive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * 功能简述:<br>
 *     对http请求接收到的内容进行过滤。另外方式，也可以用过滤器处理。
 *
 * @author zmt
 * @create 2018-03-07 上午9:30
 * @updateTime
 * @since 1.0.0
 */
public class SensitiveFormatter implements Formatter<String> {

    private static final Logger logger = LogManager.getLogger(SensitiveFormatter.class);

    /**
     * 在这里对接收到的内容进行过滤。
     * @param s
     * @param locale
     * @return
     * @throws ParseException
     */
    @Override
    public String parse(String s, Locale locale) throws ParseException {
        logger.debug("敏感词过滤前：" + s);
        return SensitiveUtil.filter(s);
    }

    @Override
    public String print(String s, Locale locale) {
        return s;
    }
}
