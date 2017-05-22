package com.llh.constants;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ApplicationContext是Spring的核心，Context我们通常解释为上下文环境，我想用“容器”来表述它更容易理解一些，
 * ApplicationContext则是“应用的容器”了；在Web应用中，我们会用到WebApplicationContext，
 * WebApplicationContext继承自ApplicationContext
 * ；WebApplicationContext的初始化方式和BeanFactory
 * .ApplicationContext有所区别,因为WebApplicationContext需要ServletContext实例
 * ,也就是说它必须拥有Web容器的前提下才能完成启动的工作
 * .有过Web开发经验的读者都知道可以在web.xml中配置自启动的Servlet或定义Web容器监听器
 * (ServletContextListener),借助着两者中的任何一个,我们就可以启动Spring Web应用上下文的工作.
 * 
 * @author lenovo
 *
 */
public class ObjectFactory {
	private static WebApplicationContext webApplicationContext = null;
	
	public static void init(ServletContext sc) {
		if(null == webApplicationContext){
			webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		}
	}
	
	public static Object getBean(String name){
		if(null != webApplicationContext){
			return webApplicationContext.getBean(name);
		}else{
			return null;
		}
	}

}
