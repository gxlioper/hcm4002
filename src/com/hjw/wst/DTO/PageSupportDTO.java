package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

public class PageSupportDTO implements Cloneable,java.io.Serializable {
	private static final long serialVersionUID = 1423864541338511512L;
	private int page;
	private int total;
	private int rp;
	private List rows = new ArrayList();
	
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
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	

}
