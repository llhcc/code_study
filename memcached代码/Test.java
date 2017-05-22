package com.whty.framework.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test {
	
	private static final Logger LOGGER = LogManager.getLogger(Test.class);
	
	private static MemcachedClient memcachedClient;
	
	private Test() {}
	
	private static void init() {
		
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("127.0.0.1:11211"));
		
		builder.setSessionLocator(new KetamaMemcachedSessionLocator());
		
		builder.setConnectionPoolSize(Integer.valueOf("2"));
		
		try {
			memcachedClient = builder.build();
			memcachedClient.setOpTimeout(3000);
		} catch(IOException ex) {
			LOGGER.error("初始化MemcachedClient错误:", ex);
		}
	}
	
	private static MemcachedClient getMemcachedClient() {
		if (memcachedClient == null) {
			init();
		}
		return memcachedClient;
	}
	
	/**
	 * 添加数据到memcache缓存
	 * @param key
	 * @param obj
	 * @return
	 */
	public static boolean putObj(String key, Object obj, int secs) {
		boolean ret = false;
		try {
			ret = getMemcachedClient().set(key, secs, obj);
		} catch(Exception ex) {
			LOGGER.error("添加memcache缓存数据错误:", ex);
		}
		return ret;
	}
	
	/**
	 * 获取memcache缓存数据
	 * @param key
	 * @return
	 */
	public static Object getObj(String key) {
		Object obj = null;
		try {
			obj = getMemcachedClient().get(key);
		} catch(Exception ex) {
			LOGGER.error("获取memcache缓存数据错误:", ex);
		}
		return obj;
	}
	
	/**
	 * 批量获取memcache缓存数据
	 * @param keys
	 * @return
	 */
	public static Map<String,Object> getObjs(List<String> keys) {
		Map<String,Object> objs = null;
		try {
			objs = getMemcachedClient().get(keys);
		} catch(Exception ex) {
			LOGGER.error("批量获取memcache缓存数据错误:", ex);
		}
		return objs;
	}
	
	/**
	 * 更新memcache缓存数据
	 * @param key
	 * @param obj
	 * @param secs
	 * @return
	 */
	public static boolean updateObj(String key, Object obj, int secs) {
		boolean ret = false;
		try {
			ret = getMemcachedClient().replace(key, secs, obj);
		} catch(Exception ex) {
			LOGGER.error("更新memcache缓存数据错误:", ex);
		}
		return ret;
	}
	
	/**
	 * 删除memcache缓存数据
	 * @param key
	 * @return
	 */
	public static boolean delObj(String key) {
		boolean ret = false;
		try {
			ret = getMemcachedClient().delete(key);
		} catch(Exception ex) {
			LOGGER.error("删除memcache缓存数据错误:", ex);
		}
		return ret;
	}
	
	/**
	 * @desc	清空memcache缓存
	 */
	public static void flushAll() {
		try {
			getMemcachedClient().flushAll();
		} catch(Exception ex) {
			LOGGER.error("清空memcache缓存出错：", ex);
		}
	}
	

	private static void shutdown(){
		try {
			memcachedClient.shutdown();
		} catch (IOException e) {
			LOGGER.error("关闭MemcachedClient错误:", e);
		}
	}
	
//	private static void initConfByPropertes(){
//		serverIps = StringUtils.isNotEmpty(rb.getString("memcache.seaver.ips")) ? rb.getString("memcache.seaver.ips") : "localhost:11211";
//		poolSize = StringUtils.isNotEmpty(rb.getString("memcache.poolSize")) ? Integer.valueOf(rb.getString("memcache.poolSize")) : 2;
//	}
	
	public static void main(String args[]){
		try {
			Object obj = getObj("sessionUserId");
			putObj("sessionUserId",obj,24*60*60);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
