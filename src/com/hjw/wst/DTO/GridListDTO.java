package com.hjw.wst.DTO;

public class GridListDTO  implements Cloneable,java.io.Serializable{
    private static final long serialVersionUID = -3565567339406922273L;

//	private String display;
//	private String name;
	private String title;
	private String field;
	private int width;
	private boolean sortable;
	private String align;
	
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}

	/*public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
*/
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
