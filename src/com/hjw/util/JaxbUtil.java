package com.hjw.util;

import java.io.StringReader;  
import java.io.StringWriter;  
 
import javax.xml.bind.JAXBContext;  
import javax.xml.bind.Marshaller;  
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Node;  
 
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.util   
     * @Description:  
     * @author: yangm     
     * @date:   2016年10月6日 下午8:45:31   
     * @version V2.0.0.0
 */
public class JaxbUtil {  
 
   /** 
    * JavaBean转换成xml 
    * 默认编码UTF-8 
    * @param obj 
    * @param writer 
    * @return  
    */  
   public static String convertToXml(Object obj,boolean falgs) {  
       return convertToXml(obj, "UTF-8",falgs);  
   }  
 
   /** 
    * JavaBean转换成xml 
    * @param obj 
    * @param encoding  
    * @return  
    */  
   public static String convertToXml(Object obj, String encoding,boolean formatted) {  
       String result = null;  
       try {  
           JAXBContext context = JAXBContext.newInstance(obj.getClass());  
           Marshaller marshaller = context.createMarshaller();  
           // Marshaller.JAXB_FORMATTED_OUTPUT 决定是否在转换成xml时同时进行格式化（即按标签自动换行，否则即是一行的xml）
           //Marshaller.JAXB_ENCODING xml的编码方式
           marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);  
           marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
           StringWriter writer = new StringWriter();  
           marshaller.marshal(obj, writer);  
           result = writer.toString();  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
 
       return result;  
   }  
 
   /** 
    * xml转换成JavaBean 
    * @param xml 
    * @param c 
    * @return 
    */  
   @SuppressWarnings("unchecked")  
   public static <T> T converyToJavaBean(String xml, Class<T> c) throws Exception  {  
       T t = null;    
           JAXBContext context = JAXBContext.newInstance(c);  
           Unmarshaller unmarshaller = context.createUnmarshaller();  
           t = (T) unmarshaller.unmarshal(new StringReader(xml));  
       
       return t;  
   }  
}  