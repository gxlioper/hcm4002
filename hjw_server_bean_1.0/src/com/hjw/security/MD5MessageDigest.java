package com.hjw.security;

import java.security.MessageDigest;

/*
 *
 * Copyright:   Copyright (c) 2007閿熸枻鎷�2008
 * Company:     syntongs
 * author       yangm
 * version      2.3.1.0
 */

public class MD5MessageDigest {
    /**
     */
    public static  byte[] computeMD5(String inputStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(inputStr.getBytes("utf-8"));
            byte hash[]  = digest.digest();
            return hash;
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return null;
        }
    }
 
    
    public static String bytesToString(byte[] b) {
        StringBuffer result = new StringBuffer("");
        if (b != null) {
            int length = b.length;
            for (int i = 0; i < length; i++) {
                result.append((char) (b[i] & 0xff));
            }
        }
        return result.toString();
    }
    
    public static void printHexString( byte[] b) {
   	 for (int i = 0; i < b.length; i++) {
   		 String hex = Integer.toHexString(b[i] & 0xFF);      
   		 if (hex.length() == 1) {        
   			 hex = '0' + hex;      
   		}      
   		 System.out.print(hex.toUpperCase() );   
   		 }
   	 }
    
    public static void main(String[] ddd) throws Exception
    {
        String xmlmsg = "1|2a9304zl25e25geaa5aff574153eafc95c97672c6|20160121114039";
       // byte[] mm=MD5MessageDigest.computeMD5(dd);
        
        byte[] digs = MD5MessageDigest.computeMD5(xmlmsg);
       
        printHexString(digs);
    }
} 

