package com.hjw.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ExcelUtil {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getExcelData(Class c, Map map, List list){
		List dataList = new ArrayList();
		Method method = null;
		System.err.println("map=="+map);
		try{
//				List colData = new ArrayList();
//				Set keys = map.keySet();
//				String key = "";
//				Object obj = null;
//				Iterator it = keys.iterator();
//				while(it.hasNext()){
//					key = it.next().toString();
//					colData.add(map.get(key));
//				}
//				dataList.add(colData);
//				
			Map  keyList = new HashMap();//用来存放key的集合
			Map  valueList=new HashMap();//用来存放value的集合
			Set mapEntry = map.entrySet();
			Iterator it = mapEntry.iterator();
			Map keyMap=new HashMap();
			Map valueMap=new HashMap();
			while(it.hasNext()){
				Entry entry=(Entry) it.next();
				keyMap=(Map) entry.getKey();
				 valueMap=(Map) entry.getValue();
				keyList.putAll(keyMap);
				valueList.putAll(valueMap);
				
			}
			System.err.println("keyList=="+keyList);
			System.err.println("valueList=="+valueList);
			Set valueSet=valueList.keySet();
			Iterator iterator=valueSet.iterator();
			List coList=new ArrayList();
			while(iterator.hasNext()){
				coList.add(valueList.get(iterator.next()));
			}
			System.out.println("colList:"+coList);
			dataList.add(coList);
//				for(int i=0; i<list.size(); i++){
//					obj = list.get(i);
//					colData = new ArrayList();
//					it = keys.iterator();
//					while(it.hasNext()){
//						key = it.next().toString();
//						method = c.getDeclaredMethod(key);
//						method.setAccessible(true);
//					 	Object val = method.invoke(list.get(i));
//						colData.add(val);
//					}
//					dataList.add(colData);
//				}
			Set keySet=keyList.keySet();
			Iterator keyIterator=keySet.iterator();
			List key=new ArrayList();
			while(keyIterator.hasNext()){
				key.add(keyList.get(keyIterator.next()));
			}
			System.err.println("key=="+key);
			Object object=null;
			List colDataList=null;//用来存放从数据库中读取的数据
			for(int i=0;i<list.size();i++){
				object=list.get(i);
				System.err.println("object=="+object);
				colDataList=new ArrayList();
				for(int j=0;j<key.size();j++){
					method = c.getDeclaredMethod(key.get(j).toString());
					method.setAccessible(true);
					Object value=method.invoke(object);
					if(value==null){//如果数据库中该值为null，则默认为0
						value="0.00";
					}
					colDataList.add(value);
				}
				System.err.println("colDataList:"+colDataList);
				dataList.add(colDataList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//p(dataList);
		return dataList;
	}
	
	@SuppressWarnings("rawtypes")
	public static void p(List data){
	for(Object obj : data){
		for(Object o : (List)obj){
			System.out.print(o+"\t");
		}
		System.out.println("\n");
	}
	}

}
