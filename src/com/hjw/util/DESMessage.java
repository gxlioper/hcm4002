package com.hjw.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
/*
 *
 * Copyright:   Copyright (c) 2007��2008
 * Company:     syntongs
 * author       yangm
 * version      2.3.1.0
 */
public class DESMessage {

    /**
     * 
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param str
     * @param strkey
     * @return
     * @throws Exception
     */
    public static String encString(String str, String strkey) throws Exception {
        byte rawKeyData[] = strkey.getBytes();
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte encryptedClassData[] = cipher.doFinal(str.getBytes());
        return byte2hex(encryptedClassData);
    }

	/**
     * 
     * @param b
     * @return
     */

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if(stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if((b.length % 2) != 0)
			throw new IllegalArgumentException("");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

    /**
     * 
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param str
     * @param strkey
     * @return
     * @throws Exception
     */
    public static String decString(String str, String strkey) {
    	try{
        byte rawKeyData[] = strkey.getBytes();   
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte decryptedData[] = cipher.doFinal(hex2byte(str.getBytes()));
        return new String(decryptedData);
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return "";
    	}
    }

    public static String getStrKey(String str) {    	
        try {
        	String strkey="h14j818w";
            String dec = DESMessage.encString(str, strkey);
            dec = DESMessage.encString(dec, strkey);
            return "ok-"+dec.substring(0,12);
        } catch (Exception ex) {
            return "error-计算错误";
        }
       }

    /**
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param args
     */
    public static void main(String[] args) {
    	
        try {
        	String strkey="h8j1w8ww";
            System.out.println(strkey);
            String str = "123456";
            String dec = DESMessage.encString(str, strkey);
            System.out.println(dec);
            String enc = DESMessage.decString(dec, strkey);
            System.out.println(enc);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
       }
}
