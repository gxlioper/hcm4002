package com.hjw.wst.DTO;

public class GridListColumDTO  implements Cloneable,java.io.Serializable{
    private static final long serialVersionUID = -3565567339406922273L;

	private String field;
	private String title;
	private int width;
	private boolean sortable;
	private String align;
	
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	

	public String getField() {
		return field;
	}



	public void setField(String field) {
		this.field = field;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean getSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}
}
