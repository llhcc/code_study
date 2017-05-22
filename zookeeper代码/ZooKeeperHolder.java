/**
 * 
 */
package com.whty.framework.zookeeper;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger; 
import org.apache.zookeeper.data.Stat;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.framework.api.CuratorWatcher;
import com.netflix.curator.framework.state.ConnectionState;
import com.netflix.curator.framework.state.ConnectionStateListener;
import com.netflix.curator.retry.ExponentialBackoffRetry;

/**
 * 
 */
public class ZooKeeperHolder {

	private static final Logger LOGGER = LogManager.getLogger(ZooKeeperHolder.class);

	private static ResourceBundle rb = ResourceBundle.getBundle("zk");

	private static CuratorFramework zkclient;

	static {
		init();
	}
	
	private static void init() {
		String connStr = rb.getString("server");
		int connectionTimeoutMs = Integer.valueOf(rb.getString("connectionTimeoutMs"));
		int sessionTimeoutMs = Integer.valueOf(rb.getString("sessionTimeoutMs"));
		String namespace = rb.getString("namespace");

		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);

		try {
			zkclient = CuratorFrameworkFactory.builder()
					.connectString(connStr)
					.namespace(namespace)
					.retryPolicy(retryPolicy)
					.connectionTimeoutMs(connectionTimeoutMs)
					.sessionTimeoutMs(sessionTimeoutMs).build();

			zkclient.start();

			final Semaphore s = new Semaphore(0);
			zkclient.getConnectionStateListenable().addListener(new ConnectionStateListener() {
				@Override
				public void stateChanged(CuratorFramework client,
						ConnectionState state) {
					if (state.equals(ConnectionState.CONNECTED)) {
						s.release();
					}
				}

			});
			s.acquire();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public static void registWatcher(String path, CuratorWatcher watcher) {
		try {
			zkclient.getData().usingWatcher(watcher).forPath(path);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public static byte[] getDataAndWatcher(String path, CuratorWatcher watcher) {
		try {
			LOGGER.info("start read from zookeeper:" + path);
			byte[] data = zkclient.getData().usingWatcher(watcher).forPath(path);
			if (data != null) {
				LOGGER.info("read zookeeper success:" + path + ":" + new String(data));
			} else {
				LOGGER.info("read zookeeper fail,no data:" + path);
			}
			return data;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	public static byte[] getData(String path) {
		try {
			LOGGER.info("start read from zookeeper:" + path);
			byte[] data = zkclient.getData().forPath(path);
			if (data != null) {
				LOGGER.info("read zookeeper success:" + path + ":" + new String(data));
			} else {
				LOGGER.info("read zookeeper fail,no data:" + path);
			}
			return data;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	public static String getStrData(String path) {
		try {
			LOGGER.info("start read from zookeeper:" + path);
			byte[] data = zkclient.getData().forPath(path);
			if (data != null) {
				LOGGER.info("read zookeeper success:" + path + ":" + new String(data));
				return new String(data);
			}
			return "";
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	public static Stat setData(String path, byte[] data) {
		try {
			return zkclient.setData().forPath(path, data);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	public static CuratorFramework getClient() {
		return zkclient;
	}

}
