package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="data_migration_company")
public class DataMigrationCompany implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")
	 private long id;
	 
	 @Column(name="com_name")
	 private String com_name;

	 @Column(name="com_id")
	 private long com_id;
	 
	 @Column(name="com_id_car")
	 private long com_id_car;
	 
	 @Column(name="batch_id")
	 private long batch_id;
	 
	 @Column(name="batch_id_car")
	 private long batch_id_car;
	 
	 @Column(name="group_id")
	 private long group_id;
	 
	 @Column(name="group_id_car")
	 private long group_id_car;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCom_name() {
		return com_name;
	}

	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}

	public long getCom_id() {
		return com_id;
	}

	public void setCom_id(long com_id) {
		this.com_id = com_id;
	}

	public long getCom_id_car() {
		return com_id_car;
	}

	public void setCom_id_car(long com_id_car) {
		this.com_id_car = com_id_car;
	}

	public long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}

	public long getBatch_id_car() {
		return batch_id_car;
	}

	public void setBatch_id_car(long batch_id_car) {
		this.batch_id_car = batch_id_car;
	}

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public long getGroup_id_car() {
		return group_id_car;
	}

	public void setGroup_id_car(long group_id_car) {
		this.group_id_car = group_id_car;
	}
}
