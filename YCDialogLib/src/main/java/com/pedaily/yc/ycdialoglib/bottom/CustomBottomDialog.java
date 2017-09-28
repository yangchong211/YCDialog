package com.pedaily.yc.ycdialoglib.bottom;

import android.content.Context;
import android.support.v7.widget.OrientationHelper;

import java.util.List;
/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/5/2
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CustomBottomDialog {

    private CustomDialog customDialog;
    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
    public static final int VERTICAL = OrientationHelper.VERTICAL;

    public CustomBottomDialog(Context context) {
        customDialog = new CustomDialog(context);
    }

    public CustomBottomDialog title(String title) {
        customDialog.title(title);
        return this;
    }

    public CustomBottomDialog title(int title) {
        customDialog.title(title);
        return this;
    }

    public CustomBottomDialog setCancel(boolean isShow , String text) {
        customDialog.setCancel(isShow, text);
        return this;
    }

    public CustomBottomDialog background(int res) {
        customDialog.background(res);
        return this;
    }

    public CustomBottomDialog inflateMenu(int menu, OnItemClickListener onItemClickListener) {
        customDialog.inflateMenu(menu, onItemClickListener);
        return this;
    }

    public CustomBottomDialog layout(int layout) {
        customDialog.layout(layout);
        return this;
    }

    public CustomBottomDialog orientation(int orientation) {
        customDialog.orientation(orientation);
        return this;
    }

    public CustomBottomDialog addItems(List<CustomItem> items, OnItemClickListener onItemClickListener) {
        customDialog.addItems(items, onItemClickListener);
        return this;
    }

    public CustomBottomDialog itemClick(OnItemClickListener listener) {
        customDialog.setItemClick(listener);
        return this;
    }

    public void show() {
        customDialog.show();
    }

}
