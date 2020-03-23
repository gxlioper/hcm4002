package com.hjw.crm.DTO;

import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.BatchDTO;

public class CrmSignBillPlanDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	private String id;
	private long company_id;//单位ID
	private String com_name;//单位名称
	private String name;
	private String sign_num;//签单计划编号
	
	private String sign_name;//签单计划名称
	
	private String sign_pingying;//拼音助记码
	
	private String sign_year;//年度
	
	private String sign_type;//签单类型
	
	private String old_new_type;//新旧分类
	
	private String customer_type;//客户分类
	
	private long sign_count;//签单数量
	
	private String sign_date;//预计签单日期
	
	private String sign_persion;//预计体检人数
	
	private String sign_amount;//预计体检金额
	
	private String end_date;//预计体检结束日期
	
	private String track_progress;//跟踪进度
	private String track_progresss;
	
	private String track_time;//跟踪进度变化时间
	
	private String sign_status;//签单计划状态
	private String sign_statuss;
	
	private String protect_date;//保护日期
	
	private String abort_date;//保护截止日期
	
	private String create_time;
	private String creater;
	private String flag;
	private String weipaichuzhuangdancount;
	private String kaishigenzongcount;
	private String zhizuofangancount;
	private String shengchenghetongcount;
	private String yibeidancount;
	private String tijianbinghuikuancount;
	private String yipaichuzhuangdancount;
	private String caogaocount;
	private String zhuangdancount;
	private String zhengshigaocount;
	private String counts;
	private String gusuanzongjine;
	private String gusuanzongrenshu;
	
	
	public String getGusuanzongjine() {
		return gusuanzongjine;
	}

	public void setGusuanzongjine(String gusuanzongjine) {
		this.gusuanzongjine = gusuanzongjine;
	}

	public String getGusuanzongrenshu() {
		return gusuanzongrenshu;
	}

	public void setGusuanzongrenshu(String gusuanzongrenshu) {
		this.gusuanzongrenshu = gusuanzongrenshu;
	}

	public String getWeipaichuzhuangdancount() {
		return weipaichuzhuangdancount;
	}

	public void setWeipaichuzhuangdancount(String weipaichuzhuangdancount) {
		this.weipaichuzhuangdancount = weipaichuzhuangdancount;
	}

	public String getKaishigenzongcount() {
		return kaishigenzongcount;
	}

	public void setKaishigenzongcount(String kaishigenzongcount) {
		this.kaishigenzongcount = kaishigenzongcount;
	}

	public String getZhizuofangancount() {
		return zhizuofangancount;
	}

	public void setZhizuofangancount(String zhizuofangancount) {
		this.zhizuofangancount = zhizuofangancount;
	}

	public String getShengchenghetongcount() {
		return shengchenghetongcount;
	}

	public void setShengchenghetongcount(String shengchenghetongcount) {
		this.shengchenghetongcount = shengchenghetongcount;
	}

	public String getYibeidancount() {
		return yibeidancount;
	}

	public void setYibeidancount(String yibeidancount) {
		this.yibeidancount = yibeidancount;
	}

	public String getTijianbinghuikuancount() {
		return tijianbinghuikuancount;
	}

	public void setTijianbinghuikuancount(String tijianbinghuikuancount) {
		this.tijianbinghuikuancount = tijianbinghuikuancount;
	}

	public String getYipaichuzhuangdancount() {
		return yipaichuzhuangdancount;
	}

	public void setYipaichuzhuangdancount(String yipaichuzhuangdancount) {
		this.yipaichuzhuangdancount = yipaichuzhuangdancount;
	}

	public String getCaogaocount() {
		return caogaocount;
	}

	public void setCaogaocount(String caogaocount) {
		this.caogaocount = caogaocount;
	}

	public String getZhuangdancount() {
		return zhuangdancount;
	}

	public void setZhuangdancount(String zhuangdancount) {
		this.zhuangdancount = zhuangdancount;
	}

	public String getZhengshigaocount() {
		return zhengshigaocount;
	}

	public void setZhengshigaocount(String zhengshigaocount) {
		this.zhengshigaocount = zhengshigaocount;
	}

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	private List<BatchDTO> children=new ArrayList<BatchDTO>();
	
	public List<BatchDTO> getChildren() {
		return children;
	}

	public void setChildren(List<BatchDTO> children) {
		this.children = children;
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

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public String getSign_num() {
		return sign_num;
	}

	public void setSign_num(String sign_num) {
		this.sign_num = sign_num;
	}

	public String getSign_name() {
		return sign_name;
	}

	public void setSign_name(String sign_name) {
		this.sign_name = sign_name;
	}

	public String getSign_year() {
		return sign_year;
	}

	public void setSign_year(String sign_year) {
		this.sign_year = sign_year;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getOld_new_type() {
		return old_new_type;
	}

	public void setOld_new_type(String old_new_type) {
		this.old_new_type = old_new_type;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public long getSign_count() {
		return sign_count;
	}

	public void setSign_count(long sign_count) {
		this.sign_count = sign_count;
	}

	public String getSign_date() {
		return sign_date;
	}

	public void setSign_date(String sign_date) {
		this.sign_date = sign_date;
	}

	public String getSign_persion() {
		return sign_persion;
	}

	public void setSign_persion(String sign_persion) {
		this.sign_persion = sign_persion;
	}

	public String getSign_amount() {
		return sign_amount;
	}

	public void setSign_amount(String sign_amount) {
		this.sign_amount = sign_amount;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getTrack_progress() {
		return track_progress;
	}

	public void setTrack_progress(String track_progress) {
		this.track_progress = track_progress;
		if("1".equals(track_progress)){
			this.track_progresss = "未排除撞单";
		}else if("2".equals(track_progress)){
			this.track_progresss = "开始跟踪";
		}else if("3".equals(track_progress)){
			this.track_progresss = "制作方案";
		}else if("4".equals(track_progress)){
			this.track_progresss = "生成合同";
		}else if("5".equals(track_progress)){
			this.track_progresss = "已备单";
		}else if("6".equals(track_progress)){
			this.track_progresss = "体检并回款";
		}else if("7".equals(track_progress)){
			this.track_progresss = "已排除撞单";
		}else if("8".equals(track_progress)){
			this.track_progresss = "合同审核通过";
		}
	}

	public String getTrack_time() {
		return track_time;
	}

	public void setTrack_time(String track_time) {
		this.track_time = track_time;
	}

	public String getSign_status() {
		return sign_status;
	}

	public void setSign_status(String sign_status) {
		this.sign_status = sign_status;
		if("1".equals(sign_status)){
			this.sign_statuss = "草稿";
		}else if("2".equals(sign_status)){
			this.sign_statuss = "撞单";
		}else if("3".equals(sign_status)){
			this.sign_statuss = "正式稿";
		}
	}

	public String getProtect_date() {
		return protect_date;
	}

	public void setProtect_date(String protect_date) {
		this.protect_date = protect_date;
	}

	public String getAbort_date() {
		return abort_date;
	}

	public void setAbort_date(String abort_date) {
		this.abort_date = abort_date;
	}

	public String getCom_name() {
		return com_name;
	}

	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}

	public String getTrack_progresss() {
		return track_progresss;
	}

	public void setTrack_progresss(String track_progresss) {
		this.track_progresss = track_progresss;
	}

	public String getSign_statuss() {
		return sign_statuss;
	}

	public void setSign_statuss(String sign_statuss) {
		this.sign_statuss = sign_statuss;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getSign_pingying() {
		return sign_pingying;
	}

	public void setSign_pingying(String sign_pingying) {
		this.sign_pingying = sign_pingying;
	}
}
