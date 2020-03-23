package com.hjw.wst.model;

import java.util.Date;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  导简单回收人
     * @author:zr  
     * @date:   2016年10月19日 下午11:28:12   
     * @version V2.0.0.0
 */
public class examInfoRecyclingGuidModel  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;//主键
	
	private long exam_info_id;//体检人信息id
	
	private long creater;//回收操作人
	
	private Date create_time;//回收时间
	
	private String exam_num;
    
    public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExam_info_id() {
		return exam_info_id;
	}

	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
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
	
}
