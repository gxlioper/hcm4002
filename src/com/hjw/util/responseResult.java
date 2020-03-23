package com.hjw.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;

import com.opensymphony.xwork2.ActionContext;


/** 
 * <b>Application name:</b>新中新第一事业部<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2013 新中新第一事业部。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2013-4-15<br>
 * @author 路志友
 * @version $Revision$
 */
public final class responseResult {



    static
    
    {
         context=ActionContext.getContext(); 
        
        
    }
    
    private static Map<String, Object> resultMap = new HashMap<String, Object>();
   public  static  ActionContext context ;
   public static final String SUCCESS="success";
   //返回request 对象
      @SuppressWarnings("rawtypes")
	public static  Map result()
      {
         
         context=ActionContext.getContext(); 
         return (Map)context.get("request");
      }
    
      @SuppressWarnings("rawtypes")
	public Map SessionMap()
      {
          if(context==null)
          {
              context=ActionContext.getContext(); 
          }
          if(context.getSession()!=null)
           return  context.getSession();
          else
          {
              
              return null;
          }
       
      }
   //以map 的形式返回json
   @SuppressWarnings({ "rawtypes", "unchecked" })
public static void Result(String result,Map map) {
       // TODO Auto-generated method stub

        context =ActionContext.getContext(); 
       
       HttpServletResponse response = (HttpServletResponse)context.get(StrutsStatics.HTTP_RESPONSE);
       response.setContentType("text/html; charset=utf-8");
       List list=new ArrayList();
       list.add(map);
       HashMap resutlMap=new HashMap();
       resutlMap.put(result, list);
       Writer out;
       try {
           out = response.getWriter();
          
           out.write( JSONObject.fromObject(resutlMap).toString());
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
     
       
   }
   //以列表的形式返回json
   @SuppressWarnings({ "rawtypes", "unchecked" })
public  void Result(String result,List list) {
       // TODO Auto-generated method stub
        context =ActionContext.getContext(); 
        HttpServletResponse response = (HttpServletResponse)context.get(StrutsStatics.HTTP_RESPONSE);
        response.setContentType("text/html; charset=utf-8");
        HashMap resutlMap=new HashMap();
        resutlMap.put(result, list);
       Writer out;
       try {
           out = response.getWriter();
           System.out.println(JSONObject.fromObject(resutlMap).toString());
           out.write( JSONObject.fromObject(resutlMap).toString());
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }

       
   }
   /**
    * 输出字符串
    * 
    * @param str
    *            字符串
    * @param contentType
    *            类型
    */
   public static void writeString(String str, String contentType) {
       HttpServletResponse response = (HttpServletResponse) getResponse();
       if (contentType != null && contentType.trim().length() > 0) {
           response.setContentType(contentType);
       }
       //response.setCharacterEncoding("UTF-8");
       response.setHeader("Charset", "UTF-8");
       response.setHeader("Cache-Control", "no-cache");
       PrintWriter out = null;
       try {
           out = response.getWriter();
           out.write(str);
           out.flush();
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (out != null)
               out.close();
       }
   }
   /**
    * 获取响应对象
    * 
    * @return 响应对象
    */
   public static ServletResponse getResponse() {
       return ServletActionContext.getResponse();
   }
   /**
    * 输出文本内容
    * 
    * @param text
    *            文本内容
    */
   public static void writeHtmlText(String text) {
       writeString(text, "text/html");
   }
   public static  void RequestResult(String result,Object object) {
       // TODO Auto-generated method stub
         context =ActionContext.getContext(); 
         context.put(result, object);


       
   }
   //以列表的形式返回json
   public static void Result(String result) {
       
       // TODO Auto-generated method stub
         context =ActionContext.getContext(); 
         HttpServletResponse response = (HttpServletResponse)context.get(StrutsStatics.HTTP_RESPONSE);
           response.setContentType("text/html; charset=utf-8");
           Writer out;
           try {
               out = response.getWriter();
               out.write( result);
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
   }

   public void ResultXml(String result) {
       
       // TODO Auto-generated method stub
         context =ActionContext.getContext(); 
         HttpServletResponse response = (HttpServletResponse)context.get(StrutsStatics.HTTP_RESPONSE);
           response.setContentType("text/xml;charset=utf-8");
           response.setHeader("Cache-Control", "no-cache");    
           Writer out;
           try {
               out = response.getWriter();
               out.write("<?xml version='1.0' encoding='UTF-8'?>"+ result);
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
   }
   public static int computePagingStartIndex(int pageNo, int pageSize) {
       if(pageNo<1)
       {
           pageNo=1;
       }
       return (pageNo - 1) * pageSize;
   }
   /**
    * 通用分页查询执行方法
    * 
    * @param query
    *            查询对象
    * @param currentPage
    *            当前页数
    * @param pageSize
    *            最大记录数
    * @param where
    * @return
    */
   @SuppressWarnings("unchecked")
   protected static <T> List<T> commonQuery(Query query, int startIndex,
           int pageSize, Map<String, Object> where) {
       responseResult.bindQueryWhere(query, where);
       query.setFirstResult(startIndex);
       query.setMaxResults(pageSize);
       return query.list();
   }
   /**
    * 绑定查询条件
    * 
    * @param <T>
    *            类型
    * @param query
    *            查询对象
    * @param where
    *            条件
    * @return 查询对象
    */
   protected static <T extends Query> T bindQueryWhere(T query,
           Map<String, Object> where) {
       if (where != null && where.size() > 0) {
           query.setProperties(where);
       }
       return query;
   }
	/**
	 * 设置result参数
	 * @throws Exception
	 * author:yujia
	 */
  public static void setResultParam(String paramName, Object param)
  {
		try
		{
			resultMap.put(paramName, param);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
  }
	/**
	 * 设置result参数success
	 * @throws Exception
	 * author:yujia
	 */
  public static void setResultSuccess(boolean flag) 
  {
		try
		{
			resultMap.put(SUCCESS, flag);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
  /**
   * 增加json输出
   * @param name
   * @param obj
   * author:yujia
   */
	public static void outResultJson()
	{
		try
		{
			HttpServletResponse response=(HttpServletResponse)context.get(StrutsStatics.HTTP_RESPONSE);
			response.setContentType("text/json;charset=UTF-8");
			// 将resultMap对象转化为json字符串对象,使用的是JackSon中的ObjectMapper
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(resultMap);
			response.getWriter().print(jsonString);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
 
}
