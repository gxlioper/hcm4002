package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="exam_summary")
public class ExamSummary implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_doctor_id")
	private long exam_doctor_id;
	
	@Column(name="exam_info_id")
	private long exam_info_id;
	
	@Column(name="final_exam_result")
	private String final_exam_result;
	
	@Column(name="result_Y")
	private String result_Y;
	
	@Column(name="result_D")
	private String result_D;
	
	@Column(name="suggest")
	private String suggest;
	
	@Column(name="center_num")
	private String center_num;
	
	@Column(name="check_doc")
	private long check_doc;
	
	@Column(name="check_time")
	private Date check_time;
	
	@Column(name="approve_status")
	private String approve_status;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;
	
	@Column(name="acceptance_check")
	private long acceptance_check;
	
	@Column(name="acceptance_doctor")
	private long acceptance_doctor;
	
	@Column(name="acceptance_date")
	private Date acceptance_date;
	
	@Column(name="read_status")
	private long read_status;
	
	@Column(name="read_time")
	private Date read_time;
	
	@Column(name="exam_guidance")
	private String exam_guidance;
	
	@Column(name="app_type")
	private String app_type;

	@Column(name="final_status")
	private String final_status;
	
	@Column(name="final_time")
	private Date final_time;
	

	@Column(name="censoring_status")
	private String censoring_status;//复审状态
	
	@Column(name = "censoring_doc")
	private long censoring_doc;//复审医生

	@Column(name = "censoring_time")
	private Date censoring_time;//复审时间
	

	@Column(name="exam_num")
    private String exam_num;
	
	@Column(name="cancel_type")
	private long cancel_type;//操作类型 1 一键取消，0 一键恢复
	
	@Column(name="final_worknum")
	private long final_worknum;//总检工作量
	
	@Column(name="approve_worknum")
	private long approve_worknum;//审核工作量
	
	@Column(name="censoring_worknum")
	private long censoring_worknum;//终审工作量
	
	@Column(name="report_class")
	private long report_class;//报告等级
	
	@Column(name="report_class_user")
	private long report_class_user; //设定人
	
	@Column(name="report_class_date")
	private Date report_class_date; //设定时间
	
	@Column(name="report_class_type")
	private long report_class_type;//设定类型

    @Column(name="read_status1")
    private int read_status1;

    @Column(name="read_status2")
    private int read_status2;

    @Column(name="read_status3")
    private int read_status3;

    @Column(name="read_time1")
    private Date read_time1;

    @Column(name="read_time2")
    private Date read_time2;

    @Column(name="read_time3")
    private Date read_time3;



    public int getRead_status1() {
        return read_status1;
    }

    public void setRead_status1(int read_status1) {
        this.read_status1 = read_status1;
    }

    public int getRead_status2() {
        return read_status2;
    }

    public void setRead_status2(int read_status2) {
        this.read_status2 = read_status2;
    }

    public int getRead_status3() {
        return read_status3;
    }

    public void setRead_status3(int read_status3) {
        this.read_status3 = read_status3;
    }

    public Date getRead_time1() {
        return read_time1;
    }

    public void setRead_time1(Date read_time1) {
        this.read_time1 = read_time1;
    }

    public Date getRead_time2() {
        return read_time2;
    }

    public void setRead_time2(Date read_time2) {
        this.read_time2 = read_time2;
    }

    public Date getRead_time3() {
        return read_time3;
    }

    public void setRead_time3(Date read_time3) {
        this.read_time3 = read_time3;
    }

    public long getReport_class() {
		return report_class;
	}

	public void setReport_class(long report_class) {
		this.report_class = report_class;
	}

	public long getReport_class_user() {
		return report_class_user;
	}

	public void setReport_class_user(long report_class_user) {
		this.report_class_user = report_class_user;
	}

	public Date getReport_class_date() {
		return report_class_date;
	}

	public void setReport_class_date(Date report_class_date) {
		this.report_class_date = report_class_date;
	}

	public long getReport_class_type() {
		return report_class_type;
	}

	public void setReport_class_type(long report_class_type) {
		this.report_class_type = report_class_type;
	}

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

	public long getExam_doctor_id() {
		return exam_doctor_id;
	}

	public void setExam_doctor_id(long exam_doctor_id) {
		this.exam_doctor_id = exam_doctor_id;
	}

	public long getExam_info_id() {
		return exam_info_id;
	}

	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}

	public String getFinal_exam_result() {
		return final_exam_result;
	}

	public void setFinal_exam_result(String final_exam_result) {
		this.final_exam_result = final_exam_result;
	}

	public String getResult_Y() {
		return result_Y;
	}

	public void setResult_Y(String result_Y) {
		this.result_Y = result_Y;
	}

	public String getResult_D() {
		return result_D;
	}

	public void setResult_D(String result_D) {
		this.result_D = result_D;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public long getCheck_doc() {
		return check_doc;
	}

	public void setCheck_doc(long check_doc) {
		this.check_doc = check_doc;
	}

	public Date getCheck_time() {
		return check_time;
	}

	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}

	public String getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public long getAcceptance_check() {
		return acceptance_check;
	}

	public void setAcceptance_check(long acceptance_check) {
		this.acceptance_check = acceptance_check;
	}

	public long getAcceptance_doctor() {
		return acceptance_doctor;
	}

	public void setAcceptance_doctor(long acceptance_doctor) {
		this.acceptance_doctor = acceptance_doctor;
	}

	public Date getAcceptance_date() {
		return acceptance_date;
	}

	public void setAcceptance_date(Date acceptance_date) {
		this.acceptance_date = acceptance_date;
	}

	public long getRead_status() {
		return read_status;
	}

	public void setRead_status(long read_status) {
		this.read_status = read_status;
	}

    public Date getRead_time() {
        return read_time;
    }

    public void setRead_time(Date read_time) {
        this.read_time = read_time;
    }

    public String getExam_guidance() {
		return exam_guidance;
	}

	public void setExam_guidance(String exam_guidance) {
		this.exam_guidance = exam_guidance;
	}
	
	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	public String getFinal_status() {
		return final_status;
	}

	public void setFinal_status(String final_status) {
		this.final_status = final_status;
	}

	public Date getFinal_time() {
		return final_time;
	}

	public void setFinal_time(Date final_time) {
		this.final_time = final_time;
	}

	public String getCensoring_status() {
		return censoring_status;
	}

	public void setCensoring_status(String censoring_status) {
		this.censoring_status = censoring_status;
	}

	public long getCensoring_doc() {
		return censoring_doc;
	}

	public void setCensoring_doc(long censoring_doc) {
		this.censoring_doc = censoring_doc;
	}

	public Date getCensoring_time() {
		return censoring_time;
	}

	public void setCensoring_time(Date censoring_time) {
		this.censoring_time = censoring_time;
	}

	public long getCancel_type() {
		return cancel_type;
	}

	public void setCancel_type(long cancel_type) {
		this.cancel_type = cancel_type;
	}

	public long getFinal_worknum() {
		return final_worknum;
	}

	public void setFinal_worknum(long final_worknum) {
		this.final_worknum = final_worknum;
	}

	public long getApprove_worknum() {
		return approve_worknum;
	}

	public void setApprove_worknum(long approve_worknum) {
		this.approve_worknum = approve_worknum;
	}

	public long getCensoring_worknum() {
		return censoring_worknum;
	}

	public void setCensoring_worknum(long censoring_worknum) {
		this.censoring_worknum = censoring_worknum;
	}
	
}
