package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  团体结算体检人员明细表
     * @author: yangm     
     * @date:   2016年12月13日 下午4:54:02   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "team_account_exam_list")
public class TeamAccountExamList implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

	    @Column(name = "batchid")
		private long batchid;
	    
	    @Column(name = "acc_num")
	    private String acc_num="";
	    
	    @Column(name = "exam_num")
	    private String exam_num="";
	    
	    @Column(name = "isPrePay")
	    private String isPrePay="";
	    
	    @Column(name = "isnotPay")
	    private String isnotPay="";
	    
	    @Column(name = "createtime")
	    private Date createtime;
	    
	    @Column(name = "center_num")
	    private String center_num="";    

		public String getCenter_num() {
			return center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getBatchid() {
			return batchid;
		}

		public void setBatchid(long batchid) {
			this.batchid = batchid;
		}

		public String getAcc_num() {
			return acc_num;
		}

		public void setAcc_num(String acc_num) {
			this.acc_num = acc_num;
		}

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

		public String getIsPrePay() {
			return isPrePay;
		}

		public void setIsPrePay(String isPrePay) {
			this.isPrePay = isPrePay;
		}

		public String getIsnotPay() {
			return isnotPay;
		}

		public void setIsnotPay(String isnotPay) {
			this.isnotPay = isnotPay;
		}

		public Date getCreatetime() {
			return createtime;
		}

		public void setCreatetime(Date createtime) {
			this.createtime = createtime;
		}
      
	}