package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hjw.wst.domain.WebResrelAtionship;
import com.synjones.framework.util.DateTimeUtil;

public class UserInfoDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

		private Integer id;

		private Integer exam_center_id;

		private String chi_Name;

		private String log_Name;

		private String pwd_encrypted;

		private String is_active;
		
		private String isactives;

		private String email;

		private String phone_num;

		private String user_pic_path;

		private String user_signature;

		private Long invoice_num_min;

		private Long invoice_num_max;

		private String discount;

		private long creater;
		private String creaters;
		private Date createTime;
		
		private String createtimes;
		

		private long updater;
		private String updaters;

		private Date updateTime;
		
		private String updateTimes;
		
		private String work_num;
		
		private String center_name;
		private String dep_name;
		private String role_name;
		private String dept_name;
		private List<WebResrelAtionship> webResource=new ArrayList<WebResrelAtionship>();//资源

		public List<WebResrelAtionship> getWebResource() {
			return webResource;
		}

		public void setWebResource(List<WebResrelAtionship> webResource) {
			this.webResource = webResource;
		}

		public String getDept_name() {
			return dept_name;
		}

		public void setDept_name(String dept_name) {
			this.dept_name = dept_name;
		}

		public String getDep_name() {
			return dep_name;
		}

		public void setDep_name(String dep_name) {
			this.dep_name = dep_name;
		}

		public String getRole_name() {
			return role_name;
		}

		public void setRole_name(String role_name) {
			this.role_name = role_name;
		}

		public String getCenter_name() {
			return center_name;
		}

		public void setCenter_name(String center_name) {
			this.center_name = center_name;
		}

		public String getCreatetimes() {
			return createtimes;
		}

		public void setCreatetimes(String createtimes) {
			this.createtimes = createtimes;
		}

		public String getUpdateTimes() {
			return updateTimes;
		}

		public void setUpdateTimes(String updateTimes) {
			this.updateTimes = updateTimes;
		}

		public String getIsactives() {
			return isactives;
		}

		public void setIsactives(String isactives) {
			this.isactives = isactives;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getExam_center_id() {
			return exam_center_id;
		}

		public void setExam_center_id(Integer exam_center_id) {
			this.exam_center_id = exam_center_id;
		}

		public String getChi_Name() {
			return chi_Name;
		}

		public void setChi_Name(String chi_Name) {
			this.chi_Name = chi_Name;
		}

		public String getLog_Name() {
			return log_Name;
		}

		public void setLog_Name(String log_Name) {
			this.log_Name = log_Name;
		}

		public String getPwd_encrypted() {
			return pwd_encrypted;
		}

		public void setPwd_encrypted(String pwd_encrypted) {
			this.pwd_encrypted = pwd_encrypted;
		}

		public String getIs_active() {
			return is_active;
		}

		public void setIs_active(String is_active) {
			this.is_active = is_active;
			if("Y".equals(is_active)){
				this.setIsactives("启用");
			}else{
				this.setIsactives("停用");
			}
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone_num() {
			return phone_num;
		}

		public void setPhone_num(String phone_num) {
			this.phone_num = phone_num;
		}

		public String getUser_pic_path() {
			return user_pic_path;
		}

		public void setUser_pic_path(String user_pic_path) {
			this.user_pic_path = user_pic_path;
		}

		public String getUser_signature() {
			return user_signature;
		}

		public void setUser_signature(String user_signature) {
			this.user_signature = user_signature;
		}

		public Long getInvoice_num_min() {
			return invoice_num_min;
		}

		public void setInvoice_num_min(Long invoice_num_min) {
			this.invoice_num_min = invoice_num_min;
		}

		public Long getInvoice_num_max() {
			return invoice_num_max;
		}

		public void setInvoice_num_max(Long invoice_num_max) {
			this.invoice_num_max = invoice_num_max;
		}

		public String getDiscount() {
			return discount;
		}

		public void setDiscount(String discount) {
			this.discount = discount;
		}

		public long getCreater() {
			return creater;
		}

		public void setCreater(long creater) {
			this.creater = creater;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
			if(createTime!=null){
				this.setCreatetimes(DateTimeUtil.shortFmt(createTime));
			}
		}

		public long  getUpdater() {
			return updater;
		}

		public void setUpdater(long updater) {
			this.updater = updater;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
			if(updateTime!=null){
				this.setUpdateTimes(DateTimeUtil.shortFmt(createTime));
			}
		}

		public String getWork_num() {
			return work_num;
		}

		public void setWork_num(String work_num) {
			this.work_num = work_num;
		}

		public String getCreaters() {
			return creaters;
		}

		public void setCreaters(String creaters) {
			this.creaters = creaters;
		}

		public String getUpdaters() {
			return updaters;
		}

		public void setUpdaters(String updaters) {
			this.updaters = updaters;
		}

}
