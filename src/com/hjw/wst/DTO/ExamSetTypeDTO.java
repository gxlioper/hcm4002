package com.hjw.wst.DTO;
/**
 * 套餐类别维护功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.crm.DTO   
     * @Description:  
     * @author: zr     
     * @date:   2017年10月27日 上午10:33:43   
     * @version V2.0.0.0
 */
public class ExamSetTypeDTO {
	public long  id;
	public String set_type_name;
	public String is_Active;
	public long set_class;
	public long creater;
	public String create_time;
	public long updater;
	public String update_time;
	public String set_class_s;
	
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSet_type_name() {
		return set_type_name;
	}
	public void setSet_type_name(String set_type_name) {
		this.set_type_name = set_type_name;
	}
	public String getIs_Active() {
		return is_Active;
	}
	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}
	public long getSet_class() {
		return set_class;
	}
	public void setSet_class(long set_class) {
		if(set_class==1){
			this.setSet_class_s("常规体检");
		} else if(set_class==2){
			this.setSet_class_s("微信体检");
		} else{
			this.setSet_class_s("");
		}
		this.set_class = set_class;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getSet_class_s() {
		return set_class_s;
	}
	public void setSet_class_s(String set_class_s) {
		this.set_class_s = set_class_s;
	}
}
