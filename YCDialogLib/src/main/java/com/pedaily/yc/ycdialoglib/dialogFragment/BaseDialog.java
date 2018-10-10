package com.pedaily.yc.ycdialoglib.dialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;

import com.pedaily.yc.ycdialoglib.R;
import com.pedaily.yc.ycdialoglib.toast.ToastUtils;


/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2018/2/10
 *     desc  : 这是一个dialog的基类
 *     revise: 注意：这个初始化布局是在创建对象的时候进行的
 * </pre>
 */
public abstract class BaseDialog extends DialogFragment {

    private static final String TAG = "base_bottom_dialog";
    private Local local = Local.BOTTOM;
    private final View v;
    private int width = 0;
    public enum Local {
        TOP, CENTER, BOTTOM
    }

    private static final float DEFAULT_DIM = 0.2f;


    public BaseDialog() {
        v = View.inflate(ToastUtils.getApp(),getLayoutRes(),null);
        bindView(v);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(local == Local.BOTTOM){
            setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
        }else if(local == Local.CENTER){
            setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CenterDialog);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getDialog()!=null){
            if(getDialog().getWindow()!=null){
                getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
            getDialog().setCanceledOnTouchOutside(getCancelOutside());
            getDialog().setCancelable(true);
            getDialog().setCanceledOnTouchOutside(true);
        }
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params ;
        if (window != null) {
            params = window.getAttributes();
            params.dimAmount = getDimAmount();
            if (getWidth() > 0){
                params.width = getWidth();
            }else {
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
            }
            if (getHeight() > 0) {
                params.height = getHeight();
            } else {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            if (local == Local.TOP) {
                params.gravity = Gravity.TOP;
            } else if (local == Local.CENTER) {
                params.gravity = Gravity.CENTER;
            } else {
                params.gravity = Gravity.BOTTOM;
            }
            window.setAttributes(params);
        }
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void bindView(View v);

    public int getHeight() {
        return -1;
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    public boolean getCancelOutside() {
        return false;
    }

    public String getFragmentTag() {
        return TAG;
    }

    public void dialogClose() {
        Dialog dialog = getDialog();
        if (dialog != null){
            dialog.dismiss();
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void show(FragmentManager fragmentManager) {
        if(fragmentManager!=null){
            show(fragmentManager, getFragmentTag());
        }else {
            Log.e("show","需要设置setFragmentManager");
            throw new NullPointerException("需要设置setFragmentManager");
        }
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mListener!=null){
            mListener.listener();
        }
        if(v!=null){
             ViewParent vp= v.getParent();
                if(vp!=null){
                    ((ViewGroup)vp).removeView(v);
                }
        }
    }

    public onLoadFinishListener mListener;
    public void setLoadFinishListener(onLoadFinishListener listener){
        mListener = listener;
    }
    public interface onLoadFinishListener{
        void listener();
    }


}
