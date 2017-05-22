package com.llh.message;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;

public class StrHttpMessageConverter extends StringHttpMessageConverter{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return String.class.equals(clazz);
	}

	@Override
	protected String readInternal(Class<? extends String> clazz,
			HttpInputMessage inputMessage) throws IOException {
		ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) inputMessage;
		HttpServletRequest request = serverRequest.getServletRequest();
		String requestUri = request.getRequestURI();
		String appName = request.getContextPath();
		String url = requestUri.substring(appName.length());
		String strBody = IOUtils.toString(inputMessage.getBody(), "UTF-8").replaceAll("[\\n\\r]", "");
		JSONObject json = JSONObject.fromObject(strBody);
		return json.toString();
	}

	@Override
	protected void writeInternal(String s, HttpOutputMessage outputMessage)
			throws IOException {
		super.writeInternal(s, outputMessage);
	}

}
