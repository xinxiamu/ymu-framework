package com.ymu.framework.spring.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ymu.framework.spring.mvc.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理。
 */
public class JsonHandlerExceptionResolver extends SimpleMappingExceptionResolver {

	static Logger log = LoggerFactory.getLogger(JsonHandlerExceptionResolver.class);

	/**
	 * 用于获取JSONP调用时的回调函数名的请求参数名
	 */
	private String jsonpCallbackParameterName = "jsonpcallback";

	public String getJsonpCallbackParameterName() {
		return jsonpCallbackParameterName;
	}

	public void setJsonpCallbackParameterName(String jsonpCallbackParameterName) {
		this.jsonpCallbackParameterName = jsonpCallbackParameterName;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
		if (!response.isCommitted()) {
			if (handler instanceof HandlerMethod) {
				final String callbackName = request.getParameter(jsonpCallbackParameterName);
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				if (handlerMethod.getMethodAnnotation(ResponseBody.class) != null
						|| handlerMethod.getBeanType().getAnnotation(ResponseBody.class) != null
						|| handlerMethod.getBeanType().getAnnotation(RestController.class) != null
						|| callbackName != null) {
					return handleExceptionMessage(request, response, ex, callbackName);
				}
			}
		} else {
			log.error("不能将异常信息处理为JSON格式，因为输出流已提交", ex);
		}
		return super.resolveException(request, response, handler, ex);
	}

	private ModelAndView handleExceptionMessage(HttpServletRequest request, HttpServletResponse response, Exception ex,
                                                final String callbackName) {
		response.resetBuffer();
		return new ModelAndView(new View() {
			@Override
			public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				response.setContentType(getContentType());
				PrintWriter out = response.getWriter();
				try {
					handleExceptionJsonMessage(out, ex, callbackName);
				} finally {
					out.close();
				}
			}

			@Override
			public String getContentType() {
				return callbackName == null ? "application/json;charset=utf-8" : "application/javascript;charset=utf-8";
			}
		});
	}

	public static void handleExceptionJsonMessage(PrintWriter out, Exception ex, String callbackName) {
		Map<String, Object> data = new HashMap<>();
		Throwable throwable;
		if (ex != null && ex.getCause() != null && ex.getMessage() != null) {
			throwable = ex.getCause();
		} else {
			throwable = ex;
		}
		StringWriter stringWriter = new StringWriter();
		try (PrintWriter printWriter = new PrintWriter(stringWriter)){
			throwable.printStackTrace(printWriter);
		}
		data.put("cause",stringWriter.toString());
		log.error("The handleExceptionJsonMessage will handled this exception.", throwable);
		if (throwable instanceof ApiException) {
            ApiException apiException = (ApiException) throwable;
            data.put("error", apiException.getMessage());
            data.put("code", apiException.getCode());
        } else if (throwable instanceof MethodArgumentNotValidException) {
            StringBuilder errorMessageBuilder = new StringBuilder();
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) throwable;
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            data.put("errors", bindingResult.getAllErrors());
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                if (errorMessageBuilder.length() > 0) {
                    errorMessageBuilder.append(";");
                }
                errorMessageBuilder.append(objectError.getObjectName());
                if (objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    errorMessageBuilder.append(".");
                    errorMessageBuilder.append(fieldError.getField());
                }
                errorMessageBuilder.append(objectError.getDefaultMessage());
            }
            data.put("error", errorMessageBuilder);
        } else {
            data.put("error", throwable.getMessage() == null ? throwable.getClass().getCanonicalName()
                    : throwable.getMessage());
        }
		data.put("type", throwable.getClass().getCanonicalName());
		String json = JSON.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect);
		if (callbackName != null) {
            out.print(callbackName);
            out.print("(");
            out.print(json);
            out.print(")");
        } else {
            out.print(json);
        }
	}
}
