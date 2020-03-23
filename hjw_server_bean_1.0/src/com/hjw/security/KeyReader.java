package com.hjw.security;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;

import javax.crypto.Cipher;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/*
 *
 * Copyright:   Copyright (c) 2007��2008
 * Company:     syntongs
 * author       yangm
 * version      2.3.1.0
 */
public class KeyReader {

    /**
     *
     * 
     * @author: yangm
     * @create: Jan 7, 2009
     * @document:
     * @param p12FileName
     * @param pfxPassword
     * @return
     */
    public static PublicKey Publickey(String p12FileName, String pfxPassword,
            String keyAlias) {
        PublicKey pubKey = null;
        try {
            InputStream fis = new FileInputStream(p12FileName);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, pfxPassword.toCharArray());
            Certificate c = ks.getCertificate(keyAlias);
            X509Certificate t = (X509Certificate) c;
            pubKey = t.getPublicKey();
            return pubKey;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }

    }

    /**
     * 
     * 
     * @author: yangm
     * @create: Jan 7, 2009
     * @document:
     * @param p12FileName
     * @param pfxPassword
     * @return
     */
    public static PrivateKey PrivateKey(String p12FileName, String pfxPassword,
            String keyAlias) {
        PrivateKey priKey = null;
        try {
            InputStream fis = new FileInputStream(p12FileName);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, pfxPassword.toCharArray());

            priKey = (PrivateKey) ks
                    .getKey(keyAlias, pfxPassword.toCharArray());
            return priKey;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     *
     * 
     * @author: yangm
     * @create: Jan 7, 2009
     * @document:
     * @param priKey
     * @param oriString
     * @return
     */
    public static byte[] sign(PrivateKey priKey, byte[] string) {
        try {
            Signature sig = Signature.getInstance("MD5withRSA");
            sig.initSign(priKey);
            sig.update(string);
            byte[] signature = sig.sign();
            return signature;
        } catch (Exception e0) {
            System.out.println(e0.toString());
            return null;
        }
    }

    /**
     * 
     * 
     * @author: yangm
     * @create: Jan 7, 2009
     * @document:
     * @param priKey
     * @param oriString
     * @return
     */
    public static boolean verifySign(PublicKey pubKey, byte[] string,
            byte[] oriString) {
        try {
            Signature sig = Signature.getInstance("MD5withRSA");
            sig.initVerify(pubKey);
            sig.update(string);
            boolean sing = sig.verify(oriString);
            return sing;
        } catch (Exception e0) {
            System.out.println(e0.toString());
            return false;
        }
    }

    /** 
     * @author: yangm
     * @create: Jan 7, 2009
     * @document:
     * @param priKey
     * @param oriString
     * @return
     */
    public static String encryptDecrypt(Key key,int cipherMode, String oriString) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(cipherMode, key);
            byte[] decstr=null;
            String sed=null;
            if (cipherMode==Cipher.DECRYPT_MODE)
            {
                decstr = Base64.base64Decode(oriString);
            }else if (cipherMode==Cipher.ENCRYPT_MODE)
            {
            	decstr = Base64.base64Decode(oriString);
            }
                decstr = cipher.doFinal(decstr);
            if (cipherMode==Cipher.ENCRYPT_MODE)
            {
                sed= Base64.base64Encode(decstr);
            }else if (cipherMode==Cipher.DECRYPT_MODE)
            {
                sed= Base64.base64Encode(decstr);
            }
            return sed;
        } catch (Exception e0) {
            System.out.println(e0.toString());
            return null;
        }
    }
    


    /**
     * 
    * Description: 
    * Title: encryptDecrypt2
    * @author    yangm
    * @date      May 13, 2009
    * @param key
    * @param keytype
    * @param cipherMode
    * @param oriString
    * @return
     */
    public static String encryptDecrypt2(Key key,int keytype,int cipherMode, String oriString) {
        RSAPublicKey pubkey=null;
        RSAPrivateKey prikey=null;
        BigInteger e = null;
        BigInteger n =null;
        try {
            byte[] bytestr=null;
            String sed=null;
            if (cipherMode==Cipher.DECRYPT_MODE)
            {
                bytestr = Base64.base64Decode(oriString);
                if(1==keytype)
                {
                    pubkey= (RSAPublicKey)key;
                    e = pubkey.getPublicExponent();
                    n = pubkey.getModulus();
                }else if(2==keytype){
                    prikey= (RSAPrivateKey)key;
                    e = prikey.getPrivateExponent();
                    n = prikey.getModulus();
                }
            }else if (cipherMode==Cipher.ENCRYPT_MODE)
            {
                bytestr= oriString.getBytes();
                if(1==keytype)
                {
                    prikey= (RSAPrivateKey)key;
                    e = prikey.getPrivateExponent();
                    n = prikey.getModulus();
                }else if(2==keytype){
                    pubkey= (RSAPublicKey)key;
                    e = pubkey.getPublicExponent();
                    n = pubkey.getModulus();
                }
            }
            BigInteger m = new BigInteger(bytestr);
            BigInteger c = m.modPow(e, n);
            byte[] cs = c.toByteArray();
            if (cipherMode==Cipher.ENCRYPT_MODE)
            {
                sed= Base64.base64Encode(cs);
            }else if (cipherMode==Cipher.DECRYPT_MODE)
            {
                sed= new String(cs);
            }
            return sed;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return ex.getMessage();
        }
    }
     
    /**
     * 
     * 
     * @author: yangm
     * @create: Dec 26, 2008
     * @document:
     * @param cerFileName
     * @param p12FileName
     * @param pfxPassword
     * @return
     */
    public static PublicKey Publickey(String cerFileName) {
        PublicKey pubKey = null;
        try {
            InputStream fis = new FileInputStream(cerFileName);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");  
            Certificate c = cf.generateCertificate(fis);  
            X509Certificate t = (X509Certificate) c;
            pubKey = t.getPublicKey();
            return pubKey;

        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
 
    public static void main(String[] args) throws Exception {
        String p12FileName = "c:/key/web-client.p12";
        String cerFileName2= "c:/key/web-client11.cer";
        String pfxPassword = "openssl";
        String keyAlias = "web-client"; // 
      
    }

}