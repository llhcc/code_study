package com.llh.proxy;

import java.lang.reflect.Proxy;

public class HelloWorldTest {
	public static void main(String args[]) {
		//动态代理
		HelloWorld obj = new HelloWorldImpl();
		HelloWorldHandler handler = new HelloWorldHandler(obj);
		HelloWorld proxy = (HelloWorld) Proxy.newProxyInstance(obj.getClass()
				.getClassLoader(), obj.getClass().getInterfaces(), handler);
		proxy.sayHelloWorld();
	}
}
