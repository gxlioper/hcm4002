package com.hjw.util;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class MyDataConverter implements Converter {

	@Override
	public Object convert(Class type, Object value) {
		String dateStr;
		Date date = null;
		try {
			dateStr = (String)value;
			date = Timestamp.valueOf(dateStr);
		} catch (Exception e) {
			date = (Timestamp)value;
		}
		return date;
	}
}
