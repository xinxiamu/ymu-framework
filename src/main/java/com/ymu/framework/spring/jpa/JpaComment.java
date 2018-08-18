package com.ymu.framework.spring.jpa;

import java.lang.annotation.*;

/**
 * Created by lujijiang on 2016/12/20.
 */
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JpaComment {
    /**
     * 字段名
     *
     * @return
     */
    String value();
    /**
     * 字段详细描述
     *
     * @return
     */
    String description() default "";
}
