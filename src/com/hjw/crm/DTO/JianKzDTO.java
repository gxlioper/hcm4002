package com.hjw.crm.DTO;

public class JianKzDTO {
	private String dwbm;//单位编码
	private String pic;//照片地址
	private String bh;//体检证编号
	private String xm;//姓名
	private String xb;//性别
	private String nl;//年龄
	private String dw;//工作单位
	private String hy;//行业
	private String gz;//工种
	private String dh;//联系电话
	private String sfzh;//身份证号
	private String rq;//体检日期
	private String hjg;//培训结果
	private String sfhg;//是否合格
	private String jg_xin;//心检查结果
	private String jg_gan;//肝检查结果
	private String jg_pi;//脾检查结果
	private String jg_tppa;//TPPA
	private String jg_pf;//皮肤检查结果
	private String jg_qt;//其他检查结果
	private String jg_lj;//痢疾
	private String jg_sh;//伤寒
	private String jg_xt;//胸部透视
	private String jg_alt;//ALT
	private String jg_hbsag;//乙肝Hbsag
	private String jg_hbeag;//乙肝hbeag
	private String jg_rpr;//RPR
	private String jg_lqj;//淋球菌
	private String jg_hav;//甲肝igm
	private String jg_hiv;//艾滋病
	private String sffz;//是否发证
	private String fzrq;//发证日期
	private String yxq;//有效期
	private String djczy;//登记操作员
	private String dyrq;//打印证日期
	private String dwdz;//单位地址
	private String exam_num;
	private String exam_nums;
	private String examflag;
	private String health_no;
	
	public String getHealth_no() {
		return health_no;
	}
	public void setHealth_no(String health_no) {
		this.health_no = health_no;
	}
	public String getExamflag() {
		return examflag;
	}
	public void setExamflag(String examflag) {
		this.examflag=examflag;
		if("1".equals(this.examflag)){
			this.setExamflag("已上传");
		}else if("2".equals(this.examflag)){
			this.setExamflag("上传失败");
		}else if("0".equals(this.examflag)){
			this.setExamflag("未上传");
		}
	}
	public String getExam_nums() {
		return exam_nums;
	}
	public void setExam_nums(String exam_nums) {
		this.exam_nums = exam_nums;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getDwbm() {
		return dwbm;
	}
	public void setDwbm(String dwbm) {
		this.dwbm = dwbm;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getNl() {
		return nl;
	}
	public void setNl(String nl) {
		this.nl = nl;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getHy() {
		return hy;
	}
	public void setHy(String hy) {
		this.hy = hy;
	}
	public String getGz() {
		return gz;
	}
	public void setGz(String gz) {
		this.gz = gz;
	}
	public String getDh() {
		return dh;
	}
	public void setDh(String dh) {
		this.dh = dh;
	}
	public String getSfzh() {
		return sfzh;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	public String getRq() {
		return rq;
	}
	public void setRq(String rq) {
		this.rq = rq;
	}
	public String getHjg() {
		return hjg;
	}
	public void setHjg(String hjg) {
		this.hjg = hjg;
	}
	public String getSfhg() {
		return sfhg;
	}
	public void setSfhg(String sfhg) {
		this.sfhg = sfhg;
	}
	public String getJg_xin() {
		return jg_xin;
	}
	public void setJg_xin(String jg_xin) {
		this.jg_xin = jg_xin;
	}
	public String getJg_gan() {
		return jg_gan;
	}
	public void setJg_gan(String jg_gan) {
		this.jg_gan = jg_gan;
	}
	public String getJg_pi() {
		return jg_pi;
	}
	public void setJg_pi(String jg_pi) {
		this.jg_pi = jg_pi;
	}
	public String getJg_tppa() {
		return jg_tppa;
	}
	public void setJg_tppa(String jg_tppa) {
		this.jg_tppa = jg_tppa;
	}
	public String getJg_pf() {
		return jg_pf;
	}
	public void setJg_pf(String jg_pf) {
		this.jg_pf = jg_pf;
	}
	public String getJg_qt() {
		return jg_qt;
	}
	public void setJg_qt(String jg_qt) {
		this.jg_qt = jg_qt;
	}
	public String getJg_lj() {
		return jg_lj;
	}
	public void setJg_lj(String jg_lj) {
		this.jg_lj = jg_lj;
	}
	public String getJg_sh() {
		return jg_sh;
	}
	public void setJg_sh(String jg_sh) {
		this.jg_sh = jg_sh;
	}
	public String getJg_xt() {
		return jg_xt;
	}
	public void setJg_xt(String jg_xt) {
		this.jg_xt = jg_xt;
	}
	public String getJg_alt() {
		return jg_alt;
	}
	public void setJg_alt(String jg_alt) {
		this.jg_alt = jg_alt;
	}
	public String getJg_hbsag() {
		return jg_hbsag;
	}
	public void setJg_hbsag(String jg_hbsag) {
		this.jg_hbsag = jg_hbsag;
	}
	public String getJg_hbeag() {
		return jg_hbeag;
	}
	public void setJg_hbeag(String jg_hbeag) {
		this.jg_hbeag = jg_hbeag;
	}
	public String getJg_rpr() {
		return jg_rpr;
	}
	public void setJg_rpr(String jg_rpr) {
		this.jg_rpr = jg_rpr;
	}
	public String getJg_lqj() {
		return jg_lqj;
	}
	public void setJg_lqj(String jg_lqj) {
		this.jg_lqj = jg_lqj;
	}
	public String getJg_hav() {
		return jg_hav;
	}
	public void setJg_hav(String jg_hav) {
		this.jg_hav = jg_hav;
	}
	public String getJg_hiv() {
		return jg_hiv;
	}
	public void setJg_hiv(String jg_hiv) {
		this.jg_hiv = jg_hiv;
	}
	public String getSffz() {
		return sffz;
	}
	public void setSffz(String sffz) {
		this.sffz = sffz;
	}
	public String getFzrq() {
		return fzrq;
	}
	public void setFzrq(String fzrq) {
		this.fzrq = fzrq;
	}
	public String getYxq() {
		return yxq;
	}
	public void setYxq(String yxq) {
		this.yxq = yxq;
	}
	public String getDjczy() {
		return djczy;
	}
	public void setDjczy(String djczy) {
		this.djczy = djczy;
	}
	public String getDyrq() {
		return dyrq;
	}
	public void setDyrq(String dyrq) {
		this.dyrq = dyrq;
	}
	public String getDwdz() {
		return dwdz;
	}
	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}
	
}
