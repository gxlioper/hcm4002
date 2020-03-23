package com.hjw.wst.DTO;
/**
 * 
     * @Title:  圈存查询一体机系统3.0   
     * @Package com.synjones.wst.DTO   
     * @Description:    图片传递
     * @author: yujia     
     * @date:   2015-8-20 下午2:46:46   
     * @version V3.0.0.0
 */
public class ImgDTO implements java.io.Serializable{

	private static final long serialVersionUID = 7981418198529509708L;
	private String imgsrc;//图片地址
    private int selected ;//选择标记
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public int getSelected() {
		return selected;
	}
	public void setSelected(int selected) {
		this.selected = selected;
	}
}
