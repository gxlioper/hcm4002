package com.hjw.wst.model;

import java.util.Date;

public class QuestModel implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	private int titleID;

    private String titleName;
    
    private String supID;//所属ID
    
    private int Level; //级别
    
    private int seqNo; //排序号
    
    private String isVisable; //是否显示
    
    private String quest_sub_code; //问卷类型ID
    
    private String titleColumn;
    
    private String isMuslCheck;
    

	public String getIsMuslCheck() {
		return isMuslCheck;
	}

	public void setIsMuslCheck(String isMuslCheck) {
		this.isMuslCheck = isMuslCheck;
	}

	public String getTitleColumn() {
   		return titleColumn;
   	}

   	public void setTitleColumn(String titleColumn) {
   		this.titleColumn = titleColumn;
   	}

	public int getTitleID() {
		return titleID;
	}

	public void setTitleID(int titleID) {
		this.titleID = titleID;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getSupID() {
		return supID;
	}

	public void setSupID(String supID) {
		this.supID = supID;
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		Level = level;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public String getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(String isVisable) {
		this.isVisable = isVisable;
	}
	
	public String getQuest_sub_code() {
		return quest_sub_code;
	}

	public void setQuest_sub_code(String quest_sub_code) {
		this.quest_sub_code = quest_sub_code;
	}
	
	//-----------Menu表------------------

		public int MenuId;
		
		public int MenuTitleID;
		
		public String MenuTitle;
		
		public int MenuTitleFatherId;
		
		public String MenuTitleFather;
		
		public int MenuPageNum;

		public int MeunquestNum;
		
		public int MenuPagequestNum;
		
		public int MenuPageTatol; 
		
		public String MenuPageSubtitle;
		
		//public int seqNo;
		
		public int TableBcW1;
		
		public int TableBcW2;
		
		public int TableBcW3;
		
		public int TableBcH1;
		
		public int TableBcH2;
		
		public int tableNumW;
		
		public int tableTitleItemW;
		
		public int tableItemW;
		
		public int tableNumH;
		
		public int tableLineH;
		
		public int tableboder;
		
		public String tablebodercolor;
		
		public int w;
		
		public int w1;
		
		public int h;
		
		public int itemnumofpage;

		public int getMenuId() {
			return MenuId;
		}

		public void setMenuId(int menuId) {
			MenuId = menuId;
		}

		public int getMenuTitleID() {
			return MenuTitleID;
		}

		public void setMenuTitleID(int menuTitleID) {
			MenuTitleID = menuTitleID;
		}

		public String getMenuTitle() {
			return MenuTitle;
		}

		public void setMenuTitle(String menuTitle) {
			MenuTitle = menuTitle;
		}

		public int getMenuTitleFatherId() {
			return MenuTitleFatherId;
		}

		public void setMenuTitleFatherId(int menuTitleFatherId) {
			MenuTitleFatherId = menuTitleFatherId;
		}

		public String getMenuTitleFather() {
			return MenuTitleFather;
		}

		public void setMenuTitleFather(String menuTitleFather) {
			MenuTitleFather = menuTitleFather;
		}

		public int getMenuPageNum() {
			return MenuPageNum;
		}

		public void setMenuPageNum(int menuPageNum) {
			MenuPageNum = menuPageNum;
		}

		public int getMeunquestNum() {
			return MeunquestNum;
		}

		public void setMeunquestNum(int meunquestNum) {
			MeunquestNum = meunquestNum;
		}

		public int getMenuPagequestNum() {
			return MenuPagequestNum;
		}

		public void setMenuPagequestNum(int menuPagequestNum) {
			MenuPagequestNum = menuPagequestNum;
		}

		public int getMenuPageTatol() {
			return MenuPageTatol;
		}

		public void setMenuPageTatol(int menuPageTatol) {
			MenuPageTatol = menuPageTatol;
		}

		public String getMenuPageSubtitle() {
			return MenuPageSubtitle;
		}

		public void setMenuPageSubtitle(String menuPageSubtitle) {
			MenuPageSubtitle = menuPageSubtitle;
		}

		public int getTableBcW1() {
			return TableBcW1;
		}

		public void setTableBcW1(int tableBcW1) {
			TableBcW1 = tableBcW1;
		}

		public int getTableBcW2() {
			return TableBcW2;
		}

		public void setTableBcW2(int tableBcW2) {
			TableBcW2 = tableBcW2;
		}

		public int getTableBcW3() {
			return TableBcW3;
		}

		public void setTableBcW3(int tableBcW3) {
			TableBcW3 = tableBcW3;
		}

		public int getTableBcH1() {
			return TableBcH1;
		}

		public void setTableBcH1(int tableBcH1) {
			TableBcH1 = tableBcH1;
		}

		public int getTableBcH2() {
			return TableBcH2;
		}

		public void setTableBcH2(int tableBcH2) {
			TableBcH2 = tableBcH2;
		}

		public int getTableNumW() {
			return tableNumW;
		}

		public void setTableNumW(int tableNumW) {
			this.tableNumW = tableNumW;
		}

		public int getTableTitleItemW() {
			return tableTitleItemW;
		}

		public void setTableTitleItemW(int tableTitleItemW) {
			this.tableTitleItemW = tableTitleItemW;
		}

		public int getTableItemW() {
			return tableItemW;
		}

		public void setTableItemW(int tableItemW) {
			this.tableItemW = tableItemW;
		}

		public int getTableNumH() {
			return tableNumH;
		}

		public void setTableNumH(int tableNumH) {
			this.tableNumH = tableNumH;
		}

		public int getTableLineH() {
			return tableLineH;
		}

		public void setTableLineH(int tableLineH) {
			this.tableLineH = tableLineH;
		}

		public int getTableboder() {
			return tableboder;
		}

		public void setTableboder(int tableboder) {
			this.tableboder = tableboder;
		}

		public String getTablebodercolor() {
			return tablebodercolor;
		}

		public void setTablebodercolor(String tablebodercolor) {
			this.tablebodercolor = tablebodercolor;
		}

		public int getW() {
			return w;
		}

		public void setW(int w) {
			this.w = w;
		}

		public int getW1() {
			return w1;
		}

		public void setW1(int w1) {
			this.w1 = w1;
		}

		public int getH() {
			return h;
		}

		public void setH(int h) {
			this.h = h;
		}

		public int getItemnumofpage() {
			return itemnumofpage;
		}

		public void setItemnumofpage(int itemnumofpage) {
			this.itemnumofpage = itemnumofpage;
		}
		
		//-----------------style表------------------------
		public int partStyleId;
		
		public String partStyleTitle;
		
		public int isCheck;
		
		public int tCss;
		
		public String tlogo;
		
		public String timageInputIDTag;
		
		public String timageInputbutton;
		
		public String twidthInputCardID;
		
		public String twenxintishi;
		
		public String timageDatibuttion;
		
		public String timagesexn;
		
		public String timagesexlv;
		
		public int tableMainW0;
		
		public int tableMainH0;
		
		public int tableMainW;
		
		public int tableMainH1;
		
		public int tableMainH2;
		
		public int tableMainboder;
		
		public String tableMainbodercolor;
		
		public String Tlogintbackground;
		
		public String tbackground;

		public int getPartStyleId() {
			return partStyleId;
		}

		public void setPartStyleId(int partStyleId) {
			this.partStyleId = partStyleId;
		}

		public String getPartStyleTitle() {
			return partStyleTitle;
		}

		public void setPartStyleTitle(String partStyleTitle) {
			this.partStyleTitle = partStyleTitle;
		}

		public int getIsCheck() {
			return isCheck;
		}

		public void setIsCheck(int isCheck) {
			this.isCheck = isCheck;
		}

		public int gettCss() {
			return tCss;
		}

		public void settCss(int tCss) {
			this.tCss = tCss;
		}

		public String getTlogo() {
			return tlogo;
		}

		public void setTlogo(String tlogo) {
			this.tlogo = tlogo;
		}

		public String getTimageInputIDTag() {
			return timageInputIDTag;
		}

		public void setTimageInputIDTag(String timageInputIDTag) {
			this.timageInputIDTag = timageInputIDTag;
		}

		public String getTimageInputbutton() {
			return timageInputbutton;
		}

		public void setTimageInputbutton(String timageInputbutton) {
			this.timageInputbutton = timageInputbutton;
		}

		public String getTwidthInputCardID() {
			return twidthInputCardID;
		}

		public void setTwidthInputCardID(String twidthInputCardID) {
			this.twidthInputCardID = twidthInputCardID;
		}

		public String getTwenxintishi() {
			return twenxintishi;
		}

		public void setTwenxintishi(String twenxintishi) {
			this.twenxintishi = twenxintishi;
		}

		public String getTimageDatibuttion() {
			return timageDatibuttion;
		}

		public void setTimageDatibuttion(String timageDatibuttion) {
			this.timageDatibuttion = timageDatibuttion;
		}

		public String getTimagesexn() {
			return timagesexn;
		}

		public void setTimagesexn(String timagesexn) {
			this.timagesexn = timagesexn;
		}

		public String getTimagesexlv() {
			return timagesexlv;
		}

		public void setTimagesexlv(String timagesexlv) {
			this.timagesexlv = timagesexlv;
		}

		public int getTableMainW0() {
			return tableMainW0;
		}

		public void setTableMainW0(int tableMainW0) {
			this.tableMainW0 = tableMainW0;
		}

		public int getTableMainH0() {
			return tableMainH0;
		}

		public void setTableMainH0(int tableMainH0) {
			this.tableMainH0 = tableMainH0;
		}

		public int getTableMainW() {
			return tableMainW;
		}

		public void setTableMainW(int tableMainW) {
			this.tableMainW = tableMainW;
		}

		public int getTableMainH1() {
			return tableMainH1;
		}

		public void setTableMainH1(int tableMainH1) {
			this.tableMainH1 = tableMainH1;
		}

		public int getTableMainH2() {
			return tableMainH2;
		}

		public void setTableMainH2(int tableMainH2) {
			this.tableMainH2 = tableMainH2;
		}

		public int getTableMainboder() {
			return tableMainboder;
		}

		public void setTableMainboder(int tableMainboder) {
			this.tableMainboder = tableMainboder;
		}

		public String getTableMainbodercolor() {
			return tableMainbodercolor;
		}

		public void setTableMainbodercolor(String tableMainbodercolor) {
			this.tableMainbodercolor = tableMainbodercolor;
		}

		public String getTlogintbackground() {
			return Tlogintbackground;
		}

		public void setTlogintbackground(String tlogintbackground) {
			Tlogintbackground = tlogintbackground;
		}

		public String getTbackground() {
			return tbackground;
		}

		public void setTbackground(String tbackground) {
			this.tbackground = tbackground;
		}
		
		//----------------item表------------------------
		
		public int ItemId;
		
		public String ItemName;
		
		public String supItemId;
		
		public int Itemlevel;
		
		//public int seqNo;
		
		//public int titleId;
		
		//public String isVisable;
		
		public String isMulSel;
		
		public String linkNo;
		
		public String textUnit;
		
		public String linkItem;
		
		public String linksubItem;
		
		public String ItemCode;
		
		public String Sex;
		
		public int IsDefault;
		
		private String defaultResult;
		
		private String itemPinYin;
		

		public String getItemPinYin() {
			return itemPinYin;
		}

		public void setItemPinYin(String itemPinYin) {
			this.itemPinYin = itemPinYin;
		}

		public String getDefaultResult() {
			return defaultResult;
		}

		public void setDefaultResult(String defaultResult) {
			this.defaultResult = defaultResult;
		}

		public int getItemId() {
			return ItemId;
		}

		public void setItemId(int itemId) {
			ItemId = itemId;
		}

		public String getItemName() {
			return ItemName;
		}

		public void setItemName(String itemName) {
			ItemName = itemName;
		}

		public String getSupItemId() {
			return supItemId;
		}

		public void setSupItemId(String supItemId) {
			this.supItemId = supItemId;
		}

		public int getItemlevel() {
			return Itemlevel;
		}

		public void setItemlevel(int itemlevel) {
			Itemlevel = itemlevel;
		}

		public String getIsMulSel() {
			return isMulSel;
		}

		public void setIsMulSel(String isMulSel) {
			this.isMulSel = isMulSel;
		}

		public String getLinkNo() {
			return linkNo;
		}

		public void setLinkNo(String linkNo) {
			this.linkNo = linkNo;
		}

		public String getTextUnit() {
			return textUnit;
		}

		public void setTextUnit(String textUnit) {
			this.textUnit = textUnit;
		}

		public String getLinkItem() {
			return linkItem;
		}

		public void setLinkItem(String linkItem) {
			this.linkItem = linkItem;
		}

		public String getLinksubItem() {
			return linksubItem;
		}

		public void setLinksubItem(String linksubItem) {
			this.linksubItem = linksubItem;
		}

		public String getItemCode() {
			return ItemCode;
		}

		public void setItemCode(String itemCode) {
			ItemCode = itemCode;
		}

		public String getSex() {
			return Sex;
		}

		public void setSex(String sex) {
			Sex = sex;
		}

		public int getIsDefault() {
			return IsDefault;
		}

		public void setIsDefault(int isDefault) {
			IsDefault = isDefault;
		}
		
		//------------------record表----------------
		private int recId;//记录ID
		
		private String peId;//体检编号
		
		private Date inDate;//开始体检时间
		
		private Date tranDate;//上传时间
		
		private long traner;//上传人员
		
		private String tranFlag;//0：未上传；1：已上传
		
		private Date checkDate;//审核时间
		
		private long checker;//审核人员
		
		private String delFlag;//1：已删除
		
		private String custId;
		
		private int downFlag;//下载标识
		
		//private String quest_sub_code;//量表代码
		
		private int score;//总分数
		
		private String appraise;//评估内容

		
		public int getRecId() {
			return recId;
		}

		public void setRecId(int recId) {
			this.recId = recId;
		}

		public String getPeId() {
			return peId;
		}

		public void setPeId(String peId) {
			this.peId = peId;
		}

		public Date getInDate() {
			return inDate;
		}

		public void setInDate(Date inDate) {
			this.inDate = inDate;
		}

		public Date getTranDate() {
			return tranDate;
		}

		public void setTranDate(Date tranDate) {
			this.tranDate = tranDate;
		}

		public long getTraner() {
			return traner;
		}

		public void setTraner(long traner) {
			this.traner = traner;
		}

		public String getTranFlag() {
			return tranFlag;
		}

		public void setTranFlag(String tranFlag) {
			this.tranFlag = tranFlag;
		}

		public Date getCheckDate() {
			return checkDate;
		}

		public void setCheckDate(Date checkDate) {
			this.checkDate = checkDate;
		}

		public long getChecker() {
			return checker;
		}

		public void setChecker(long checker) {
			this.checker = checker;
		}

		public String getDelFlag() {
			return delFlag;
		}

		public void setDelFlag(String delFlag) {
			this.delFlag = delFlag;
		}

		public String getCustId() {
			return custId;
		}

		public void setCustId(String custId) {
			this.custId = custId;
		}

		public int getDownFlag() {
			return downFlag;
		}

		public void setDownFlag(int downFlag) {
			this.downFlag = downFlag;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public String getAppraise() {
			return appraise;
		}

		public void setAppraise(String appraise) {
			this.appraise = appraise;
		}

		//-------------List表-----------------------
		private int questID;
		
		private String recordId;
		
		//private String peId;
		
		private String resultId;
		
		private String questResult;
		
		private String resultId_itemID;
		
		private String resultId_itemID1;
		
		private String questResult_itemID;
		
		private String itemTitleID;
		
		private int itemIsMulsel;
		
		private String itemtextUnit;
		
		private String resultId_itemName;
		
		private String resultId_itemName1;
		
		private String questResult_itemName;

		public int getQuestID() {
			return questID;
		}

		public void setQuestID(int questID) {
			this.questID = questID;
		}

		public String getRecordId() {
			return recordId;
		}

		public void setRecordId(String recordId) {
			this.recordId = recordId;
		}

		public String getResultId() {
			return resultId;
		}

		public void setResultId(String resultId) {
			this.resultId = resultId;
		}

		public String getQuestResult() {
			return questResult;
		}

		public void setQuestResult(String questResult) {
			this.questResult = questResult;
		}

		public String getResultId_itemID() {
			return resultId_itemID;
		}

		public void setResultId_itemID(String resultId_itemID) {
			this.resultId_itemID = resultId_itemID;
		}

		public String getResultId_itemID1() {
			return resultId_itemID1;
		}

		public void setResultId_itemID1(String resultId_itemID1) {
			this.resultId_itemID1 = resultId_itemID1;
		}

		public String getQuestResult_itemID() {
			return questResult_itemID;
		}

		public void setQuestResult_itemID(String questResult_itemID) {
			this.questResult_itemID = questResult_itemID;
		}

		public String getItemTitleID() {
			return itemTitleID;
		}

		public void setItemTitleID(String itemTitleID) {
			this.itemTitleID = itemTitleID;
		}

		public int getItemIsMulsel() {
			return itemIsMulsel;
		}

		public void setItemIsMulsel(int itemIsMulsel) {
			this.itemIsMulsel = itemIsMulsel;
		}

		public String getItemtextUnit() {
			return itemtextUnit;
		}

		public void setItemtextUnit(String itemtextUnit) {
			this.itemtextUnit = itemtextUnit;
		}

		public String getResultId_itemName() {
			return resultId_itemName;
		}

		public void setResultId_itemName(String resultId_itemName) {
			this.resultId_itemName = resultId_itemName;
		}

		public String getResultId_itemName1() {
			return resultId_itemName1;
		}

		public void setResultId_itemName1(String resultId_itemName1) {
			this.resultId_itemName1 = resultId_itemName1;
		}

		public String getQuestResult_itemName() {
			return questResult_itemName;
		}

		public void setQuestResult_itemName(String questResult_itemName) {
			this.questResult_itemName = questResult_itemName;
		}
		
		//体检编号
		private String exam_num;

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}
		
		//-----------------quest_dict_list表-------------------------
		//private String quest_sub_code;
		
		private String quest_code;
		
		private String quest_sub_name;
		
		private String class_name;
		
		public String getClass_name() {
			return class_name;
		}

		public void setClass_name(String class_name) {
			this.class_name = class_name;
		}

		public String getQuest_code() {
			return quest_code;
		}

		public void setQuest_code(String quest_code) {
			this.quest_code = quest_code;
		}

		public String getQuest_sub_name() {
			return quest_sub_name;
		}

		public void setQuest_sub_name(String quest_sub_name) {
			this.quest_sub_name = quest_sub_name;
		}
		
		private String dep_id;


		public String getDep_id() {
			return dep_id;
		}

		public void setDep_id(String dep_id) {
			this.dep_id = dep_id;
		}
		
		private String startdate; //起始日期
		
		private String enddate; //截止日期
		
		private String company; //工作单位
		
		private String workshop;//工作部门
		
		private String worktype;//工作种类
		
		private String sc_classname;//放射种类


		public String getStartdate() {
			return startdate;
		}

		public void setStartdate(String startdate) {
			this.startdate = startdate;
		}

		public String getEnddate() {
			return enddate;
		}

		public void setEnddate(String enddate) {
			this.enddate = enddate;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getWorkshop() {
			return workshop;
		}

		public void setWorkshop(String workshop) {
			this.workshop = workshop;
		}

		public String getWorktype() {
			return worktype;
		}

		public void setWorktype(String worktype) {
			this.worktype = worktype;
		}

		public String getSc_classname() {
			return sc_classname;
		}

		public void setSc_classname(String sc_classname) {
			this.sc_classname = sc_classname;
		}
		
		

}