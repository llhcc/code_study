package com.whty.cms.unit.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ThreadPoolUtils {
	
	private static ExecutorService exec = Executors.newCachedThreadPool();
	
	private static void init() {
		exec = Executors.newCachedThreadPool();
	}
	
	public static void execute(Runnable command) {
		if (exec == null) {
			init();
		}
		exec.execute(command);
	}
	
	/**
	 * 关闭线程池使之不再接受新的任务，之前添加进来的任务会继续执行
	 * @date	2015年6月17日
	 * @desc
	 */
	public static void shutdown() {
		if (exec != null) {
			exec.shutdown();
		}
	}

}
