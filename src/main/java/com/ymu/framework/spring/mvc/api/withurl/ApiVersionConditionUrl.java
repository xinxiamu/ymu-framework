package com.ymu.framework.spring.mvc.api.withurl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

public class ApiVersionConditionUrl implements RequestCondition<ApiVersionConditionUrl> {

    // 路径中版本的前缀， 这里用 /v[1-9]/的形式
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)/");
    
    private int apiVersion;
    
    public ApiVersionConditionUrl(int apiVersion){
        this.apiVersion = apiVersion;
    }
    
    public ApiVersionConditionUrl combine(ApiVersionConditionUrl other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionConditionUrl(other.getApiVersion());
    }

    public ApiVersionConditionUrl getMatchingCondition(HttpServletRequest request) {
//    	String pathInfo = request.getPathInfo();//这个方法获取是null，报错。
    	String path = request.getServletPath(); 
    	if (path == null) {
			return null;
		}
        Matcher m = VERSION_PREFIX_PATTERN.matcher(path);//匹配路径
        if(m.find()){
            Integer version = Integer.valueOf(m.group(1));
            if(version >= this.apiVersion) // 如果请求的版本号大于配置版本号， 则满足
                return this;
        }
        return null;
    }

    public int compareTo(ApiVersionConditionUrl other, HttpServletRequest request) {
        // 优先匹配最新的版本号
        return other.getApiVersion() - this.apiVersion;
    }

    public int getApiVersion() {
        return apiVersion;
    }

}