package com.llh.thread;

import java.io.PrintWriter;

import javax.servlet.AsyncContext;

public class AsyncRequest implements Runnable {
	private AsyncContext ctx;

	public AsyncRequest(AsyncContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void run() {
		try {
			// 模拟长时间的处理
			Thread.sleep(10000);
			PrintWriter out = ctx.getResponse().getWriter();
			out.println("久等了...XD");
			// 这边才真正送出回应
			ctx.complete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
