package com.chenkh.media.bean;

public class Page {

    public boolean paging;
    public int showpage;
    public int pageSize;

    public boolean isPaging() {
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
    }

    public int getShowpage() {
        return showpage;
    }

    public void setShowpage(int showpage) {
        this.showpage = showpage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
