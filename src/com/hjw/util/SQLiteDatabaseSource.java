package com.hjw.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabaseSource {

	/**
	 * @Method: getConnection
	 * @Description: 从数据源中获取数据库连接
	 * @Anthor:
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection(String sqlite_path) throws Exception {
		Connection connect=null;
		Class.forName("org.sqlite.JDBC");
		connect = DriverManager.getConnection("jdbc:sqlite:"+sqlite_path);
		return connect;
	}

	public static void close(Connection connect) throws Exception {
		if (connect != null) {
			try {
				// 将Connection连接对象还给数据库连接池
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Connection connect,Statement st) {	
		if (st != null) {
			try {
				// 关闭负责执行SQL命令的Statement对象
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (connect != null) {
			try {
				// 将Connection连接对象还给数据库连接池
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public static void close(Connection connect,Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				// 关闭存储查询结果的ResultSet对象
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				// 关闭负责执行SQL命令的Statement对象
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (connect != null) {
			try {
				// 将Connection连接对象还给数据库连接池
				connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
