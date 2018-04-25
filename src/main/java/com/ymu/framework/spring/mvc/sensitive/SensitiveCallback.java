package com.ymu.framework.spring.mvc.sensitive;

/**
 * 功能简述:<br>
 *
 *     在具体项目中实现该接口。回调做敏感词过滤。
 *
 * @author zmt
 * @create 2018-04-17 下午10:34
 * @updateTime
 * @since 1.0.0
 */
@FunctionalInterface
public interface SensitiveCallback {

    String filter(String oriStr);
}
