package com.hjw.zyb.DTO;

import java.util.ArrayList;
import java.util.List;

public class ZybEconomicclassDTO {
	
	
	//经济类型属性
		private String economicID;//树结构的value
		private String economicclass_name;//树结构显示的text
		private String parentID;//树结构节点自定义属性
		private Boolean checked;//树结构节点是否选中
		private String state/*="closed"*/;//树结构的节点状态 open,closed
		private String iconCls;//图标
		private String economicclass_code;//单位编码
		
		public String getEconomicID() {
			return economicID;
		}
		public void setEconomicID(String economicID) {
			this.economicID = economicID;
		}
		public String getEconomicclass_name() {
			return economicclass_name;
		}
		public void setEconomicclass_name(String economicclass_name) {
			this.economicclass_name = economicclass_name;
		}
		public String getParentID() {
			return parentID;
		}
		public void setParentID(String parentID) {
			this.parentID = parentID;
		}
		public Boolean getChecked() {
			return checked;
		}
		public void setChecked(Boolean checked) {
			this.checked = checked;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getIconCls() {
			return iconCls;
		}
		public void setIconCls(String iconCls) {
			this.iconCls = iconCls;
		}
		public String getEconomicclass_code() {
			return economicclass_code;
		}
		public void setEconomicclass_code(String economicclass_code) {
			this.economicclass_code = economicclass_code;
		}
		private List<ZybEconomicclassDTO> children=new ArrayList<ZybEconomicclassDTO>();

		public List<ZybEconomicclassDTO> getChildren() {
			return children;
		}
		public void setChildren(List<ZybEconomicclassDTO> children) {
			this.children = children;
		}
		
	

}
