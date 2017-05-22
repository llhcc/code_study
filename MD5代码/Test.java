package com.whty.cms.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

public class Test {
	
	private static final Logger LOGGER = LogManager.getLogger(Test.class);
	
	/**
	 * 获取单个文件的MD5值！
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = null;
		if(file.length() > 1024 * 1024 * 1024){
			buffer = new byte[1024 * 128];
		}else{
			buffer = new byte[1024 * 8];
		}
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null) in.close();
			} catch(IOException ex) {
				LOGGER.error("关闭输入输出流异常", ex);
			}
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		String result = bigInt.toString(16);
		int length = result.length();
		if (length < 32) {
			for (int i = 0; i < (32 - length); i++) {
				result = "0" + result;
			}
		}
		return result;
	}
	
	public static void main(String args[]){
		File file = new File("D://oracle11_client//win32_11gR2_database_1of2.zip");
		long start = System.currentTimeMillis();
		String md5 = getFileMD5(file);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println(md5);
		//315188059950200674536161725854254497989
		//ed1f0324983f674700030098d07600c5
		
		//82203
		//dfb1d53e34068d5326a06242f01f11eb
		
		//25813
		//dfb1d53e34068d5326a06242f01f11eb
	}

}
