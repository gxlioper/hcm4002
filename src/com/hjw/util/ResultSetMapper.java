package com.hjw.util;
 
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
 
public class ResultSetMapper {
	public static <T> List<T> mapRersultSetToObject(Connection connection,String sql, Class<?> outputClass) {
		ConvertUtils.register(new MyDataConverter(), Date.class);
		List<T> outputList = new ArrayList<T>();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(sql);
			try {
				// make sure resultset is not null
				if (rs != null) {
					// check if outputClass has 'Entity' annotation
					if (outputClass.isAnnotationPresent(Entity.class)) {
						// get the resultset metadata
						ResultSetMetaData rsmd = rs.getMetaData();
						// get all the attributes of outputClass
						Field[] fields = outputClass.getDeclaredFields();
						while (rs.next()) {
							T bean = (T) outputClass.newInstance();
							for (int _iterator = 0; _iterator < rsmd.getColumnCount(); _iterator++) {
								// getting the SQL column name
								String columnName = rsmd.getColumnName(_iterator + 1);
								// reading the value of the SQL column
								Object columnValue = rs.getObject(_iterator + 1);
								// iterating over outputClass attributes to check if
								// any attribute has 'Column' annotation with
								// matching 'name' value
								for (Field field : fields) {
									if (field.isAnnotationPresent(Column.class)) {
										Column column = field.getAnnotation(Column.class);
										if (column.name().equalsIgnoreCase(columnName) && columnValue != null) {
											BeanUtils.setProperty(bean, field.getName(), columnValue);
											break;
										}
									}else if(field.isAnnotationPresent(Id.class)){
										if(field.getName().equalsIgnoreCase(columnName)){
											BeanUtils.setProperty(bean, field.getName(), columnValue);
											break;
										}
									}
								}
							}
							outputList.add(bean);
						}
					} else {
						// throw some error
					}
				} else {
					//return null;
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null){
					rs.close();
				}
				if(st != null){
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return outputList;
	}
}