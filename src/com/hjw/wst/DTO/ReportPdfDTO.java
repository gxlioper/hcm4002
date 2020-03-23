package com.hjw.wst.DTO;


public class ReportPdfDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	private String exam_num="";
	private String arch_num="";
	private int report_year;
	private String pdf_file="";
	private String report_path="";
	private String ftp_server="";
	private int ftp_port;
	private String ftp_user="";
	private String ftp_password="";
	private int is_finished;
	private String report_date="";
	private String create_time="";
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public int getReport_year() {
		return report_year;
	}
	public void setReport_year(int report_year) {
		this.report_year = report_year;
	}
	public String getPdf_file() {
		return pdf_file;
	}
	public void setPdf_file(String pdf_file) {
		this.pdf_file = pdf_file;
	}
	public String getReport_path() {
		return report_path;
	}
	public void setReport_path(String report_path) {
		this.report_path = report_path;
	}
	public String getFtp_server() {
		return ftp_server;
	}
	public void setFtp_server(String ftp_server) {
		this.ftp_server = ftp_server;
	}
	public int getFtp_port() {
		return ftp_port;
	}
	public void setFtp_port(int ftp_port) {
		this.ftp_port = ftp_port;
	}
	public String getFtp_user() {
		return ftp_user;
	}
	public void setFtp_user(String ftp_user) {
		this.ftp_user = ftp_user;
	}
	public String getFtp_password() {
		return ftp_password;
	}
	public void setFtp_password(String ftp_password) {
		this.ftp_password = ftp_password;
	}
	public int getIs_finished() {
		return is_finished;
	}
	public void setIs_finished(int is_finished) {
		this.is_finished = is_finished;
	}
	public String getReport_date() {
		return report_date;
	}
	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
}