package com.hjw.crm.DTO;


public class CrmPlanTacticsDTO {
	private long id;
	
	private String tactics_num;//策略编码
	
	private String notices;//策略描述  
	
	private int tactics_type;//策略类型  表示：1 慢病、2复查、3危机值、4vip回访，5特殊回访
	
	private String rmark;//对应策略说明
	
	private String creater;
	
	private String create_date;
	
 	private String updater;
	    
    private String update_date;
    
    private String tactics_type_s;

	public long getId() {
		return id;
	}

	public String getTactics_num() {
		return tactics_num;
	}

	public String getNotices() {
		return notices;
	}

	public int getTactics_type() {
		return tactics_type;
	}

	public String getRmark() {
		return rmark;
	}

	public String getCreater() {
		return creater;
	}

	public String getCreate_date() {
		return create_date;
	}

	public String getUpdater() {
		return updater;
	}

	public String getUpdate_date() {
		return update_date;
	}

	

	public void setId(long id) {
		this.id = id;
	}

	public void setTactics_num(String tactics_num) {
		this.tactics_num = tactics_num;
	}

	public void setNotices(String notices) {
		this.notices = notices;
	}

	public void setTactics_type(int tactics_type) {
		this.tactics_type = tactics_type;  //策略类型  表示：1 慢病、2复查、3危机值、4vip回访，5特殊回访
		if(tactics_type == 1){
			this.setTactics_type_s("慢病");
		}else if(tactics_type == 2){
			this.setTactics_type_s("复查");
		}else if(tactics_type == 3){
			this.setTactics_type_s("危机值");
		}else if(tactics_type == 4){
			this.setTactics_type_s("vip回访");
		}else if(tactics_type == 5){
			this.setTactics_type_s("特殊回访");
		}
	}

	public void setRmark(String rmark) {
		this.rmark = rmark;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getTactics_type_s() {
		return tactics_type_s;
	}

	public void setTactics_type_s(String tactics_type_s) {
		this.tactics_type_s = tactics_type_s;
	}


}
