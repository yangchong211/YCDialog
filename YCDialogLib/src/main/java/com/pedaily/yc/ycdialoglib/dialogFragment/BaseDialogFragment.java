package com.pedaily.yc.ycdialoglib.dialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.pedaily.yc.ycdialoglib.R;
import com.pedaily.yc.ycdialoglib.toast.ToastUtils;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/8/9
 *     desc  : 自定义布局弹窗DialogFragment
 *     revise:
 * </pre>
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private static final String TAG = "base_bottom_dialog";
    private static final float DEFAULT_DIM = 0.2f;
    private static Dialog dialog;
    private Local local = Local.BOTTOM;
    public enum Local {
        TOP, CENTER, BOTTOM
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialog = getDialog();
        if(dialog!=null){
            if(dialog.getWindow()!=null){
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            }
            dialog.setCanceledOnTouchOutside(getCancelOutside());
            dialog.setCancelable(false);
        }
        View v = inflater.inflate(getLayoutRes(), container, false);
        bindView(v);
        return v;
    }

    /**
     * 获取布局资源文件
     * @return              布局资源文件id值
     */
    @LayoutRes
    public abstract int getLayoutRes();

    /**
     * 绑定
     * @param v             view
     */
    public abstract void bindView(View v);

    /**
     * 开始展示
     */
    @Override
    public void onStart() {
        super.onStart();
        if(dialog==null){
            dialog = getDialog();
        }
        Window window = dialog.getWindow();
        if(window!=null){
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = getDimAmount();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mListener!=null){
            mListener.listener();
        }
    }

    /**
     * 获取弹窗高度
     * @return          int类型值
     */
    public int getHeight() {
        return -1;
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    public boolean getCancelOutside() {
        return true;
    }

    public String getFragmentTag() {
        return TAG;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public void show(FragmentManager fragmentManager) {
        if(fragmentManager!=null){
            show(fragmentManager, getFragmentTag());
        }else {
            ToastUtils.showToast("需要设置setFragmentManager");
        }
    }

    /**
     * 一定要销毁dialog，设置为null，防止内存泄漏
     * GitHub地址：https://github.com/yangchong211
     * 如果可以，欢迎star
     */
    public static void dismissDialog(){
        if(dialog!=null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
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
