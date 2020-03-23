package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "quest_dict_Item")
public class QuestDictItem implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private int ItemId;

	    @Column(name = "ItemName")
		private String ItemName;
	    
	    @Column(name = "supItemId")
		private String supItemId;

	    @Column(name = "Itemlevel")
		private int Itemlevel;
	    
	    @Column(name = "seqNo")
		private int seqNo;
	    
	    @Column(name = "titleId")
		private int titleId;
	    
	    @Column(name = "isVisable")
		private String isVisable;
	    
	    @Column(name = "isMulSel")
		private String isMulSel;
		
		@Column(name = "linkNo")
		private String linkNo;
		 
		@Column(name = "textUnit")
		private String textUnit;
		
		@Column(name = "linkItem")
		private String linkItem;
		
		@Column(name = "linksubItem")
		private String linksubItem;
		
		@Column(name = "ItemCode")
		private String ItemCode;
		
		@Column(name = "Sex")
		private String Sex;
		 
		@Column(name = "IsDefault")
		private int IsDefault;
		
		@Column(name = "defaultResult")
		private String defaultResult;
		
		
		@Column(name = "itemPinYin")
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

		public int getSeqNo() {
			return seqNo;
		}

		public void setSeqNo(int seqNo) {
			this.seqNo = seqNo;
		}

		public int getTitleId() {
			return titleId;
		}

		public void setTitleId(int titleId) {
			this.titleId = titleId;
		}

		public String getIsVisable() {
			return isVisable;
		}

		public void setIsVisable(String isVisable) {
			this.isVisable = isVisable;
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
		

	    
}
