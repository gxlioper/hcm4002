package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 敏感词 词库
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: dq     
     * @date:   2017年7月21日 上午11:28:45   
     * @version V2.0.0.0
 */
@Entity
@Table(name="sensitive_words_lib")
public class SensitiveWordsLib implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="sensitive_type")
	private String sensitive_type;  //敏感词类型  1表示性别不一致敏感词  2表示特殊重要疾病敏感词
	
	@Column(name="sensitive_sex")
	private String sensitive_sex;  //适用性别
	
	@Column(name="sensitive_content")
	private String sensitive_content;//敏感词内容
	
	@Column(name="is_active")
	private String is_active;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSensitive_type() {
		return sensitive_type;
	}

	public void setSensitive_type(String sensitive_type) {
		this.sensitive_type = sensitive_type;
	}

	public String getSensitive_sex() {
		return sensitive_sex;
	}

	public void setSensitive_sex(String sensitive_sex) {
		this.sensitive_sex = sensitive_sex;
	}

	public String getSensitive_content() {
		return sensitive_content;
	}

	public void setSensitive_content(String sensitive_content) {
		this.sensitive_content = sensitive_content;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
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
}
