package com.hjw.security;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



/*
 *
 * Copyright: Copyright (c) 2005
 * Company: synjones
 * author yangm
 * version 2.0
 */

public class Base64 {
    
    /**
     * base64缂栫爜锟斤拷锟斤拷
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param s
     * @return
     */
    public static String base64Encode(byte[] s)    
    {   
        if (s == null)   
            return null;   
        BASE64Encoder b = new BASE64Encoder();   
        return b.encode(s);   
    }   
       
    /**
     * base64瑙ｇ爜锟斤拷锟斤拷
     * @author: yangm
     * @create: Dec 25, 2008
     * @document:
     * @param s
     * @return
     * @throws IOException
     */
    public static byte[] base64Decode(String s) throws IOException    
    {
        if (s == null)   
        {
           return null;
        }   
        BASE64Decoder decoder = new BASE64Decoder();   
        byte[] b = decoder.decodeBuffer(s);   
        return b;   
    }   
}