package com.hjw.Bean.YXKD;

public class ResCard {
	public String res = "";
	public ResCardObject data = new ResCardObject();
	public ResError error = new ResError();

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public ResCardObject getData() {
		return data;
	}

	public void setData(ResCardObject data) {
		this.data = data;
	}

	public ResError getError() {
		return error;
	}

	public void setError(ResError error) {
		this.error = error;
	}

}
