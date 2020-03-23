package com.hjw.wst.action;

import java.sql.SQLException;

import com.hjw.util.DESMessage;
import com.hjw.util.FormatTransfer;
import com.hjw.util.XORBinary;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: yangm     
     * @date:   2016年8月18日 下午3:17:36   
     * @version V2.0.0.0
 */

public class CardAction extends BaseAction {

	private static final long serialVersionUID = -4395254787469394208L;

	private String cardId;// 物理卡号

	private String cardNO;// 会员卡号

	private String cardconn;// 0快内容

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public String getCardconn() {
		return cardconn;
	}

	public void setCardconn(String cardconn) {
		this.cardconn = cardconn;
	}

	/**
	 * 方案管理 340获取卡片密钥
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String getCardKey() throws WebException, SQLException {
		this.message = "error-错误";
		if ((this.cardId != null) && (this.cardId.trim().length() == 8)) {
			this.message = DESMessage.getStrKey(this.cardId.trim());
		} else {
			this.message = "error-卡号位数不对";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	     * @Title: getCardNOHex   341 
	     * @Description: 获取16进制的1扇区1块内容   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCardNOHex() throws WebException, SQLException {
		this.message = "error-错误";
		if (this.getCardNO() == null) {
			this.message = "error-会员卡号错误";
		} else {
			if (this.getCardNO().trim().length() < 10) {
				for (int i = 0; i < (10 - this.getCardNO().trim().length());) {
					this.setCardNO("0" + "" + this.getCardNO().trim());
				}
			} else if (this.getCardNO().trim().length() > 10) {
				this.setCardNO(this.getCardNO().trim().substring(0, 10));
			}
			
			String str = FormatTransfer.str2HexStr(this.getCardNO()).trim();			
			str = str+"000000000000";
			String sxor = XORBinary.XOR(str, 15);
			str = str+""+sxor;
			this.message="ok-"+str;
			
		}
		this.outJsonStrResult(this.message);
		return NONE;

	}

	/**
	 * 获取卡片会员卡号 342
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String getCardNOForConn() throws WebException, SQLException {
		this.message = "error-错误";
		if ((this.getCardconn()!= null) || (this.getCardconn().trim().length() ==64)) {
			String str = FormatTransfer.hexStr2Str(this.getCardconn().trim().substring(0, 20));
			this.message = "ok-"+str;
		} else {
			this.message = "error-获取扇区块内容错误";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

}
