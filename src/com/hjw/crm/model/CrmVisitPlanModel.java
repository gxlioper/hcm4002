package com.hjw.crm.model;

import java.util.Date;


public class CrmVisitPlanModel {
	private static final long serialVersionUID = 1L;
	//id
	private String id;
	//档案号
	private String arch_num;
	//计划回访医生id
	private Long plan_doctor_id;
	//计划回访时间
	private String plan_visit_date;
	//回访内容
	private String visit_content;
	//健康计划编码
	private String visit_num;
	//回访状态：1-计划回访，2-开始回访，3-结束回访
	private String visit_status;
	//创建人
	private Long creater;
	//创建时间
	private Date create_time;
	//姓名
	private String name;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	private String doctorName;
	private String ids;
	private String exam_num;
	private Long doctor_id;
	private String doctorname;
	private String flag;
	private String join_date;
	//今日任务个数
	private String jinrirenwucount;
	//普通任务个数
	private String putongrenwucount;
	//一般人物个数
	private String yibanrenwucount;
	//重要任务个数
	private String zhongyaorenwucount;
	//咨询个数
	private String dafuzixun;
	//回访跟踪个数
	private String huifanggenzong;
	//客户数
	private String kehucount;
	//已答复客户数
	private String yidafukehucount;
	//未答复客户数
	private String weidafukehucount;
	//任务总数
	private String rewuzongshu;
	//客户总数
	private String kehuzongshu;
	//普通任务总数
	private String putongrenwuzongshu;
	//一般任务总数
	private String yibanrenwuzongshu;
	//重要任务总数
	private String zhongyaorenwuzongshu;
	//已答复
	private String yidafukehuzongshu;
	//未答复
	private String weidafukehuzongshu;
	//失访记录
	private String shifangjilu;
	private String fujianflag;
	
	private String report_print_type;
	private String zyb_report_print_type;
	
	//计划回访医生id
	private Long plan_doctor_id1;
	//计划回访时间
	private String plan_visit_date1;
	//计划回访医生id
	private Long plan_doctor_id2;
	//计划回访时间
	private String plan_visit_date2;
	//计划回访医生id
	private Long plan_doctor_id3;
	//计划回访时间
	private String plan_visit_date3;
	//计划回访医生id
	private Long plan_doctor_id4;
	//计划回访时间
	private String plan_visit_date4;
	//生日客户数
	private String shengrikehushu;
	
	private String cvr_id;
	private long user_id;
	private String persionName;
	
	public Long getPlan_doctor_id1() {
		return plan_doctor_id1;
	}
	public void setPlan_doctor_id1(Long plan_doctor_id1) {
		this.plan_doctor_id1 = plan_doctor_id1;
	}
	public String getPlan_visit_date1() {
		return plan_visit_date1;
	}
	public void setPlan_visit_date1(String plan_visit_date1) {
		this.plan_visit_date1 = plan_visit_date1;
	}
	public Long getPlan_doctor_id2() {
		return plan_doctor_id2;
	}
	public void setPlan_doctor_id2(Long plan_doctor_id2) {
		this.plan_doctor_id2 = plan_doctor_id2;
	}
	public String getPlan_visit_date2() {
		return plan_visit_date2;
	}
	public void setPlan_visit_date2(String plan_visit_date2) {
		this.plan_visit_date2 = plan_visit_date2;
	}
	public Long getPlan_doctor_id3() {
		return plan_doctor_id3;
	}
	public void setPlan_doctor_id3(Long plan_doctor_id3) {
		this.plan_doctor_id3 = plan_doctor_id3;
	}
	public String getPlan_visit_date3() {
		return plan_visit_date3;
	}
	public void setPlan_visit_date3(String plan_visit_date3) {
		this.plan_visit_date3 = plan_visit_date3;
	}
	public Long getPlan_doctor_id4() {
		return plan_doctor_id4;
	}
	public void setPlan_doctor_id4(Long plan_doctor_id4) {
		this.plan_doctor_id4 = plan_doctor_id4;
	}
	public String getPlan_visit_date4() {
		return plan_visit_date4;
	}
	public void setPlan_visit_date4(String plan_visit_date4) {
		this.plan_visit_date4 = plan_visit_date4;
	}
	public String getFujianflag() {
		return fujianflag;
	}
	public void setFujianflag(String fujianflag) {
		this.fujianflag = fujianflag;
	}
	public String getShifangjilu() {
		return shifangjilu;
	}
	public void setShifangjilu(String shifangjilu) {
		this.shifangjilu = shifangjilu;
	}
	public String getRewuzongshu() {
		return rewuzongshu;
	}
	public void setRewuzongshu(String rewuzongshu) {
		this.rewuzongshu = rewuzongshu;
	}
	public String getKehuzongshu() {
		return kehuzongshu;
	}
	public void setKehuzongshu(String kehuzongshu) {
		this.kehuzongshu = kehuzongshu;
	}
	public String getPutongrenwuzongshu() {
		return putongrenwuzongshu;
	}
	public void setPutongrenwuzongshu(String putongrenwuzongshu) {
		this.putongrenwuzongshu = putongrenwuzongshu;
	}
	public String getYibanrenwuzongshu() {
		return yibanrenwuzongshu;
	}
	public void setYibanrenwuzongshu(String yibanrenwuzongshu) {
		this.yibanrenwuzongshu = yibanrenwuzongshu;
	}
	public String getZhongyaorenwuzongshu() {
		return zhongyaorenwuzongshu;
	}
	public void setZhongyaorenwuzongshu(String zhongyaorenwuzongshu) {
		this.zhongyaorenwuzongshu = zhongyaorenwuzongshu;
	}
	public String getYidafukehuzongshu() {
		return yidafukehuzongshu;
	}
	public void setYidafukehuzongshu(String yidafukehuzongshu) {
		this.yidafukehuzongshu = yidafukehuzongshu;
	}
	public String getWeidafukehuzongshu() {
		return weidafukehuzongshu;
	}
	public void setWeidafukehuzongshu(String weidafukehuzongshu) {
		this.weidafukehuzongshu = weidafukehuzongshu;
	}
	public String getYidafukehucount() {
		return yidafukehucount;
	}
	public void setYidafukehucount(String yidafukehucount) {
		this.yidafukehucount = yidafukehucount;
	}
	public String getWeidafukehucount() {
		return weidafukehucount;
	}
	public void setWeidafukehucount(String weidafukehucount) {
		this.weidafukehucount = weidafukehucount;
	}
	public String getKehucount() {
		return kehucount;
	}
	public void setKehucount(String kehucount) {
		this.kehucount = kehucount;
	}
	public String getHuifanggenzong() {
		return huifanggenzong;
	}
	public void setHuifanggenzong(String huifanggenzong) {
		this.huifanggenzong = huifanggenzong;
	}
	public String getDafuzixun() {
		return dafuzixun;
	}
	public void setDafuzixun(String dafuzixun) {
		this.dafuzixun = dafuzixun;
	}
	public String getYibanrenwucount() {
		return yibanrenwucount;
	}
	public void setYibanrenwucount(String yibanrenwucount) {
		this.yibanrenwucount = yibanrenwucount;
	}
	public String getZhongyaorenwucount() {
		return zhongyaorenwucount;
	}
	public void setZhongyaorenwucount(String zhongyaorenwucount) {
		this.zhongyaorenwucount = zhongyaorenwucount;
	}
	public String getPutongrenwucount() {
		return putongrenwucount;
	}
	public void setPutongrenwucount(String putongrenwucount) {
		this.putongrenwucount = putongrenwucount;
	}
	public String getJinrirenwucount() {
		return jinrirenwucount;
	}
	public void setJinrirenwucount(String jinrirenwucount) {
		this.jinrirenwucount = jinrirenwucount;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	private String visit_important;//重要级别
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Long getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(Long doctor_id) {
		this.doctor_id = doctor_id;
	}
	public String getDoctorname() {
		return doctorname;
	}
	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public Long getPlan_doctor_id() {
		return plan_doctor_id;
	}
	public void setPlan_doctor_id(Long plan_doctor_id) {
		this.plan_doctor_id = plan_doctor_id;
	}
	public String getPlan_visit_date() {
		return plan_visit_date;
	}
	public void setPlan_visit_date(String plan_visit_date) {
		this.plan_visit_date = plan_visit_date;
	}
	public String getVisit_content() {
		return visit_content;
	}
	public void setVisit_content(String visit_content) {
		this.visit_content = visit_content;
	}
	public String getVisit_num() {
		return visit_num;
	}
	public void setVisit_num(String visit_num) {
		this.visit_num = visit_num;
	}
	public String getVisit_status() {
		return visit_status;
	}
	public void setVisit_status(String visit_status) {
		this.visit_status = visit_status;
	}
	public Long getCreater() {
		return creater;
	}
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getVisit_important() {
		return visit_important;
	}
	public void setVisit_important(String visit_important) {
		this.visit_important = visit_important;
	}
	public String getCvr_id() {
		return cvr_id;
	}
	public void setCvr_id(String cvr_id) {
		this.cvr_id = cvr_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getPersionName() {
		return persionName;
	}
	public void setPersionName(String persionName) {
		this.persionName = persionName;
	}
	public String getShengrikehushu() {
		return shengrikehushu;
	}
	public void setShengrikehushu(String shengrikehushu) {
		this.shengrikehushu = shengrikehushu;
	}
	public String getReport_print_type() {
		return report_print_type;
	}
	public String getZyb_report_print_type() {
		return zyb_report_print_type;
	}
	public void setReport_print_type(String report_print_type) {
		this.report_print_type = report_print_type;
	}
	public void setZyb_report_print_type(String zyb_report_print_type) {
		this.zyb_report_print_type = zyb_report_print_type;
	}
}
