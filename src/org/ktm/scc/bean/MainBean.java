package org.ktm.scc.bean;

import org.ktm.web.bean.FormBean;

public class MainBean extends FormBean {

    private String page;
    private String pageContent;

    public String getPageContent() {
        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
