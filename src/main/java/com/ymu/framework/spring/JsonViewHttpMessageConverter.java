package com.ymu.framework.spring;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * json和bean之间转换。
 */
public class JsonViewHttpMessageConverter
		extends TypeConstrainedMappingJackson2HttpMessageConverter {

	ObjectMapper objectMapper = new CustomObjectMapper();//默认

	/**
	 * Creates a new {@link TypeConstrainedMappingJackson2HttpMessageConverter} for the given type.
	 *
	 * @param type must not be {@literal null}.
	 */
	public JsonViewHttpMessageConverter(Class<?> type) {
		super(type);
	}

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
//        System.out.println("------JsonHttpMessageConverter read");
		// 解密
//		String json = AESUtil.decrypt (inputMessage.getBody ());
        JavaType javaType = getJavaType(type, contextClass);
        //转换
		Object reqData = this.objectMapper.readValue(inputMessage.getBody(), javaType);

		System.out.println("请求参数>>>>>>>>>>>>>>" + JSON.toJSONString(reqData));

        return reqData;
//		return super.read(type,contextClass,inputMessage);
    }

    @Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
//		System.out.println("------JsonHttpMessageConverter writeInternal");

//		object.toString();

		//使用Jackson的ObjectMapper将Java对象转换成Json String
	    ObjectMapper mapper = this.objectMapper;
	    String json = mapper.writeValueAsString(object);
		System.out.println("请求响应返回>>>>>>>>>>>" + json);
	    //加密
//	    String result = AESUtils.jdkAESEncode(keyStr, json);
	    //输出
	    outputMessage.getBody().write(json.getBytes());
	}
	
	@Override
	public ObjectMapper getObjectMapper() {
		return this.objectMapper;
	}
	
	@Override
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

}
