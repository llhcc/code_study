package com.llh.common.exception;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GatewayExceptionResolver implements HandlerExceptionResolver{
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		response.setContentType("text/plain;charset=UTF-8");
		ResponseMessage responseMessage = ResponseMessage.getFailedResponse();
		String message = StringUtils.isNotEmpty(exception.getMessage()) ? exception.getMessage() : exception.getClass().getName();
		responseMessage.setResultDesc(message);

		String requestUri = request.getRequestURI();
		String appName = request.getContextPath();
		String url = requestUri.substring(appName.length());
		String result = gson.toJson(responseMessage);
		BufferedOutputStream out = null;
		ByteArrayInputStream in = null;
		try {
			out = new BufferedOutputStream(response.getOutputStream());
			in = new ByteArrayInputStream(result.getBytes("UTF-8"));
			byte[] buff = new byte[1024 * 512];
			int len = -1;
			while ((len = in.read(buff)) > 0) {
				out.write(buff, 0, len);
			}
			out.flush();
		} catch(IOException e) {
		} finally {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
			} catch(IOException ex1) {
			}
		}
		
		return null;
	}

}
