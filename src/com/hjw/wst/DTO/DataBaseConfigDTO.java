package com.hjw.wst.DTO;

public class DataBaseConfigDTO {

	private String database_url;// IP地址
	private String database_port;// 端口号
	private String database_uame;// 数据库名称
	private String username; //  登录名称
	private String password; //  登录密码
	private long type; //配置类型 0表示数据库配置、1表示ftp配置
	private String types; 
	
	public String getDatabase_url() {
		return database_url;
	}
	public void setDatabase_url(String database_url) {
		this.database_url = database_url;
	}
	public String getDatabase_port() {
		return database_port;
	}
	public void setDatabase_port(String database_port) {
		this.database_port = database_port;
	}
	public String getDatabase_uame() {
		return database_uame;
	}
	public void setDatabase_uame(String database_uame) {
		this.database_uame = database_uame;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
		if(this.type == 0){
			this.types = "数据库配置";
		}else if(this.type == 1){
			this.types = "FTP配置";
		}
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
}
