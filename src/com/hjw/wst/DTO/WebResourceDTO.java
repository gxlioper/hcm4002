package com.hjw.wst.DTO;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  资源管理
     * @author: zr     
     * @date:   2016年12月15日 下午4:29:44   
     * @version V2.0.0.0
 */
public class WebResourceDTO  implements java.io.Serializable {
	private  String code;
	private  String  name;
	private  String  type;
	private  String  data_type;
	private  String  notice;
	private  String  example;
	private  String  examplenote;
	private  String  remark;
	private  String  remark1;
	
	private  long   id;
    private  String   res_code;
    private  String   res_type;
    private  String   datavalue;
    private  String   create_time;
    private  long   creater;
    private  long   updater;
    private  String   update_time;
    private  String   userorroleid;
    
    
    private String name_s;
    
    private String pd;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getExamplenote() {
		return examplenote;
	}

	public void setExamplenote(String examplenote) {
		this.examplenote = examplenote;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_type() {
		return res_type;
	}

	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}

	public String getDatavalue() {
		return datavalue;
	}

	public void setDatavalue(String datavalue) {
		this.datavalue = datavalue;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
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

	public String getUserorroleid() {
		return userorroleid;
	}

	public void setUserorroleid(String userorroleid) {
		this.userorroleid = userorroleid;
	}

	public String getName_s() {
		return name_s;
	}

	public void setName_s(String name_s) {
		this.name_s = name_s;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}
	
    
}
