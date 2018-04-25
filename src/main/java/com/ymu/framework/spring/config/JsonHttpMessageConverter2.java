package com.ymu.framework.spring.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonHttpMessageConverter2
		extends MappingJackson2HttpMessageConverter {
	
	ObjectMapper objectMapper = new CustomObjectMapper();//默认
	
	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		System.out.println("------JsonHttpMessageConverter readInternal");
		//解密
	    String json = "{}";
	    JavaType javaType = getJavaType(clazz, null);
	    //转换
	    return this.objectMapper.readValue(json, javaType);
	}
	
	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		System.out.println("------JsonHttpMessageConverter writeInternal");
		//使用Jackson的ObjectMapper将Java对象转换成Json String
//	    ObjectMapper mapper = this.objectMapper;
//	    String json = mapper.writeValueAsString(object);
//	    //加密
//	    String result = AESUtils.jdkAESEncode(keyStr, json);
//	    //输出
//	    outputMessage.getBody().write(result.getBytes());
		
		super.writeInternal(object, type, outputMessage);

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
