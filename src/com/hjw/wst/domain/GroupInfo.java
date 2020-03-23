package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "group_info")
public class GroupInfo implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name = "batch_id")
	private long batch_id;

	@Column(name = "group_num")
	private String group_num="";

	@Column(name = "group_name")
	private String group_name="";

	@Column(name = "start_date")
	private Date start_date;

	@Column(name = "end_date")
	private Date end_date;

	@Column(name = "sex")
	private String sex="";

	@Column(name = "min_age")
	private int min_age;

	@Column(name = "max_age")
	private int max_age;

	@Column(name = "is_Marriage")
	private String is_Marriage="";

	@Column(name = "posttion")
	private String posttion="";

	@Column(name = "discount")
	private double discount;

	@Column(name = "amount")
	private double amount;

	@Column(name = "group_index")
	private String group_index="";

	@Column(name = "person_team_amount")
	private double person_team_amount;

	@Column(name = "group_settlement_type")
	private String group_settlement_type="";

	@Column(name = "creater")
	private long creater;

	@Column(name = "create_time")
	private Date create_time;

	@Column(name = "updater")
	private long updater;

	@Column(name = "update_time")
	private Date update_time;

	@Column(name = "isActive")
	private String isActive="";
	
	@Column(name = "exam_indicator")
	private String exam_indicator="";//付费方式
	
	@Column(name = "cust_type_id")
	private String cust_type_id="";
	
	@Column(name = "con_num")
	private long con_num;
	
	@Column(name = "group_order")
	private int group_order;//分组顺序
	
	@Column(name = "maximum_amount")
	private double maximum_amount;  //分组最大金额限制
	
	public long getCon_num() {
		return con_num;
	}

	public void setCon_num(long con_num) {
		this.con_num = con_num;
	}

	public String getCust_type_id() {
		return cust_type_id;
	}

	public void setCust_type_id(String cust_type_id) {
		this.cust_type_id = cust_type_id;
	}

	public String getExam_indicator() {
		return exam_indicator;
	}

	public void setExam_indicator(String exam_indicator) {
		this.exam_indicator = exam_indicator;
	}

	public long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}

	public String getGroup_num() {
		return group_num;
	}

	public void setGroup_num(String group_num) {
		this.group_num = group_num;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getMin_age() {
		return min_age;
	}

	public void setMin_age(int min_age) {
		this.min_age = min_age;
	}

	public int getMax_age() {
		return max_age;
	}

	public void setMax_age(int max_age) {
		this.max_age = max_age;
	}

	public String getIs_Marriage() {
		return is_Marriage;
	}

	public void setIs_Marriage(String is_Marriage) {
		this.is_Marriage = is_Marriage;
	}

	public String getPosttion() {
		return posttion;
	}

	public void setPosttion(String posttion) {
		this.posttion = posttion;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getGroup_index() {
		return group_index;
	}

	public void setGroup_index(String group_index) {
		this.group_index = group_index;
	}

	public double getPerson_team_amount() {
		return person_team_amount;
	}

	public void setPerson_team_amount(double person_team_amount) {
		this.person_team_amount = person_team_amount;
	}

	public String getGroup_settlement_type() {
		return group_settlement_type;
	}

	public void setGroup_settlement_type(String group_settlement_type) {
		this.group_settlement_type = group_settlement_type;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGroup_order() {
		return group_order;
	}

	public double getMaximum_amount() {
		return maximum_amount;
	}

	public void setGroup_order(int group_order) {
		this.group_order = group_order;
	}

	public void setMaximum_amount(double maximum_amount) {
		this.maximum_amount = maximum_amount;
	}

}