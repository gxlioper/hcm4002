package com.hjw.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;

public class ActionUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void validExpRes(Map resMap, Map map, ActionSupport as){
//		Set<String> keys = resMap.keySet();
//		String getMethod = "";
//		for (String key : keys) {
//			getMethod = "get"+key.substring(0, 1).toUpperCase()+key.substring(1);
//			try{
//				map.put(getMethod, as.getText(resMap.get(key).toString()));
//			}catch(Exception ex){
//				System.out.println(ex);
//			}
//		}
		//得到resmap中sortmap的值 
		Map<String, String> map2=(Map<String, String>) resMap.get("sortmap");
		Set<String> keySet=map2.keySet();
		Iterator<String> iterator=keySet.iterator();
		List<Integer> iList=new ArrayList<Integer>();
		while(iterator.hasNext()){
			Object value=iterator.next();
			if(value!=null&&value instanceof Integer){
				iList.add((Integer) value);
			}
			
		}
		String value="";
		String getMethod="";
		if(iList.size()>0){
			for(int i=0;i<iList.size();i++){
				Map map3=new HashMap();//用来确定导出的excel文件表头的顺序 
				Map map4=new HashMap();//用来确定导出的excel数据的顺序
				getMethod="get"+map2.get(iList.get(i)).substring(0, 1).toUpperCase()+map2.get(iList.get(i)).substring(1);
				value=as.getText(resMap.get(map2.get(iList.get(i))).toString());
				//根据数据库中字段存放的位置来确定导出的字段位置
				map3.put(iList.get(i), value);
				map4.put(iList.get(i), getMethod);
				map.put(map4, map3);
				
			}
			
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> sortCol(Set resnotes, Map sortmap){
		List list = new ArrayList();
		
		for (Object notes : resnotes) {
			list.add(sortmap.get(notes));
		}
		
		Collections.sort(list);
		
		for(int i=0; i<list.size(); i++){
			list.set(i, sortmap.get(list.get(i)));
		}
		
		return list;
	}
}
