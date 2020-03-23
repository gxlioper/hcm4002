package com.hjw.wst.DTO;

public class SampleExamDetailDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		private long id;
	    private long creater;
	    private String create_time;
	    private long updater;
	    private String update_time;
	    private long exam_info_id;
	    private long sample_id;
	    private String sample_barcode;
	    private String status;
	    private String status_y;
	    private String pic_path;
	    private String center_num;
	    private String approver;
	    private String approve_date;
	    private String demo_name;
	    private long item_id;
	    private String item_ids;
	    private String item_name;
	    private String item_code;
	    private long is_binding;
	    private String is_binding_y;
	    private String demo_color;
	    private String demo_indicator;
	    private long BarCode_Class;
	    private String is_application;
	    private String is_application_y;
	    private long print_num;
	    private long print_dep;
	    private String dep_type;
	    private String dep_name;
	    private String exam_num;
	    
	    public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}
	    
 
	    public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
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

		public long getExam_info_id() {
			return exam_info_id;
		}

		public void setExam_info_id(long exam_info_id) {
			this.exam_info_id = exam_info_id;
		}

		public long getSample_id() {
			return sample_id;
		}

		public void setSample_id(long sample_id) {
			this.sample_id = sample_id;
		}

		public String getSample_barcode() {
			return sample_barcode;
		}

		public void setSample_barcode(String sample_barcode) {
			this.sample_barcode = sample_barcode;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
			if("W".equals(status)){
				this.setStatus_y("未采样");
			}else if("Y".equals(status)){
				this.setStatus_y("已采样");
			}else if("E".equals(status)){
				this.setStatus_y("已检查");
			}else if("H".equals(status)){
				this.setStatus_y("已核收");
			}else if("N".equals(status)){
				this.setStatus_y("未采样");
			}
		}

		public String getPic_path() {
			return pic_path;
		}

		public void setPic_path(String pic_path) {
			this.pic_path = pic_path;
		}

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public String getApprover() {
			return approver;
		}

		public void setApprover(String approver) {
			this.approver = approver;
		}

		public String getApprove_date() {
			return approve_date;
		}

		public void setApprove_date(String approve_date) {
			this.approve_date = approve_date;
		}

		public String getStatus_y() {
			return status_y;
		}

		public void setStatus_y(String status_y) {
			this.status_y = status_y;
		}

		public String getItem_name() {
			return item_name;
		}

		public void setItem_name(String item_name) {
			this.item_name = item_name;
		}

		public String getDemo_name() {
			return demo_name;
		}

		public void setDemo_name(String demo_name) {
			this.demo_name = demo_name;
		}

		public String getItem_code() {
			return item_code;
		}

		public void setItem_code(String item_code) {
			this.item_code = item_code;
		}

		public long getIs_binding() {
			return is_binding;
		}

		public void setIs_binding(long is_binding) {
			this.is_binding = is_binding;
			if(is_binding == 0){
				this.setIs_binding_y("未绑管");
			}else if(is_binding == 1){
				this.setIs_binding_y("已绑管");
			}
		}

		public String getIs_binding_y() {
			return is_binding_y;
		}

		public void setIs_binding_y(String is_binding_y) {
			this.is_binding_y = is_binding_y;
		}

		public String getDemo_color() {
			return demo_color;
		}

		public void setDemo_color(String demo_color) {
			this.demo_color = demo_color;
		}

		public String getDemo_indicator() {
			return demo_indicator;
		}

		public void setDemo_indicator(String demo_indicator) {
			this.demo_indicator = demo_indicator;
		}

		public long getItem_id() {
			return item_id;
		}

		public void setItem_id(long item_id) {
			this.item_id = item_id;
		}

		public long getBarCode_Class() {
			return BarCode_Class;
		}

		public void setBarCode_Class(long barCode_Class) {
			BarCode_Class = barCode_Class;
		}

		public String getIs_application() {
			return is_application;
		}

		public void setIs_application(String is_application) {
			this.is_application = is_application;
			if("Y".equals(is_application)){
				this.is_application_y = "已申请";
			}else{
				this.is_application_y = "未申请";
			}
		}

		public String getIs_application_y() {
			return is_application_y;
		}

		public void setIs_application_y(String is_application_y) {
			this.is_application_y = is_application_y;
		}

		public String getItem_ids() {
			return item_ids;
		}

		public void setItem_ids(String item_ids) {
			this.item_ids = item_ids;
		}

		public long getPrint_num() {
			return print_num;
		}

		public void setPrint_num(long print_num) {
			this.print_num = print_num;
		}

		public long getPrint_dep() {
			return print_dep;
		}

		public void setPrint_dep(long print_dep) {
			this.print_dep = print_dep;
		}

		public String getDep_type() {
			return dep_type;
		}

		public void setDep_type(String dep_type) {
			this.dep_type = dep_type;
		}

		public String getDep_name() {
			return dep_name;
		}

		public void setDep_name(String dep_name) {
			this.dep_name = dep_name;
		}
	}