package com.whty.framework.util;

import java.util.UUID;

public class UUIDUtils {
	
	public static String getRandomUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
	
	public static void main(String[] args) {
		System.out.println(UUIDUtils.getRandomUUID());
	}

}
