package com.hjw.crm.model;

public class JianKzModel {
	private String dwbm;//单位编码
	private String dwbmname;
	private String pic;//照片地址
	private String bh;//体检证编号
	private String xm;//姓名
	private String xb;//性别
	private String nl;//年龄
	private String dw;//工作单位
	private String hy;//行业
	private String hycode;
	private String gz;//工种
	private String gzcode;
	private String examflag;
	private String barcode_print_type;
	public String getBarcode_print_type() {
		return barcode_print_type;
	}
	public void setBarcode_print_type(String barcode_print_type) {
		this.barcode_print_type = barcode_print_type;
	}
	public String getHycode() {
		return hycode;
	}
	public void setHycode(String hycode) {
		this.hycode = hycode;
	}
	public String getGzcode() {
		return gzcode;
	}
	public void setGzcode(String gzcode) {
		this.gzcode = gzcode;
	}
	private String dh;//联系电话
	private String sfzh;//身份证号
	private String rq;//体检日期
	private String hjg;//培训结果
	private String hjgcode;
	private String sfhg;//是否合格
	private String sfhgcode;
	private String jg_xin;//心检查结果
	private String jg_xincode;
	private String jg_gan;//肝检查结果
	private String jg_gancode;
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
	private String jg_picode;//脾检查结果
	private String jg_tppacode;//TPPA
	private String jg_pfcode;//皮肤检查结果
	private String jg_qtcode;//其他检查结果
	private String jg_ljcode;//痢疾
	private String jg_shcode;//伤寒
	private String jg_xtcode;//胸部透视
	private String jg_altcode;//ALT
	private String jg_hbsagcode;//乙肝Hbsag
	private String jg_hbeagcode;//乙肝hbeag
	private String jg_rprcode;//RPR
	private String jg_lqjcode;//淋球菌
	private String jg_havcode;//甲肝igm
	private String jg_hivcode;//艾滋病
	private String sffz;//是否发证
	private String sffzcode;
	private String fzrq;//发证日期
	private String yxq;//有效期
	private String djczy;//登记操作员
	private String dyrq;//打印证日期
	private String dwdz;//单位地址
	private String exam_num;
	private String exam_nums;
	private String chi_name;
	private String join_date;
	private String joinstart_date;
	private String joinend_date;
	private String company_id;
	private String arch_num;
	private String sfdy;//是否打印
	private String health_no;
	private int pra; 
	
	public int getPra() {
		return pra;
	}
	public void setPra(int pra) {
		this.pra = pra;
	}
	public String getSffzcode() {
		return sffzcode;
	}
	public void setSffzcode(String sffzcode) {
		this.sffzcode = sffzcode;
	}
	public String getHjgcode() {
		return hjgcode;
	}
	public void setHjgcode(String hjgcode) {
		this.hjgcode = hjgcode;
	}
	public String getSfhgcode() {
		return sfhgcode;
	}
	public void setSfhgcode(String sfhgcode) {
		this.sfhgcode = sfhgcode;
	}
	public String getSfdy() {
		return sfdy;
	}
	public void setSfdy(String sfdy) {
		this.sfdy = sfdy;
	}
	public String getJoinstart_date() {
		return joinstart_date;
	}
	public void setJoinstart_date(String joinstart_date) {
		this.joinstart_date = joinstart_date;
	}
	public String getJoinend_date() {
		return joinend_date;
	}
	public void setJoinend_date(String joinend_date) {
		this.joinend_date = joinend_date;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getExamflag() {
		return examflag;
	}
	public void setExamflag(String examflag) {
		this.examflag = examflag;
		if("1".equals(this.examflag)){
			this.setExamflag("已上传");
		}else if("0".equals(this.examflag)){
			this.setExamflag("未上传");
		}else if("2".equals(this.examflag)){
			this.setExamflag("上传失败");
		}
	}
	public String getJg_xincode() {
		return jg_xincode;
	}
	public void setJg_xincode(String jg_xincode) {
		this.jg_xincode = jg_xincode;
	}
	public String getJg_gancode() {
		return jg_gancode;
	}
	public void setJg_gancode(String jg_gancode) {
		this.jg_gancode = jg_gancode;
	}
	public String getJg_picode() {
		return jg_picode;
	}
	public void setJg_picode(String jg_picode) {
		this.jg_picode = jg_picode;
	}
	public String getJg_tppacode() {
		return jg_tppacode;
	}
	public void setJg_tppacode(String jg_tppacode) {
		this.jg_tppacode = jg_tppacode;
	}
	public String getJg_pfcode() {
		return jg_pfcode;
	}
	public void setJg_pfcode(String jg_pfcode) {
		this.jg_pfcode = jg_pfcode;
	}
	public String getJg_qtcode() {
		return jg_qtcode;
	}
	public void setJg_qtcode(String jg_qtcode) {
		this.jg_qtcode = jg_qtcode;
	}
	public String getJg_ljcode() {
		return jg_ljcode;
	}
	public void setJg_ljcode(String jg_ljcode) {
		this.jg_ljcode = jg_ljcode;
	}
	public String getJg_shcode() {
		return jg_shcode;
	}
	public void setJg_shcode(String jg_shcode) {
		this.jg_shcode = jg_shcode;
	}
	public String getJg_xtcode() {
		return jg_xtcode;
	}
	public void setJg_xtcode(String jg_xtcode) {
		this.jg_xtcode = jg_xtcode;
	}
	public String getJg_altcode() {
		return jg_altcode;
	}
	public void setJg_altcode(String jg_altcode) {
		this.jg_altcode = jg_altcode;
	}
	public String getJg_hbsagcode() {
		return jg_hbsagcode;
	}
	public void setJg_hbsagcode(String jg_hbsagcode) {
		this.jg_hbsagcode = jg_hbsagcode;
	}
	public String getJg_hbeagcode() {
		return jg_hbeagcode;
	}
	public void setJg_hbeagcode(String jg_hbeagcode) {
		this.jg_hbeagcode = jg_hbeagcode;
	}
	public String getJg_rprcode() {
		return jg_rprcode;
	}
	public void setJg_rprcode(String jg_rprcode) {
		this.jg_rprcode = jg_rprcode;
	}
	public String getJg_lqjcode() {
		return jg_lqjcode;
	}
	public void setJg_lqjcode(String jg_lqjcode) {
		this.jg_lqjcode = jg_lqjcode;
	}
	public String getJg_havcode() {
		return jg_havcode;
	}
	public void setJg_havcode(String jg_havcode) {
		this.jg_havcode = jg_havcode;
	}
	public String getJg_hivcode() {
		return jg_hivcode;
	}
	public void setJg_hivcode(String jg_hivcode) {
		this.jg_hivcode = jg_hivcode;
	}
	public String getDwbmname() {
		return dwbmname;
	}
	public void setDwbmname(String dwbmname) {
		this.dwbmname = dwbmname;
	}
	public String getChi_name() {
		return chi_name;
	}
	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
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
		if("男".equals(this.xb)){
			this.setXb("1");
		}else if("女".equals(this.xb)){
			this.setXb("2");
		}
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
	public String getHealth_no() {
		return health_no;
	}
	public void setHealth_no(String health_no) {
		this.health_no = health_no;
	}
}
