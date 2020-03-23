package com.hjw.wst.DTO;

public class LiscountDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 private long sam_demo_id;
	 private String contrstr;
	 private String demo_name;
	 private String sample_barcode;
	 private String item_name;
	 private String status;
	 private String statuss;
	 private String item_code; 
	 private long id;	 
	 private String exam_num;
	 private long examinfo_id;
	    
     public String getExam_num() {
		 return exam_num;
	 }

	 public void setExam_num(String exam_num) {
		 this.exam_num = exam_num;
	 }

    public long getExaminfo_id() {
        return examinfo_id;
    }

    public void setExaminfo_id(long examinfo_id) {
        this.examinfo_id = examinfo_id;
    }

    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getContrstr() {
		return contrstr;
	}
	public void setContrstr(String contrstr) {
		this.contrstr = contrstr;
	}
	public long getSam_demo_id() {
		return sam_demo_id;
	}
	public void setSam_demo_id(long sam_demo_id) {
		this.sam_demo_id = sam_demo_id;
	}
	public String getDemo_name() {
		return demo_name;
	}
	public void setDemo_name(String demo_name) {
		this.demo_name = demo_name;
	}
	public String getSample_barcode() {
		return sample_barcode;
	}
	public void setSample_barcode(String sample_barcode) {
		this.sample_barcode = sample_barcode;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		if("Y".equals(status))
		{
			this.setStatuss("已采样");
			this.setContrstr("取消发送");
		}else{
			this.setStatuss("未采样");
			this.setContrstr("发送");
		}
	}
	public String getStatuss() {
		return statuss;
	}
	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}
	
	}