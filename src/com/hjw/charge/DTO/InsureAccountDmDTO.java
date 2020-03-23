package com.hjw.charge.DTO;
/* 医保收费记录表*/

import java.util.List;

public class InsureAccountDmDTO {
	private long  id;
	private String flag;
	private String cause;
	private String akc190;
	private String bae010;
	private String aae072;
	private String bke298;
	private String aka078;
	private String aka130;
	private String aaz500;
	private String aac999;
	private String aac003;
	private String aac004;
	private String aac004_mc;
	private String aac006;
	private String aaz149;
	private String bkc001;
	private String bkc001_mc;
	private String bkc026;
	private String bkc026_mc;
	private String bke174;
	private String bke174_mc;
	private String aab301;
	private double aka151;
	private double akc227;
	private double bkc040;
	private double bkc041;
	private double bkc102;
	private double bkc045;
	private double bkc052;
	private double bkc059;
	private double bkc062;
	private double bkc060;
	private double ake173;
	private double ake026;
	private double bkc075;
	private String bke921;
	private String bke922;
	private double bkc166;
	private double bkc167;
	private double bkc011;
	private double akc087;
	private double bkc591;
	private String aka150;
	private String bkc014;
	private String bkc171;
	private String ake007;
	private String bae029;
	private String aae011;
	private String amc029;
	private String amc026;
	private String amc028;
	private String amc020;
	private String bmc041;
	private String bka188;
	private String bka542;
	private String bka542_mc;
	private double bkeb34;
	private String aka063; 
	private String aka063_mc;
//	private double akc227;
	private double bkc008;
	private double bkc009;
	private double bkc010;
//	private double bkc011;
	private List<InsureAccountMzDTO> mzlist;
	private List<InsureAccountFpDTO> fplist;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	
	public String getBae010() {
		return bae010;
	}
	public void setBae010(String bae010) {
		this.bae010 = bae010;
	}
	public String getAkc190() {
		return akc190;
	}
	public void setAkc190(String akc190) {
		this.akc190 = akc190;
	}
	public String getAae072() {
		return aae072;
	}
	public void setAae072(String aae072) {
		this.aae072 = aae072;
	}
	public String getBke298() {
		return bke298;
	}
	public void setBke298(String bke298) {
		this.bke298 = bke298;
	}
	public String getAka078() {
		return aka078;
	}
	public void setAka078(String aka078) {
		this.aka078 = aka078;
	}
	public String getAka130() {
		return aka130;
	}
	public void setAka130(String aka130) {
		this.aka130 = aka130;
	}
	public String getAaz500() {
		return aaz500;
	}
	public void setAaz500(String aaz500) {
		this.aaz500 = aaz500;
	}
	public String getAac999() {
		return aac999;
	}
	public void setAac999(String aac999) {
		this.aac999 = aac999;
	}
	public String getAac003() {
		return aac003;
	}
	public void setAac003(String aac003) {
		this.aac003 = aac003;
	}
	public String getAac004() {
		return aac004;
	}
	public void setAac004(String aac004) {
		this.aac004 = aac004;
	}
	public String getAac004_mc() {
		return aac004_mc;
	}
	public void setAac004_mc(String aac004_mc) {
		this.aac004_mc = aac004_mc;
	}
	public String getAac006() {
		return aac006;
	}
	public void setAac006(String aac006) {
		this.aac006 = aac006;
	}
	public String getAaz149() {
		return aaz149;
	}
	public void setAaz149(String aaz149) {
		this.aaz149 = aaz149;
	}
	public String getBkc001() {
		return bkc001;
	}
	public void setBkc001(String bkc001) {
		this.bkc001 = bkc001;
	}
	public String getBkc001_mc() {
		return bkc001_mc;
	}
	public void setBkc001_mc(String bkc001_mc) {
		this.bkc001_mc = bkc001_mc;
	}
	public String getBkc026() {
		return bkc026;
	}
	public void setBkc026(String bkc026) {
		this.bkc026 = bkc026;
	}
	public String getBkc026_mc() {
		return bkc026_mc;
	}
	public void setBkc026_mc(String bkc026_mc) {
		this.bkc026_mc = bkc026_mc;
	}
	public String getBke174() {
		return bke174;
	}
	public void setBke174(String bke174) {
		this.bke174 = bke174;
	}
	public String getBke174_mc() {
		return bke174_mc;
	}
	public void setBke174_mc(String bke174_mc) {
		this.bke174_mc = bke174_mc;
	}
	public String getAab301() {
		return aab301;
	}
	public void setAab301(String aab301) {
		this.aab301 = aab301;
	}
	public double getAka151() {
		return aka151;
	}
	public void setAka151(double aka151) {
		this.aka151 = aka151;
	}
	public double getAkc227() {
		return akc227;
	}
	public void setAkc227(double akc227) {
		this.akc227 = akc227;
	}
	public double getBkc040() {
		return bkc040;
	}
	public void setBkc040(double bkc040) {
		this.bkc040 = bkc040;
	}
	public double getBkc041() {
		return bkc041;
	}
	public void setBkc041(double bkc041) {
		this.bkc041 = bkc041;
	}
	public double getBkc102() {
		return bkc102;
	}
	public void setBkc102(double bkc102) {
		this.bkc102 = bkc102;
	}
	public double getBkc045() {
		return bkc045;
	}
	public void setBkc045(double bkc045) {
		this.bkc045 = bkc045;
	}
	public double getBkc052() {
		return bkc052;
	}
	public void setBkc052(double bkc052) {
		this.bkc052 = bkc052;
	}
	public double getBkc059() {
		return bkc059;
	}
	public void setBkc059(double bkc059) {
		this.bkc059 = bkc059;
	}
	public double getBkc062() {
		return bkc062;
	}
	public void setBkc062(double bkc062) {
		this.bkc062 = bkc062;
	}
	public double getBkc060() {
		return bkc060;
	}
	public void setBkc060(double bkc060) {
		this.bkc060 = bkc060;
	}
	public double getAke173() {
		return ake173;
	}
	public void setAke173(double ake173) {
		this.ake173 = ake173;
	}
	public double getAke026() {
		return ake026;
	}
	public void setAke026(double ake026) {
		this.ake026 = ake026;
	}
	public double getBkc075() {
		return bkc075;
	}
	public void setBkc075(double bkc075) {
		this.bkc075 = bkc075;
	}
	public String getBke921() {
		return bke921;
	}
	public void setBke921(String bke921) {
		this.bke921 = bke921;
	}
	public String getBke922() {
		return bke922;
	}
	public void setBke922(String bke922) {
		this.bke922 = bke922;
	}
	public double getBkc166() {
		return bkc166;
	}
	public void setBkc166(double bkc166) {
		this.bkc166 = bkc166;
	}
	public double getBkc167() {
		return bkc167;
	}
	public void setBkc167(double bkc167) {
		this.bkc167 = bkc167;
	}
	public double getBkc011() {
		return bkc011;
	}
	public void setBkc011(double bkc011) {
		this.bkc011 = bkc011;
	}
	public double getAkc087() {
		return akc087;
	}
	public void setAkc087(double akc087) {
		this.akc087 = akc087;
	}
	public double getBkc591() {
		return bkc591;
	}
	public void setBkc591(double bkc591) {
		this.bkc591 = bkc591;
	}
	public String getAka150() {
		return aka150;
	}
	public void setAka150(String aka150) {
		this.aka150 = aka150;
	}
	public String getBkc014() {
		return bkc014;
	}
	public void setBkc014(String bkc014) {
		this.bkc014 = bkc014;
	}
	public String getBkc171() {
		return bkc171;
	}
	public void setBkc171(String bkc171) {
		this.bkc171 = bkc171;
	}
	public String getAke007() {
		return ake007;
	}
	public void setAke007(String ake007) {
		this.ake007 = ake007;
	}
	public String getBae029() {
		return bae029;
	}
	public void setBae029(String bae029) {
		this.bae029 = bae029;
	}
	public String getAae011() {
		return aae011;
	}
	public void setAae011(String aae011) {
		this.aae011 = aae011;
	}
	public String getAmc029() {
		return amc029;
	}
	public void setAmc029(String amc029) {
		this.amc029 = amc029;
	}
	public String getAmc026() {
		return amc026;
	}
	public void setAmc026(String amc026) {
		this.amc026 = amc026;
	}
	public String getAmc028() {
		return amc028;
	}
	public void setAmc028(String amc028) {
		this.amc028 = amc028;
	}
	public String getAmc020() {
		return amc020;
	}
	public void setAmc020(String amc020) {
		this.amc020 = amc020;
	}
	public String getBmc041() {
		return bmc041;
	}
	public void setBmc041(String bmc041) {
		this.bmc041 = bmc041;
	}
	public String getBka188() {
		return bka188;
	}
	public void setBka188(String bka188) {
		this.bka188 = bka188;
	}
	public String getBka542() {
		return bka542;
	}
	public void setBka542(String bka542) {
		this.bka542 = bka542;
	}
	public String getBka542_mc() {
		return bka542_mc;
	}
	public void setBka542_mc(String bka542_mc) {
		this.bka542_mc = bka542_mc;
	}
	public double getBkeb34() {
		return bkeb34;
	}
	public void setBkeb34(double bkeb34) {
		this.bkeb34 = bkeb34;
	}
	public String getAka063() {
		return aka063;
	}
	public void setAka063(String aka063) {
		this.aka063 = aka063;
	}
	public String getAka063_mc() {
		return aka063_mc;
	}
	public void setAka063_mc(String aka063_mc) {
		this.aka063_mc = aka063_mc;
	}
	public double getBkc008() {
		return bkc008;
	}
	public void setBkc008(double bkc008) {
		this.bkc008 = bkc008;
	}
	public double getBkc009() {
		return bkc009;
	}
	public void setBkc009(double bkc009) {
		this.bkc009 = bkc009;
	}
	public double getBkc010() {
		return bkc010;
	}
	public void setBkc010(double bkc010) {
		this.bkc010 = bkc010;
	}
	public List<InsureAccountMzDTO> getMzlist() {
		return mzlist;
	}
	public void setMzlist(List<InsureAccountMzDTO> mzlist) {
		this.mzlist = mzlist;
	}
	public List<InsureAccountFpDTO> getFplist() {
		return fplist;
	}
	public void setFplist(List<InsureAccountFpDTO> fplist) {
		this.fplist = fplist;
	}

}
