package com.ymu.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 把实体名称转成数据表对应名称。默认都是下划线分割。
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE,ElementType.PACKAGE})
public @interface GenDomainField2DBFieldAnnotation {
}
