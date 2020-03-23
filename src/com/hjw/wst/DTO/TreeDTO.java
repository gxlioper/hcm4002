package com.hjw.wst.DTO;

import java.util.ArrayList;

import java.util.List;




public class TreeDTO {
	/**
	 * 树结构信息
	 */
	private String id;//树结构的value
	private String text;//树结构显示的text
	private String attributes;//树结构节点自定义属性
	private Boolean checked;//树结构节点是否选中
	private String state;//树结构的节点状态 open,closed
	private String depttype;//单位类型 0表示单位，1表示部门
	private String iconCls;//图标
	private String comnum;//单位编码
	private List<TreeDTO> children=new ArrayList<TreeDTO>();	
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getDepttype() {
		return depttype;
	}
	public void setDepttype(String depttype) {
		this.depttype = depttype;
	}
	public String getComnum() {
		return comnum;
	}
	public void setComnum(String comnum) {
		this.comnum = comnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
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
	
	
	public List<TreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<TreeDTO> children) {
		this.children = children;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((checked == null) ? 0 : checked.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((comnum == null) ? 0 : comnum.hashCode());
		result = prime * result + ((depttype == null) ? 0 : depttype.hashCode());
		result = prime * result + ((iconCls == null) ? 0 : iconCls.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeDTO other = (TreeDTO) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (checked == null) {
			if (other.checked != null)
				return false;
		} else if (!checked.equals(other.checked))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (comnum == null) {
			if (other.comnum != null)
				return false;
		} else if (!comnum.equals(other.comnum))
			return false;
		if (depttype == null) {
			if (other.depttype != null)
				return false;
		} else if (!depttype.equals(other.depttype))
			return false;
		if (iconCls == null) {
			if (other.iconCls != null)
				return false;
		} else if (!iconCls.equals(other.iconCls))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TreeDTO{" +
				"id='" + id + '\'' +
				", text='" + text + '\'' +
				", attributes='" + attributes + '\'' +
				", checked=" + checked +
				", state='" + state + '\'' +
				", depttype='" + depttype + '\'' +
				", iconCls='" + iconCls + '\'' +
				", comnum='" + comnum + '\'' +
				", children=" + children +
				'}';
	}
}
