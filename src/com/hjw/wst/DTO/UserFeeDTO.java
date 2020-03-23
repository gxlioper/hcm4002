package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

/*
 * Title:ϵͳ
 * Description: 
 *   Jun 8, 200710:49:43 AM
 *     webschoolcard
 *      com.synjones.wst.domain
 *
 * Copyright:   Copyright (c) 2011-2016
 * Company:     syntongs
 * author       yangm
 * version      3.0.0.0
 */

public class UserFeeDTO implements Cloneable, java.io.Serializable {
	private static final long serialVersionUID = -3565567339406922273L;
	private boolean flags=false;
	
	private String error="";

	private String username="";
	
	private String work_num="";// 姓名
	
	private String req_nums="";

	public boolean isFlags() {
		return flags;
	}

	public void setFlags(boolean flags) {
		this.flags = flags;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWork_num() {
		return work_num;
	}

	public void setWork_num(String work_num) {
		this.work_num = work_num;
	}

	public String getReq_nums() {
		return req_nums;
	}

	public void setReq_nums(String req_nums) {
		this.req_nums = req_nums;
	}


}
