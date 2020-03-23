package com.hjw.Bean.YXKD;

public class ResToken {
	public String res = "";
	public ResTokenData Data = new ResTokenData();
	public ResError error = new ResError();

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public ResTokenData getData() {
		return Data;
	}

	public void setData(ResTokenData data) {
		Data = data;
	}

	public ResError getError() {
		return error;
	}

	public void setError(ResError error) {
		this.error = error;
	}

}
