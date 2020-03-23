package com.hjw.charge.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="medical_price_medical_item")
public class MedicalPriceMedicalItemCharge {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private	long	id;
	private	long	medical_price_id;
	private	String	medical_item_code;
	private	int	item_num;
	private	String	medical_type;
	private	long	creater;
	private	Date	create_date;
	private	long	updater;
	private	Date	update_date;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public long getMedical_price_id() {
		return medical_price_id;
	}
	public void setMedical_price_id(long medical_price_id) {
		this.medical_price_id = medical_price_id;
	}
	public String getMedical_item_code() {
		return medical_item_code;
	}
	public void setMedical_item_code(String medical_item_code) {
		this.medical_item_code = medical_item_code;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public String getMedical_type() {
		return medical_type;
	}
	public void setMedical_type(String medical_type) {
		this.medical_type = medical_type;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

}
