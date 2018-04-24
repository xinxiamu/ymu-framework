package com.ymu.framework.spring.mvc.api.withurl;

import com.ymu.framework.spring.mvc.api.ApiVersion;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Set;

public class CustomRequestMappingHandlerMappingUrl extends RequestMappingHandlerMapping {

    /**
     * 为所有注册路径添加"/{version}"匹配规则。目的，做api版本管理。
     * 不用在每个类或方法的@RequestMapping中加。
     * @param method
     * @param handlerType
     * @return
     */
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo requestMappingInfo = super.getMappingForMethod(method, handlerType);
        if (requestMappingInfo != null) {
            PatternsRequestCondition pcOri = requestMappingInfo.getPatternsCondition();
            Set<String> s = pcOri.getPatterns();
            StringBuilder pathNew = new StringBuilder("");
            if (s != null && !s.isEmpty()) {
                for (String str: s ) {
                    if (!"/error".equals(str)) {
                        pathNew.append("/{version}");
                        pathNew.append(str);
                    } else {
                        pathNew.append(str);
                    }
                }
            }

            PatternsRequestCondition pcnNew = new PatternsRequestCondition(pathNew.toString());

            RequestMappingInfo requestMappingInfoNew = new RequestMappingInfo(requestMappingInfo.getName(),pcnNew,requestMappingInfo.getMethodsCondition(),requestMappingInfo.getParamsCondition(),requestMappingInfo.getHeadersCondition(),requestMappingInfo.getConsumesCondition(),requestMappingInfo.getProducesCondition(),requestMappingInfo.getCustomCondition());

            return requestMappingInfoNew;

        }
        return requestMappingInfo;
    }

    @Override
    protected RequestCondition<ApiVersionConditionUrl> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }

    @Override
    protected RequestCondition<ApiVersionConditionUrl> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }
    
    private RequestCondition<ApiVersionConditionUrl> createCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVersionConditionUrl(apiVersion.value());
    }
}
