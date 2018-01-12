package com.pedaily.yc.ycdialog;

/**
 * Created by yc on 2018/1/10.
 */

public class DialogBean {

    private String logo;
    private String name;
    private String title;

    public DialogBean(String logo, String name, String title) {
        this.logo = logo;
        this.name = name;
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
