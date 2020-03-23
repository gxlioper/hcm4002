package com.hjw.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
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
     * 
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @return
     * @throws Exception
     */
    public static String getKey() throws Exception {
        String res = "";
        SecureRandom sr = new SecureRandom();
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        kg.init(sr);
        SecretKey key = kg.generateKey();
        res = Base64.base64Encode(key.getEncoded());
        return res;
    }

    /**
     * 
     * 
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param str
     * @param strkey
     * @return
     * @throws Exception
     */
    public static String encString(String str, String strkey,String codetype) throws Exception {
        //byte rawKeyData[] = Base64.base64Decode(strkey);
    	byte rawKeyData[] = strkey.getBytes();
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte encryptedClassData[] = cipher.doFinal(str.getBytes(codetype));
        return Base64.base64Encode(encryptedClassData);
    }

    /**
     * 
     * 
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param str
     * @param strkey
     * @return
     * @throws Exception
     */
    public static String decString(String str, String strkey,String codetype) throws Exception {
        //byte rawKeyData[] = Base64.base64Decode(strkey); 
    	byte rawKeyData[] = strkey.getBytes();
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] xml=Base64.base64Decode(str);
        byte decryptedData[] = cipher.doFinal(xml);
        return new String(decryptedData,codetype);
    }

    /**
     * 
     * 
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param str
     * @param strkey
     * @return
     * @throws Exception
     */
    public static String encString2(String str, String strkey) throws Exception {
    	byte rawKeyData[] = strkey.getBytes();
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte encryptedClassData[] = cipher.doFinal(str.getBytes());
        return Base64.base64Encode(encryptedClassData);
    }
    
    /**
     * 
     * 
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param str
     * @param strkey
     * @return
     * @throws Exception
     */
    public static String encString2(String str, byte[] strkey) throws Exception {
    	byte rawKeyData[] = strkey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte encryptedClassData[] = cipher.doFinal(str.getBytes());
        return Base64.base64Encode(encryptedClassData);
    }
    
    public static byte[] hexStringToByte(String hex) {
	    int len = (hex.length() / 2);
	    byte[] result = new byte[len];
	    char[] achar = hex.toCharArray();
	    for (int i = 0; i < len; i++) {
	     int pos = i * 2;
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
	    }
	    return result;
	}

    private static byte toByte(char c) {
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);
	    return b;
	}
    /**
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param args
     */
    public static void main(String[] args) {
    	try{
    		String key = "syn123to";
    	String dd = DESMessage.encString2("123456",key);
    	System.out.println(dd);
         dd = DESMessage.decString("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","hjwwx001","utf-8");
         System.out.println(dd);
         //System.out.println(new String(Base64.base64Decode(dd)));
    	}catch(Exception ex){
    		System.out.println(ex.toString());
    	}
       }
}
