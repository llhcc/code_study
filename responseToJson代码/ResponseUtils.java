package com.whty.cms.unit.common.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseUtils {
	
	private static final Logger LOGGER = LogManager.getLogger(ResponseUtils.class);
	
	private static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	public static void responseToJson(HttpServletResponse response, Object obj) {
		response.setContentType("application/json;charset=UTF-8");
		BufferedOutputStream out = null;
		ByteArrayInputStream in = null;
		String result = gson.toJson(obj);
		try {
			out = new BufferedOutputStream(response.getOutputStream());
			in = new ByteArrayInputStream(result.getBytes("UTF-8"));
			byte[] buff = new byte[1024 * 512];
			int len = -1;
			while ((len = in.read(buff)) > 0) {
				out.write(buff, 0, len);
			}
			out.flush();
		} catch(IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} finally {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
			} catch(IOException ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
	}

}
