package com.hjw.Bean.HYTnew;

import java.util.List;

import com.google.gson.Gson;

public class QuestionnaireDetail {

	private long createTime;
	private int id;
	private String code = "";
	private String name = "";
	private String userId = "";
	private UserInfo userInfo;
	List<CategoryList> categoryList;
	
	public String getCode() {
		return code;
	}
	public long getCreateTime() {
		return createTime;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUserId() {
		return userId;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<CategoryList> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<CategoryList> categoryList) {
		this.categoryList = categoryList;
	}
	public static void main(String[] args) {
		String body = "{\"categoryList\":[{\"code\":\"qscg_1563778821694\",\"id\":7,\"name\":\"基本信息\",\"questionnaireList\":[{\"categoryCode\":\"qscg_1563778821694\",\"code\":\"1563778973596\",\"id\":11,\"institutionCode\":\"SHRJ0355001\",\"name\":\"您的职业\",\"required\":\"1\",\"type\":\"1\",\"valueName\":\"G：自由职业\"},{\"categoryCode\":\"qscg_1563778821694\",\"code\":\"1563779044603\",\"id\":12,\"institutionCode\":\"SHRJ0355001\",\"name\":\"您的文化程度\",\"required\":\"1\",\"type\":\"1\",\"valueName\":\"D：高中及中专\"}]},{\"code\":\"qscg_1563778835676\",\"id\":8,\"name\":\"健康病史\",\"questionnaireList\":[{\"categoryCode\":\"qscg_1563778835676\",\"code\":\"1563779112117\",\"id\":13,\"institutionCode\":\"SHRJ0355001\",\"name\":\"您是否已怀孕(包含可能怀孕)或正在哺乳？近3-6个月您是否有怀孕的打算?\",\"required\":\"1\",\"type\":\"1\",\"valueName\":\"B：近3-6个月有怀孕的打算\"}]}],\"code\":\"156378281246998\",\"createTime\":1563826013000,\"id\":123,\"name\":\"陈少明\",\"userId\":\"2adf0ddc-bbb1-4d8d-99bc-96487cc810bf\",\"userInfo\":{\"age\":\"34\",\"gender\":1,\"identity\":\"445281198410281258\",\"marital\":1,\"mobile\":\"13621792209\",\"name\":\"陈少明\"}}";
		QuestionnaireDetail questionnaireDetail = new Gson().fromJson(body, QuestionnaireDetail.class);
		System.out.println(questionnaireDetail.getUserInfo().getName());
		System.out.println(questionnaireDetail.getCategoryList().get(0).getQuestionnaireList().get(0).getName());
	}
}
