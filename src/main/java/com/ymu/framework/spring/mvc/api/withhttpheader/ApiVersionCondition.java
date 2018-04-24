package com.ymu.framework.spring.mvc.api.withhttpheader;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    private int apiVersion;

    public ApiVersionCondition(int apiVersion){
        this.apiVersion = apiVersion;
    }

    public ApiVersionCondition combine(ApiVersionCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionCondition(other.getApiVersion());
    }

    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
    	String path = request.getServletPath();
    	if (path == null) {
			return null;
		}
		String contentVersion = request.getHeader("Content-Version"); //在http请求头中定义api版本，而不是在url中
        if (null == contentVersion || "".equals(contentVersion)) {
            throw new IllegalArgumentException("Content-Version非null非空");
        }
        if (!isInteger(contentVersion)) {
            throw new IllegalArgumentException("Content-Version必须为整数");
        }

        int version = Integer.valueOf(contentVersion).intValue();
        if(version >= this.apiVersion) { // 如果请求的版本号大于配置版本号， 则满足
            return this;
        }
        return null;
    }

    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 优先匹配最新的版本号
        return other.getApiVersion() - this.apiVersion;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    /**
     * 判断字符串是否为整数。
     * @param str
     * @return
     */
    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
