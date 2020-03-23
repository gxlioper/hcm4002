package com.hjw.wst.model;

public class ThridInterfaceLogModel implements java.io.Serializable{
	
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private String id;
	private String req_no;
	private String exam_no;
	private String message_id;
	private String message_name;
	private String message_startDate;
	private String message_endDate;
	
	private String message_type;
	private String sender;
	private String receiver;
	private String message_request;
	private String message_response;
	private Integer flag;
	private String sys_request;
	private String sys_respones;
	private String xtgnb_id;
	private Integer message_inout; //调用方式
	
	private String til_id;
	private String ldate;
	private String lmessage;
	
	private String chkItem;
	
	private String config_key;
	private String config_url;
	private String config_method;
	private String config_remark;
	
	
	
	//lis实体
	//private int lis_id;
	private String exam_num;
	private String sample_barcode;
	private String lis_item_code;
	private String lis_item_name;
	private String report_item_code;
	private String report_item_name;
	private String exam_date;
	private String item_result;
	private String item_unit;
	private String lis_flag;
	private String ref;
	private int seq_code;;
	private String doctor;
	private String sh_doctor;
	private String note;
	private int read_flag;
	
	//pacs实体
	//id
	//req_no
	private String pacs_checkno;
	//exam_num
	//til_id
	private String item_name;
	private String pacs_item_code;
	private String study_type;
	private String study_body_part;
	private String clinic_diagnose;
	private String clinic_symptom;
	private String clinic_advice;
	private String is_abnormal;
	private String report_img_path;
	private String img_path;
	private int study_state;
	private String reg_doc;
	private String check_doc;
	private String check_date;
	private String report_doc;
	private String report_date;
	private String audit_doc;
	private String audit_date;
	//private String note;
	private int status;
	private String trans_date;
	private int is_tran_image;
	private int is_report_image;
	
	private String create_time;
	
	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getSample_barcode() {
		return sample_barcode;
	}
	public void setSample_barcode(String sample_barcode) {
		this.sample_barcode = sample_barcode;
	}
	public String getLis_item_code() {
		return lis_item_code;
	}
	public void setLis_item_code(String lis_item_code) {
		this.lis_item_code = lis_item_code;
	}
	public String getLis_item_name() {
		return lis_item_name;
	}
	public void setLis_item_name(String lis_item_name) {
		this.lis_item_name = lis_item_name;
	}
	public String getReport_item_code() {
		return report_item_code;
	}
	public void setReport_item_code(String report_item_code) {
		this.report_item_code = report_item_code;
	}
	public String getReport_item_name() {
		return report_item_name;
	}
	public void setReport_item_name(String report_item_name) {
		this.report_item_name = report_item_name;
	}
	public String getExam_date() {
		return exam_date;
	}
	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}
	public String getItem_result() {
		return item_result;
	}
	public void setItem_result(String item_result) {
		this.item_result = item_result;
	}
	public String getItem_unit() {
		return item_unit;
	}
	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	public String getLis_flag() {
		return lis_flag;
	}
	public void setLis_flag(String lis_flag) {
		this.lis_flag = lis_flag;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public int getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(int seq_code) {
		this.seq_code = seq_code;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getSh_doctor() {
		return sh_doctor;
	}
	public void setSh_doctor(String sh_doctor) {
		this.sh_doctor = sh_doctor;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getRead_flag() {
		return read_flag;
	}
	public void setRead_flag(int read_flag) {
		this.read_flag = read_flag;
	}
	public String getPacs_checkno() {
		return pacs_checkno;
	}
	public void setPacs_checkno(String pacs_checkno) {
		this.pacs_checkno = pacs_checkno;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getPacs_item_code() {
		return pacs_item_code;
	}
	public void setPacs_item_code(String pacs_item_code) {
		this.pacs_item_code = pacs_item_code;
	}
	public String getStudy_type() {
		return study_type;
	}
	public void setStudy_type(String study_type) {
		this.study_type = study_type;
	}
	public String getStudy_body_part() {
		return study_body_part;
	}
	public void setStudy_body_part(String study_body_part) {
		this.study_body_part = study_body_part;
	}
	public String getClinic_diagnose() {
		return clinic_diagnose;
	}
	public void setClinic_diagnose(String clinic_diagnose) {
		this.clinic_diagnose = clinic_diagnose;
	}
	public String getClinic_symptom() {
		return clinic_symptom;
	}
	public void setClinic_symptom(String clinic_symptom) {
		this.clinic_symptom = clinic_symptom;
	}
	public String getClinic_advice() {
		return clinic_advice;
	}
	public void setClinic_advice(String clinic_advice) {
		this.clinic_advice = clinic_advice;
	}
	public String getIs_abnormal() {
		return is_abnormal;
	}
	public void setIs_abnormal(String is_abnormal) {
		this.is_abnormal = is_abnormal;
	}
	public String getReport_img_path() {
		return report_img_path;
	}
	public void setReport_img_path(String report_img_path) {
		this.report_img_path = report_img_path;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public int getStudy_state() {
		return study_state;
	}
	public void setStudy_state(int study_state) {
		this.study_state = study_state;
	}
	public String getReg_doc() {
		return reg_doc;
	}
	public void setReg_doc(String reg_doc) {
		this.reg_doc = reg_doc;
	}
	public String getCheck_doc() {
		return check_doc;
	}
	public void setCheck_doc(String check_doc) {
		this.check_doc = check_doc;
	}
	public String getCheck_date() {
		return check_date;
	}
	public void setCheck_date(String check_date) {
		this.check_date = check_date;
	}
	public String getReport_doc() {
		return report_doc;
	}
	public void setReport_doc(String report_doc) {
		this.report_doc = report_doc;
	}
	public String getReport_date() {
		return report_date;
	}
	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}
	public String getAudit_doc() {
		return audit_doc;
	}
	public void setAudit_doc(String audit_doc) {
		this.audit_doc = audit_doc;
	}
	public String getAudit_date() {
		return audit_date;
	}
	public void setAudit_date(String audit_date) {
		this.audit_date = audit_date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}
	public int getIs_tran_image() {
		return is_tran_image;
	}
	public void setIs_tran_image(int is_tran_image) {
		this.is_tran_image = is_tran_image;
	}
	public int getIs_report_image() {
		return is_report_image;
	}
	public void setIs_report_image(int is_report_image) {
		this.is_report_image = is_report_image;
	}
	public String getConfig_key() {
		return config_key;
	}
	public void setConfig_key(String config_key) {
		this.config_key = config_key;
	}
	public String getConfig_url() {
		return config_url;
	}
	public void setConfig_url(String config_url) {
		this.config_url = config_url;
	}
	public String getConfig_method() {
		return config_method;
	}
	public void setConfig_method(String config_method) {
		this.config_method = config_method;
	}
	public String getConfig_remark() {
		return config_remark;
	}
	public void setConfig_remark(String config_remark) {
		this.config_remark = config_remark;
	}
	public Integer getMessage_inout() {
		return message_inout;
	}
	public void setMessage_inout(Integer message_inout) {
		this.message_inout = message_inout;
	}
	public String getChkItem() {
		return chkItem;
	}
	public void setChkItem(String chkItem) {
		this.chkItem = chkItem;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReq_no() {
		return req_no;
	}
	public void setReq_no(String req_no) {
		this.req_no = req_no;
	}
	public String getExam_no() {
		return exam_no;
	}
	public void setExam_no(String exam_no) {
		this.exam_no = exam_no;
	}
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getMessage_name() {
		return message_name;
	}
	public void setMessage_name(String message_name) {
		this.message_name = message_name;
	}
	public String getMessage_startDate() {
		return message_startDate;
	}
	public void setMessage_startDate(String message_startDate) {
		this.message_startDate = message_startDate;
	}
	public String getMessage_endDate() {
		return message_endDate;
	}
	public void setMessage_endDate(String message_endDate) {
		this.message_endDate = message_endDate;
	}
	public String getMessage_type() {
		return message_type;
	}
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessage_request() {
		return message_request;
	}
	public void setMessage_request(String message_request) {
		this.message_request = message_request;
	}
	public String getMessage_response() {
		return message_response;
	}
	public void setMessage_response(String message_response) {
		this.message_response = message_response;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getSys_request() {
		return sys_request;
	}
	public void setSys_request(String sys_request) {
		this.sys_request = sys_request;
	}
	public String getSys_respones() {
		return sys_respones;
	}
	public void setSys_respones(String sys_respones) {
		this.sys_respones = sys_respones;
	}
	public String getXtgnb_id() {
		return xtgnb_id;
	}
	public void setXtgnb_id(String xtgnb_id) {
		this.xtgnb_id = xtgnb_id;
	}
	public String getTil_id() {
		return til_id;
	}
	public void setTil_id(String til_id) {
		this.til_id = til_id;
	}
	public String getLdate() {
		return ldate;
	}
	public void setLdate(String ldate) {
		this.ldate = ldate;
	}
	public String getLmessage() {
		return lmessage;
	}
	public void setLmessage(String lmessage) {
		this.lmessage = lmessage;
	}
	

}
