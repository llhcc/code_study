package com.whty.cms.common.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.whty.framework.util.CollectionUtils;

public class IPAccessFilter implements Filter {
	
	private static final Logger LOGGER = LogManager.getLogger(IPAccessFilter.class);
	
	private List<String> ignoreIPList;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String remoteAddr = request.getRemoteAddr();
		LOGGER.info("remoteAddr: " + remoteAddr);
		if (ignoreIPList.contains(remoteAddr)) {
			chain.doFilter(servletRequest, servletResponse);
		} else {
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			response.sendRedirect("http://www.wuhaneduyun.cn/notice/");
		}
	}

	@Override
	public void destroy() {}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ignoreIPList = CollectionUtils.newArrayList();
		ignoreIPList.add("61.183.248.34");
		ignoreIPList.add("61.183.248.35");
		ignoreIPList.add("61.183.248.36");
		ignoreIPList.add("61.183.248.37");
		ignoreIPList.add("61.183.248.38");
		ignoreIPList.add("221.234.47.81");
		ignoreIPList.add("221.234.47.82");
		ignoreIPList.add("221.234.47.83");
		ignoreIPList.add("221.234.47.84");
		ignoreIPList.add("221.234.47.85");
		ignoreIPList.add("116.211.105.33");
		ignoreIPList.add("116.211.105.34");
		ignoreIPList.add("116.211.105.35");
		ignoreIPList.add("116.211.105.36");
		ignoreIPList.add("116.211.105.37");
		ignoreIPList.add("116.211.105.38");
		ignoreIPList.add("116.211.105.39");
		ignoreIPList.add("116.211.105.40");
		ignoreIPList.add("116.211.105.41");
		ignoreIPList.add("116.211.105.42");
		ignoreIPList.add("116.211.105.43");
		ignoreIPList.add("116.211.105.44");
		ignoreIPList.add("116.211.105.45");
		ignoreIPList.add("116.211.105.46");
		ignoreIPList.add("113.57.219.33");
		ignoreIPList.add("113.57.219.34");
		ignoreIPList.add("113.57.219.35");
		ignoreIPList.add("113.57.219.36");
		ignoreIPList.add("113.57.219.37");
		ignoreIPList.add("113.57.219.38");
		ignoreIPList.add("113.57.219.39");
		ignoreIPList.add("113.57.219.40");
		ignoreIPList.add("113.57.219.41");
		ignoreIPList.add("113.57.219.42");
		ignoreIPList.add("113.57.219.43");
		ignoreIPList.add("113.57.219.44");
		ignoreIPList.add("113.57.219.45");
		ignoreIPList.add("113.57.219.46");
		ignoreIPList.add("111.47.123.33");
		ignoreIPList.add("111.47.123.34");
		ignoreIPList.add("111.47.123.35");
		ignoreIPList.add("111.47.123.36");
		ignoreIPList.add("111.47.123.37");
		ignoreIPList.add("111.47.123.38");
		ignoreIPList.add("111.47.123.39");
		ignoreIPList.add("111.47.123.40");
		ignoreIPList.add("111.47.123.41");
		ignoreIPList.add("111.47.123.42");
		ignoreIPList.add("111.47.123.43");
		ignoreIPList.add("111.47.123.44");
		ignoreIPList.add("111.47.123.45");
		ignoreIPList.add("111.47.123.46");
		ignoreIPList.add("116.211.105.49");
		ignoreIPList.add("116.211.105.50");
		ignoreIPList.add("116.211.105.51");
		ignoreIPList.add("116.211.105.52");
		ignoreIPList.add("116.211.105.53");
		ignoreIPList.add("116.211.105.54");
	}

}
