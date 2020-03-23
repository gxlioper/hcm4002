package com.hjw.wst.DTO;

public class SysVersionListDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		private String version="";
		private String createtime="";
        private String notices="";
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
		public String getNotices() {
			return notices;
		}
		public void setNotices(String notices) {
			this.notices = notices;
		}
        
        
	}