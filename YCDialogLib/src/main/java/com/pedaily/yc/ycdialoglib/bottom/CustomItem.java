package com.pedaily.yc.ycdialoglib.bottom;

import android.graphics.drawable.Drawable;
/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/5/2
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CustomItem {
    private int id;
    private String title;
    private Drawable icon;

    public CustomItem() {}

    public CustomItem(int id, String title, Drawable icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
