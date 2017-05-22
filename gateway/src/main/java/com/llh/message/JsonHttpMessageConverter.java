package com.llh.message;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Assert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.llh.common.CheckParamsUtil;

public class JsonHttpMessageConverter extends AbstractHttpMessageConverter<Object>{

	private Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		JavaType javaType = getJavaType(clazz);
		return (this.objectMapper.canDeserialize(javaType) && canRead(mediaType));
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return (this.objectMapper.canSerialize(clazz) && canWrite(mediaType));
	}
	
	public void setObjectMapper(ObjectMapper objectMapper) {
		Assert.notNull(objectMapper, "ObjectMapper must not be null");
		this.objectMapper = objectMapper;
	}

	/**
	 * Return the underlying {@code ObjectMapper} for this view.
	 */
	public ObjectMapper getObjectMapper() {
		return this.objectMapper;
	}
	
	protected JavaType getJavaType(Class<?> clazz) {
		return TypeFactory.type(clazz);
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) inputMessage;
		HttpServletRequest request = serverRequest.getServletRequest();
		String requestUri = request.getRequestURI();
		String appName = request.getContextPath();
		String url = requestUri.substring(appName.length());
		try {
			String strBody = IOUtils.toString(inputMessage.getBody(), "UTF-8").replaceAll("[\\n\\r]", "");
			JSONObject json = JSONObject.fromObject(strBody);
			CheckParamsUtil.checkParams(url, json.toString()); // 参数校验
			Object obj = gson.fromJson(json.toString(), clazz);
			return obj;
		} catch(Exception ex) {
			throw new HttpMessageNotReadableException(ex.getMessage());
		}
	}

	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		String response = gson.toJson(t);
		System.out.println(response);
		
		BufferedOutputStream out = null;
		ByteArrayInputStream in = null;
		
		in = new ByteArrayInputStream(response.getBytes("UTF-8"));
		out = new BufferedOutputStream(outputMessage.getBody());
		byte bytes[] = new byte[1024]; 
		int len = -1;
		while((len = in.read(bytes)) > 0){
			out.write(bytes,0,len);
		}
		out.flush();
		out.close();
		in.close();
		
	}

}
