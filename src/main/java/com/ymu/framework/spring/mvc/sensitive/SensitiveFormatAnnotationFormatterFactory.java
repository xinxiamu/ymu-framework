package com.ymu.framework.spring.mvc.sensitive;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.HashSet;
import java.util.Set;

/**
 * 功能简述:<br>
 *     实现AnnotationFormatterFactory接口，将@SensitiveFormat与SensitiveFormatter绑定。
 *    <p>
 *        将SensitiveFormatAnnotationFormatterFactory注册到 Spring MVC 中。
 *    </p>
 * @author zmt
 * @create 2018-03-07 上午9:39
 * @updateTime
 * @since 1.0.0
 */
public class SensitiveFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<SensitiveFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<>();
        fieldTypes.add (String.class);
        return fieldTypes;
    }

    @Override
    public Printer<?> getPrinter(SensitiveFormat sensitiveFormat, Class<?> aClass) {
        return new SensitiveFormatter();
    }

    @Override
    public Parser<?> getParser(SensitiveFormat sensitiveFormat, Class<?> aClass) {
        return new SensitiveFormatter();
    }
}
