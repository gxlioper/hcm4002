package com.hjw.wst.DTO;

import java.util.List;

public class PageReturnDTO {
	private int page;
	private int total;
	private int rp;
	private List rows;
	private List footer;	
	
	/*public PageReturnDTO() {
		// TODO Auto-generated constructor stub
	}*/
	
	public List getFooter() {
		return footer;
	}
	public void setFooter(List footer) {
		this.footer = footer;
	}
	public int getRp() {
		return rp;
	}
	public void setRp(int rp) {
		this.rp = rp;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
