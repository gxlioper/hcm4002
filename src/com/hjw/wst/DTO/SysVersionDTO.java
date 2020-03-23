package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

public class SysVersionDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		private String version="";
		private String createtime="";
        private List<SysVersionListDTO> versionlist=new ArrayList<SysVersionListDTO>();
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
		public List<SysVersionListDTO> getVersionlist() {
			return versionlist;
		}
		public void setVersionlist(List<SysVersionListDTO> versionlist) {
			this.versionlist = versionlist;
		}
		
	}