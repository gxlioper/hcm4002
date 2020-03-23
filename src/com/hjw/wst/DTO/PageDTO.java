package com.hjw.wst.DTO;

import com.hjw.util.CommSet;


/*
 * Title:
 * Description:
 * May 16, 2006
 * screenObject
 * com.synjones.touchscreen.dto
 *
 * Copyright: Copyright (c) 2005
 * Company: synjones
 * author yangm
 * version 2.0
 */

public class PageDTO {
    private int pageCount = 0; //

    private int pageNum = 1; //

    private int pageSize = CommSet.page_PageSize; //
    
    private int pageTotal;

    private boolean firstPage = false; //

    private boolean proPage = false; //

    private boolean nextPage = false; //

    private boolean tailPage = false; //

    /**
     * @return Returns the firstPage.
     */
    public boolean isFirstPage() {
        return firstPage;
    }

    /**
     * @param firstPage
     *            The firstPage to set.
     */
    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    /**
     * @return Returns the nextPage.
     */
    public boolean isNextPage() {
        return nextPage;
    }

    /**
     * @param nextPage
     *            The nextPage to set.
     */
    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * @return Returns the pageCount.
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * @param pageCount
     *            The pageCount to set.
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @return Returns the pageNum.
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * @param pageNum
     *            The pageNum to set.
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * @return Returns the pageSize.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            The pageSize to set.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return Returns the proPage.
     */
    public boolean isProPage() {
        return proPage;
    }

    /**
     * @param proPage
     *            The proPage to set.
     */
    public void setProPage(boolean proPage) {
        this.proPage = proPage;
    }

    /**
     * @return Returns the tailPage.
     */
    public boolean isTailPage() {
        return tailPage;
    }

    /**
     * @param tailPage
     *            The tailPage to set.
     */
    public void setTailPage(boolean tailPage) {
        this.tailPage = tailPage;
    }

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
}
