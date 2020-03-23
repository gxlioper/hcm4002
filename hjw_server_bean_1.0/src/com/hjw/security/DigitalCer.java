package com.hjw.security; 

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Title: DigitalCer.java
 * Description: 一卡通电子支付通用接口
 * Copyright: Copyright (c) 2009
 * Company: Syntong
 * @author yangm
 * @date Dec 10, 2009
 * @version 2.6.0.0
 */
public class DigitalCer {
	
	
	/**
	 * 加密
	 * @param pri_cerfilepath 私钥路径
	 * @param pri_cerfilename 私钥名称
	 * @param pri_cerfilepwd   私钥密码
	 * @param pub_cerfilepath   公钥路径
	 * @param xml               要加密的字符串
	 * @return
	 * @throws Exception
	 */
	/*public static String[] Encrypt_Xml(String pri_cerfilepath,String pri_cerfilename,String pri_cerfilepwd,String pub_cerfilepath,String xml) throws Exception
	{
		String[] res = new String[2];
		String xmlmsg = xml;
		String p12FileName = pri_cerfilepath;
		String pfxPassword = pri_cerfilepwd;
		String keyAlias = pri_cerfilename;
		String cerFileName2 = pub_cerfilepath;
		String xmlstr = xmlmsg.replaceAll("[^x00-xff]*","");
		//xmlstr = new String(base64Encode(xmlstr.getBytes()));
		byte[] digs = MD5MessageDigest.computeMD5(xmlstr);
		PrivateKey privatekey = KeyReader.PrivateKey(p12FileName, pfxPassword,
				keyAlias);
		byte[] ddd = KeyReader.sign(privatekey, digs);
		String mess = base64Encode(ddd);
		xmlmsg = new String(base64Encode(xmlmsg.getBytes("utf-8")));
		String smsge = "xmlmess=" + xmlmsg + "&mess=" + mess;
		String strkey = DESMessage.getKey();
		res[0] = DESMessage.encString(smsge, strkey);
		PublicKey publickey = KeyReader.Publickey(cerFileName2);
		res[1] = KeyReader.encryptDecrypt(publickey, 
				Cipher.ENCRYPT_MODE, strkey);
		return res;
	}*/
	
	
	 public static String[] Encrypt_Xml(String pri_cerfilepath, String pri_cerfilename, String pri_cerfilepwd, String pub_cerfilepath, String xml,String codetype)
			    throws Exception
			  {
			    String[] res = new String[2];
			    String xmlmsg = xml;
			    String p12FileName = pri_cerfilepath;
			    String pfxPassword = pri_cerfilepwd;
			    String keyAlias = pri_cerfilename;
			    String cerFileName2 = pub_cerfilepath;
			    byte[] digs = MD5MessageDigest.computeMD5(xmlmsg);

			    PrivateKey privatekey = KeyReader.PrivateKey(p12FileName, pfxPassword, 
			      keyAlias);
			    byte[] ddd = KeyReader.sign(privatekey, digs);
			    String mess = base64Encode(ddd);
			    xmlmsg = new String(base64Encode(xmlmsg.getBytes()));
			    String smsge = "xmlmess=" + xmlmsg + "&mess=" + mess;
			    String strkey = DESMessage.getKey();
			    res[0] = DESMessage.encString(smsge, strkey,codetype);
			    PublicKey publickey = KeyReader.Publickey(cerFileName2);
			    res[1] = 
			      KeyReader.encryptDecrypt(publickey, 
			      1, strkey);
			    return res;
			  }
	
    /**
     * 
	 * @param pri_cerfilepath 私钥路径
	 * @param pri_cerfilename 私钥名称
	 * @param pri_cerfilepwd   私钥密码
	 * @param pub_cerfilepath   公钥路径
     * @param fmsg  密文1
     * @param gmsg  密文2
     * @return
     * @throws Exception
     */
	/*public static String Decrypt_Xml(String pri_cerfilepath,String pri_cerfilename,String pri_cerfilepwd,String pub_cerfilepath,String fmsg,String gmsg) throws Exception
	{
		String xmlmsg = "";
		String p12FileName = pri_cerfilepath;
		String pfxPassword = pri_cerfilepwd;
		String keyAlias = pri_cerfilename;
		String cerFileName2 = pub_cerfilepath;
		PrivateKey privatekey = KeyReader.PrivateKey(p12FileName, pfxPassword,
				keyAlias);
		String pristr = KeyReader.encryptDecrypt(privatekey,
				Cipher.DECRYPT_MODE, gmsg);
		if (pristr != null) {
			pristr = pristr.trim();
		}else{
			System.out.println("私钥解密错误");
		}

		String sbody = DESMessage.decString(fmsg, pristr);
		int xmlindex = sbody.indexOf("xmlmess=");
		int mmessindex = sbody.indexOf("&mess=");
		if (xmlindex >= 0 && mmessindex >= 0 && sbody.length()>10 ) {
			xmlmsg = sbody.substring(xmlindex, mmessindex);
			xmlmsg = xmlmsg.substring(8, xmlmsg.length());
			xmlmsg = new String(base64Decode(xmlmsg),"utf-8");
			String xmlstr = xmlmsg.replaceAll("[^x00-xff]*","");
			//xmlstr = new String(base64Encode(xmlstr.getBytes()));
			String mess = sbody.substring(mmessindex, sbody.length());
			mess = mess.substring(6, mess.length());
			PublicKey publickey = KeyReader.Publickey(cerFileName2);
			byte[] dd = MD5MessageDigest.computeMD5(xmlstr);
			byte[] vermsg = base64Decode(mess);
			boolean flage = KeyReader.verifySign(publickey, dd,vermsg);
			if (!flage) {
				xmlmsg = "";
			}
		}
		return xmlmsg;
	}*/
	 
	 public static String Decrypt_Xml(String pri_cerfilepath, String pri_cerfilename, String pri_cerfilepwd, String pub_cerfilepath, String fmsg, String gmsg,String codetype) throws Exception
	  {
	    String xmlmsg = "";
	    String p12FileName = pri_cerfilepath;
	    String pfxPassword = pri_cerfilepwd;
	    String keyAlias = pri_cerfilename;
	    String cerFileName2 = pub_cerfilepath;
	    PrivateKey privatekey = KeyReader.PrivateKey(p12FileName, pfxPassword, 
	      keyAlias);
	    String pristr = KeyReader.encryptDecrypt(privatekey, 
	      2, gmsg);
	    if (pristr != null) {
	      pristr = pristr.trim();
	    }

	    String sbody = DESMessage.decString(fmsg, pristr, codetype);
	    int xmlindex = sbody.indexOf("xmlmess=");
	    int mmessindex = sbody.indexOf("&mess=");
	    if ((xmlindex >= 0) && (mmessindex >= 0) && (sbody.length() > 10)) {
	      xmlmsg = sbody.substring(xmlindex, mmessindex);
	      xmlmsg = xmlmsg.substring(8, xmlmsg.length());
	      xmlmsg = new String(base64Decode(xmlmsg));
	      String mess = sbody.substring(mmessindex, sbody.length());
	      mess = mess.substring(6, mess.length());
	      PublicKey publickey = KeyReader.Publickey(cerFileName2);
	      byte[] dd = MD5MessageDigest.computeMD5(xmlmsg);
	      byte[] vermsg = base64Decode(mess);
	      boolean flage = KeyReader.verifySign(publickey, dd, vermsg);
	      if (!flage) {
	       // xmlmsg = "";
	      }
	    }
	    return xmlmsg;
	  }
	
	   /**
     * base64编码
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
     * base64解码
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
    
    
	public static void main(String[] args) throws Exception {
		String str = "<orderreq><interfaceName></interfaceName><interfaceVersion></interfaceVersion><id>99825</id><orderid>99835</orderid><thirdcode>feetran</thirdcode><notifytype>00</notifytype><orderdate>20120214153552</orderdate><name>111</name><amt>10</amt><mercacc>8888889</mercacc><trantype>0</trantype><personid>99997</personid><personidtype>2</personidtype><acctype></acctype><goodsid></goodsid><goodsnum></goodsnum><goodsname>测试程序</goodsname><carriageamt>0</carriageamt><mercurl></mercurl><merhint></merhint><remark1></remark1><remark2></remark2><passwd>111111</passwd></orderreq>";

		String[] res =  DigitalCer.Encrypt_Xml("c:/key/web-client.p12","web-client", "openssl","c:/key/web-client11.cer",str,"utf-8");

		//String result = DigitalCer.Decrypt_Xml("c:/key/web-client.p12","web-client", "openssl",
		//		 "c:/key/web-client11.cer", res[0],res[1]);
		//
		System.out.println(res[0]);
		res[0]="OUq5dObFhfUeduTlldnLmkZqH5MF6d/Gyx6Z6uXhE9MBsmAuLyA2uSeKDEf1xNdEhUBafkGRtkzkttPFqRxyAsnRpz9shHF+W29aANs2G9nj4R6d2Lb9weHzAhsQy+DlqH0KlpC0YFIlFSeKm3BImi8BmD48sRmhKbQsH7dmCebZQYn4PQz+tzrVA7Tc/Y8Zozaffg0+0arpvQYn+bKbBfJH3xLiek3E7wabkMK8CK5zi9Ahs1UBP95dBN/9wWVNzPVwL3a3N3Y2PL0lnWCW9LRBZ+h4wpK4Nad0qVhJByt7pfqASgIbEk4tQdW4ntaQohnAvPddi3IbJGeWcPU/IFBpFeZnMyc66iKmXTSV+t5tTn9XU9OkI7Tj1Wc1tcCwn1Bv0968hbibYDSbC8/GwAcaM3SM2noYWBdMk0ds6pRmhANN7xRxwoRVrA5qnlKNjdPretXfNp9Xqul70FLlbWFfOZTNV0B4QtkFhyLas8KHXtX9UbIV/obPSN//FDz4jow2Uhp21lP4Vjz4ZKZmTXAnnGjkLjiXsHR6Rmo1CZMDWP/iAvii6+bcxdSFCVVi4xApEXBtXhC6smAuUlkE+uHeKjyJVcUyloTqzX05yWkAppjir9VN0RR/Hg+XIgK/a06OmGeBUSqPpawImxGyf9/7AWwdmLJE2O6QSdRxXSS/OBjJZuWBqW/kOCYWEc+SKJAi4inYOj1v5DgmFhHPklueDE6BMbvZuU8EH1q/dpK+S8o0KG/iVyvpdcxBKxNNxnPBkFMlmU9T7DrxemhhxNtycCxFXVK5JkrSwHs3bFwCbmGSW+iDLj0qk6mcHQSMc3MSp+hYeeBMVectR72XrQFWRRfAkaP3Ms8uElQxxFFKUm6CJxCaFlBBDhnA/EtnTNh6p09FVEOiwjriNKWwDNCUdMQvdj6C5LvGQT1aB2KN7ePyGf8u8ZrBPJ7+BdlKYCdZLb7UWSsGGNF5htHeo4dokwnhNld/K6CijyWgYuktIKT1vlyPHiE1E8o/G1xg4Lzg/zLEMRRnc2RGzyY8J09wE3484YXTFTbXha2c1oUvW6C5GAQX2FApTQI52iAj2K3u//t4XscayMTB2H9aujYd9gn9k3VQUyDl6+h0NoKrGvHlnYN+Ryh6DhGKabivq2kjFDHbBA0=";
		res[1]="ROe0iZhlkOs4OCZKk9xz3sWuYgh1HGNaz0GVyocDqqUALevXOdx6EoDofArHLJXo0TlxlgPR5rK12mCc6ixGaL6Gv+fTKOpZlrtwL/ZFJzaD2mV+fUfF16M+hRfm5c7EN02UI2N8JdfdTLGfO64CxhfRsGDlk/VnqrC88AO29vU=";
		System.out.println(res[1]);
		String result = DigitalCer.Decrypt_Xml("c:/key/web-client.p12","web-client", "openssl",
			 "c:/key/web-client11.cer",res[0],res[1],"utf-8");
		System.out.println(result);

		
	}
}
 