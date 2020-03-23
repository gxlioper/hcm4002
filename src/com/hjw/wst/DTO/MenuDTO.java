package com.hjw.wst.DTO;

import java.util.List;

public class MenuDTO  implements Cloneable,java.io.Serializable{
    private static final long serialVersionUID = -3565567339406922273L;

    private String id;
    private String text;
    private String value;  //
    private boolean showcheck; //是否显示复选框
    private boolean complete; //是否异步获取，true 表示不异步
    private boolean isexpand;//是否展开
    private int checkstate; //复选框值，1表示选中，0表示不选中 2表示虚选
    private boolean hasChildren;//是否有子节点
    private List<MenuDTO> ChildNodes;
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isShowcheck() {
		return showcheck;
	}

	public void setShowcheck(boolean showcheck) {
		this.showcheck = showcheck;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public boolean isIsexpand() {
		return isexpand;
	}

	public void setIsexpand(boolean isexpand) {
		this.isexpand = isexpand;
	}

	public int getCheckstate() {
		return checkstate;
	}

	public void setCheckstate(int checkstate) {
		this.checkstate = checkstate;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public List<MenuDTO> getChildNodes() {
		return ChildNodes;
	}

	public void setChildNodes(List<MenuDTO> ChildNodes) {
		this.ChildNodes = ChildNodes;
	}
   
}
