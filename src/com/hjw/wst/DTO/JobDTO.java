package com.hjw.wst.DTO;


public class JobDTO  implements Cloneable,java.io.Serializable{
    private static final long serialVersionUID = -3565567339406922273L;
	private String id;
	private String name;
	private String remark;
	private String data_code_children;
	
	
	public String getData_code_children() {
		return data_code_children;
	}
	public void setData_code_children(String data_code_children) {
		this.data_code_children = data_code_children;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
