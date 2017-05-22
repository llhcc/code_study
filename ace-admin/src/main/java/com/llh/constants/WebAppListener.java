package com.llh.constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class WebAppListener extends ContextLoaderListener{

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ServletContext sc = event.getServletContext();
		if(null != sc){
			ObjectFactory.init(sc);
			WebApp.PATH = sc.getContextPath();
		}
	}

}
