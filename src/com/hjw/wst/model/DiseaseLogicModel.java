package com.hjw.wst.model;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.model
 * @Description: 疾病逻辑
 * @author: zr
 * @date: 2016年12月7日 上午9:42:17
 * @version V2.0.0.0
 */
public class DiseaseLogicModel implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 6218745149312156191L;

	private long id;
	private long disease_id;
	private String ids;
	private String logic_num;
	private String logic_name;
	private String logic_type;
	private String sex;
	private String age_min;
	private String age_max;
	private String li;
	private String isActive;
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;
	private long critical_flag;
	private String dep_id;
	private String item_num;
	//疾病知识
	private String disease_name;//疾病名称
	private String disease_type;//疾病类型
	private String disease_num;//疾病编码
	
	private String c_item_name;//收费项目名称
	private String e_item_name;//检查项目名称
	private String c_item_id;//收费项目Id
	private String web_Resource;
	private String disease_pinyin;
	
	private String itemList;
	private long itemId;
	private long limitCount; //项目上限
	
	private String ageMinMax;  //适应年龄
	private long logic_class;//单项阳性逻辑类型 0按检查项目结果，1按普通科室小结或影像检查按照科室保存的结果
	
	private String charging_item_code;
	private String item_name;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getCharging_item_code() {
		return charging_item_code;
	}

	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}

	public long getLogic_class() {
		return logic_class;
	}

	public void setLogic_class(long logic_class) {
		this.logic_class = logic_class;
	}

	public String getItem_num() {
		return item_num;
	}

	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getDisease_pinyin() {
		return disease_pinyin;
	}



	public void setDisease_pinyin(String disease_pinyin) {
		this.disease_pinyin = disease_pinyin;
	}



	public String getWeb_Resource() {
		return web_Resource;
	}



	public void setWeb_Resource(String web_Resource) {
		this.web_Resource = web_Resource;
	}



	public String getAge_min() {
		return age_min;
	}

	
	
	public String getDep_id() {
		return dep_id;
	}



	public void setDep_id(String dep_id) {
		this.dep_id = dep_id;
	}



	public void setAge_min(String age_min) {
		this.age_min = age_min;
	}

	public String getAge_max() {
		return age_max;
	}

	public void setAge_max(String age_max) {
		this.age_max = age_max;
	}

	public String getLi() {
		return li;
	}

	public void setLi(String li) {
		this.li = li;
	}

	public String getC_item_id() {
		return c_item_id;
	}

	public void setC_item_id(String c_item_id) {
		this.c_item_id = c_item_id;
	}

	public String getC_item_name() {
		return c_item_name;
	}

	public void setC_item_name(String c_item_name) {
		this.c_item_name = c_item_name;
	}

	public String getE_item_name() {
		return e_item_name;
	}

	public void setE_item_name(String e_item_name) {
		this.e_item_name = e_item_name;
	}

	public String getDisease_name() {
		return disease_name;
	}

	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}

	public String getDisease_type() {
		return disease_type;
	}

	public void setDisease_type(String disease_type) {
		this.disease_type = disease_type;
	}

	public String getDisease_num() {
		return disease_num;
	}

	public void setDisease_num(String disease_num) {
		this.disease_num = disease_num;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDisease_id() {
		return disease_id;
	}

	public void setDisease_id(long disease_id) {
		this.disease_id = disease_id;
	}

	public String getLogic_num() {
		return logic_num;
	}

	public void setLogic_num(String logic_num) {
		this.logic_num = logic_num;
	}

	public String getLogic_name() {
		return logic_name;
	}

	public void setLogic_name(String logic_name) {
		this.logic_name = logic_name;
	}

	public String getLogic_type() {
		return logic_type;
	}

	public void setLogic_type(String logic_type) {
		this.logic_type = logic_type;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public long getCritical_flag() {
		return critical_flag;
	}

	public void setCritical_flag(long critical_flag) {
		this.critical_flag = critical_flag;
	}



	public String getItemList() {
		return itemList;
	}



	public void setItemList(String itemList) {
		this.itemList = itemList;
	}



	public long getItemId() {
		return itemId;
	}



	public void setItemId(long itemId) {
		this.itemId = itemId;
	}



	public long getLimitCount() {
		return limitCount;
	}



	public void setLimitCount(long limitCount) {
		this.limitCount = limitCount;
	}



	public String getAgeMinMax() {
		return ageMinMax;
	}



	public void setAgeMinMax(String ageMinMax) {
		this.ageMinMax = ageMinMax;
	}

}
