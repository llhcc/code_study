package com.whty.cms.web.common.util;

import java.security.MessageDigest;

import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger; 

public class MD5Encrypt {
    protected static final Logger log = LogManager.getLogger(MD5Encrypt.class);

    public MD5Encrypt() {
    }

    /**
     * To encrypt the password
     * 
     * @param password
     *            String
     * @throws ConsoleException
     * @return String
     */
    public static String encrypt(String password) {

	String encryptPasswd = "";

	try {
	    MessageDigest alg = MessageDigest.getInstance("MD5");
	    alg.update(password.getBytes());
	    byte[] digesta = alg.digest();

	    encryptPasswd = byte2hex(digesta);
	} catch (Exception e) {
	    log.error(e);
	}

	return encryptPasswd;
    }

    private static String byte2hex(byte[] b) {

	StringBuffer hs = new StringBuffer();
	String stmp = "";
	int len = b.length;
	for (int i = 0; i < len; i++) {
	    stmp = (java.lang.Integer.toHexString(b[i] & 0XFF));
	    if (stmp.length() == 1) {
		hs.append("0").append(stmp);
	    } else
		hs.append(stmp);
	}

	return hs.toString();
    }

    public static void main(String[] args) throws Exception {
	System.out.println(MD5Encrypt.encrypt("111111"));
    }

}
