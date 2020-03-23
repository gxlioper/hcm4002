package com.hjw.util;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
 
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.hjw.wst.domain.ExamInfo;

public class ProduceSql {
    //String insertSql = getInsertSql("user", User.class, user);
 
    /**
     * 生成插入语句
     * @param tablename 表明
     * @param t 有数据的实体
     * @param <T> 数据实体类型 如 User
     */
    public static  <T> String getInsertSql(String tablename, T t,Boolean is_create_id) throws IllegalArgumentException  {
        //insert into table_name (column_name1,column_name2, ...) values (value1,value2, ...)
        boolean flag = false;
        String sql = "";
        Field[] fields = ReflectUtil.getFieldsDirectly(t.getClass(), false);
        StringBuffer topHalf = new StringBuffer();
        if(t.getClass().isAnnotationPresent(Table.class)){
    		tablename = t.getClass().getAnnotation(Table.class).name();
    	}
        if(!is_create_id){
        	topHalf.append("insert into ["+tablename+"] (");
        }else{
        	topHalf.append("insert into "+tablename+" (");
        }
        StringBuffer afterAalf = new StringBuffer("values (");
        for (Field field : fields) {
        	if("serialVersionUID".equals(field.getName())){
        		continue;
        	}
            if ("ID".equals(field.getName()) || "id".equals(field.getName())){
                if(!is_create_id){
                	continue;   //id 自动生成无需手动插入
                }
            }
            topHalf.append(field.getName() + ",");
            if (ReflectUtil.getFieldValue(t, field.getName()) instanceof String) {
            	if(!is_create_id){
            		afterAalf.append("N'" + ReflectUtil.getFieldValue(t, field.getName()).toString().replaceAll("'", "''") + "',");
	                flag = true;
            	}else{
            		afterAalf.append("'" + ReflectUtil.getFieldValue(t, field.getName()).toString().replaceAll("'", "''") + "',");
	                flag = true;
            	}
            }else if(ReflectUtil.getFieldValue(t, field.getName()) instanceof Date){
            	afterAalf.append("'" + ReflectUtil.getFieldValue(t, field.getName()) + "',");
                flag = true;
            }else {
                afterAalf.append(ReflectUtil.getFieldValue(t, field.getName()) + ",");
                flag = true;
            }
        }
        if (!flag) {
            throw new IllegalArgumentException(t.getClass() +  "NullException.\nThere is no attribute that is not empty.You must provide an object with at least one attribute.");
        }
        topHalf = new StringBuffer(StrUtil.removeSuffix(topHalf.toString(), ","));
        afterAalf = new StringBuffer(StrUtil.removeSuffix(afterAalf.toString(), ","));
        topHalf.append(") ");
        afterAalf.append(") ");
        sql = topHalf.toString() + afterAalf.toString();
        return sql;
    }
 
    /**
     * 生成更新语句
     * 必须含有id
     * 数据实体中 null 与 空字段不参与更新
     * @param tablename 数据库中的表明
     * @param t 有数据的实体
     * @param <T> 数据实体类型,如 User
     */
    public static  <T> String getUpdateSql(String tablename, T t) throws IllegalArgumentException {
        //UPDATE table_name SET column_name1 = value1, column_name2 = value2, ... where ID=xxx
        //or
        //UPDATE table_name SET column_name1 = value1, column_name2 = value2, ... where id=xxx
        boolean flag = false;
        String sql = "";
        String id = ""; //保存id列名：ID or id
        Field[] fields = ReflectUtil.getFieldsDirectly(t.getClass(), false);
        sql = "update ["+tablename+"] set ";
        for (Field field : fields) {
            StringBuffer tmp = new StringBuffer();
            if("serialVersionUID".equals(field.getName())){
        		continue;
        	}
            if ("ID".equals(field.getName()) || "id".equals(field.getName())){
                id = field.getName();
                continue;//更新的时候无需set id=xxx
            }
            if (ReflectUtil.getFieldValue(t, field.getName()) != null) {
                tmp.append( field.getName() + "=");
                if (ReflectUtil.getFieldValue(t, field.getName()) instanceof String) {
                    tmp.append( "N'" + ReflectUtil.getFieldValue(t, field.getName()).toString().replaceAll("'", "''") + "',");
                    flag = true;
                }else if(ReflectUtil.getFieldValue(t, field.getName()) instanceof Date){
                	tmp.append( "'" + ReflectUtil.getFieldValue(t, field.getName()) + "',");
                    flag = true;
                } else {
                    tmp.append(ReflectUtil.getFieldValue(t, field.getName()) + ",");
                    flag = true;
                }
                sql += tmp;
            }
        }
        if (!flag) {
            throw new IllegalArgumentException(t.getClass() +  "NullException.\nThere is no attribute that is not empty except for ID.You must provide an object with at least one attribute exclude ID.");
        }
        sql = StrUtil.removeSuffix(sql, ",") + " where " + id + "='" + ReflectUtil.getFieldValue(t, id)+"'";
        return sql;
    }
 
    /**
     * 生成删除语句
     * 根据 user 中第一个不为空的字段删除,应该尽量使用 id,提供至少一个非空属性
     * @param tablename 表明
     * @param t 有数据的实体
     * @param <T> 数据实体类型 如 User
     */
    public static  <T> String getDeleteSql(String tablename, T t) throws IllegalArgumentException {
        //delete from table_name where column_name = value
        return getSelectOrDeleteSql(tablename, t, "delete");
    }
 
    /**
     * 生成查询语句
     * 根据 user 中第一个不为空的字段查询
     * @param tablename 表名
     * @param t 有数据的实体
     * @param <T> 数据实体类型 如 User
     */
    public static  <T> String getSelectSql(String tablename, T t) throws IllegalArgumentException {
        //delete from table_name where column_name = value
        return getSelectOrDeleteSql(tablename, t, "select *");
    }
 
    /**
     * 根据 operation 生成一个如：operation from table_name where column_name = value 的sql语句
     * @param tablename
     * @param t
     * @param operation "select *"  or "delete"
     * @param <T>
     * @return
     * @throws IllegalArgumentException
     */
    private static  <T> String getSelectOrDeleteSql(String tablename, T t, String operation) throws IllegalArgumentException {
        //operation from table_name where column_name = value
        boolean flag = false;
        String sql = "";
        Field[] fields = ReflectUtil.getFieldsDirectly(t.getClass(), false);
        StringBuffer topHalf = new StringBuffer(operation + " from " + tablename + " where ");
        for (Field field : fields) {
            if ("ID".equals(field.getName()) || "id".equals(field.getName())) {
                if (ReflectUtil.getFieldValue(t, field.getName()) != null && (int)ReflectUtil.getFieldValue(t, field.getName()) != 0) {
                    //id 不为空
                    topHalf.append(field.getName() + " = " + ReflectUtil.getFieldValue(t, field.getName()));
                    flag = true;
                    break;
                }
            }
            else {
                if (ReflectUtil.getFieldValue(t, field.getName()) != null && (String)ReflectUtil.getFieldValue(t, field.getName()) != "") {
                    topHalf.append(field.getName() + " = '" + ReflectUtil.getFieldValue(t, field.getName()) + "'");
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            throw new IllegalArgumentException(t.getClass() +  "NullException.\nThere is no attribute that is not empty.You must provide an object with at least one attribute.");
        }
        sql = topHalf.toString();
        return sql;
    }
    
    public static <T> String getCreateSql(String tablename,Class<?> t) throws IllegalArgumentException  {
    	String sql = "";
    	Field[] fields = t.getDeclaredFields();
    	if(t.isAnnotationPresent(Table.class)){
    		tablename = t.getAnnotation(Table.class).name();
    	}
    	StringBuffer topHalf = new StringBuffer("create table "+tablename+" (");
    	for (Field field : fields) {
    		if("serialVersionUID".equals(field.getName())){
	        	continue;
	        }
    		if (field.isAnnotationPresent(Column.class)) {
    			topHalf.append(field.getAnnotation(Column.class).name());
    		}else{
    			topHalf.append(field.getName());
    		}
    		if (field.getType().getSimpleName().equals("Integer")) {
    			topHalf.append("  int ,\n");
    		}else if(field.getType().getSimpleName().equals("Long")){
    			topHalf.append("  int ,\n");
    		}else if(field.getType().getSimpleName().equals("Double")){
    			topHalf.append("  decimal(19,2) ,\n"); 
    		}else if(field.getType().getSimpleName().equals("String")){
    			topHalf.append("  varchar(500) ,\n");
    		}else if(field.getType().getSimpleName().equals("Date")){
    			topHalf.append("  datetime ,\n");
    		}else {
    			topHalf.append("  int ,\n");
    		}
    	}
    	topHalf = new StringBuffer(StrUtil.removeSuffix(topHalf.toString(), ",\n"));
    	topHalf.append(") ");
    	sql = topHalf.toString();
    	return sql;
    }
    
    public static void main(String[] args) {
		String sql = ProduceSql.getCreateSql("exam_info", ExamInfo.class);
		System.out.println(sql);
	}
}
 
