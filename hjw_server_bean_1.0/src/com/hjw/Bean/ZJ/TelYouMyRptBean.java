package com.hjw.Bean.ZJ;

import java.util.ArrayList;

import java.util.List;

public class TelYouMyRptBean {
	private BodyMsg bodyMsg = new BodyMsg();
	private List<RptsMsg> rptsMsg= new ArrayList<RptsMsg>();
    private long id;    
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BodyMsg getBodyMsg() {
		return bodyMsg;
	}

	public void setBodyMsg(BodyMsg bodyMsg) {
		this.bodyMsg = bodyMsg;
	}

	public List<RptsMsg> getRptsMsg() {
		return rptsMsg;
	}

	public void setRptsMsg(List<RptsMsg> rptsMsg) {
		this.rptsMsg = rptsMsg;
	}

	
}
