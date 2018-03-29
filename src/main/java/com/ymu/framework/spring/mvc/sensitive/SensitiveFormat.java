package com.ymu.framework.spring.mvc.sensitive;

import java.lang.annotation.*;

/**
 * 功能简述:<br>
 *  只需要在@RequestParam的参数上使用@SensitiveFormat注解，
 *  <br>
 *  Spring MVC 就会在注入该属性时自动进行敏感词过滤。
 *
 * @author zmt
 * @create 2018-03-07 上午9:41
 * @updateTime
 * @since 1.0.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveFormat {
}
