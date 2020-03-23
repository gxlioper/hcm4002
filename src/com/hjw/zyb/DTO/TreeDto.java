package com.hjw.zyb.DTO;

import java.util.List;

/**
 * tree树数据封装
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.DTO   
     * @Description:  
     * @author: yangm     
     * @date:   2017年12月1日 下午4:36:48   
     * @version V2.0.0.0
 */
public class TreeDto {
	private int id;
	private String text;
	private String state;
	private List<ZybOccuhazardfactorsDTO> children;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<ZybOccuhazardfactorsDTO> getChildren() {
		return children;
	}
	public void setChildren(List<ZybOccuhazardfactorsDTO> children) {
		this.children = children;
	}
	
	
}
