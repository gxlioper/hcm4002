package com.hjw.Bean.HYTnew;

import com.google.gson.Gson;
import com.hjw.Bean.HYT.Questionnaire;

public class QuestionnaireSummary {

	private int id;
	private String name = "";
	private String phone = "";
	private String identity = "";
	private String code = "";
	private long createTime;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getIdentity() {
		return identity;
	}
	public String getCode() {
		return code;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public static void main(String[] args) {
		String body = "[{\"code\":\"156378247462393\",\"createTime\":1563825675000,\"id\":122,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156378281246998\",\"createTime\":1563826013000,\"id\":123,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156378286005770\",\"createTime\":1563826060000,\"id\":124,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156378289689060\",\"createTime\":1563826097000,\"id\":125,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156378703539957\",\"createTime\":1563830235000,\"id\":126,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156387441158788\",\"createTime\":1563917612000,\"id\":130,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156387449667871\",\"createTime\":1563917697000,\"id\":131,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156447090596079\",\"createTime\":1564514106000,\"id\":209,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156447174781551\",\"createTime\":1564514948000,\"id\":210,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156447475591276\",\"createTime\":1564517956000,\"id\":213,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156456000972675\",\"createTime\":1564603210000,\"id\":216,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156456252176998\",\"createTime\":1564605722000,\"id\":217,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"},{\"code\":\"156497273204767\",\"createTime\":1565015932000,\"id\":219,\"identity\":\"445281198410281258\",\"name\":\"陈少明\",\"phone\":\"13621792209\"}]";
		QuestionnaireSummary[] questionnaireSummarys = new Gson().fromJson(body, new QuestionnaireSummary[0].getClass());
		System.out.println(questionnaireSummarys[0].getName());
	}
}
