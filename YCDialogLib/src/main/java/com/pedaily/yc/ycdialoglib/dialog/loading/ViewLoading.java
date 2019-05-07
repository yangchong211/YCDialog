package com.pedaily.yc.ycdialoglib.dialog.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.pedaily.yc.ycdialoglib.R;



/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2016/06/4
 *     desc  : 全局加载弹窗
 *     revise:
 * </pre>
 */
public class ViewLoading extends Dialog {

    private static ViewLoading loadDialog;
    private boolean canNotCancel;
    private final Animation animation;

    private ViewLoading(Context context , String content , boolean canNotCancel) {
        super(context, R.style.Loading);
        this.canNotCancel = canNotCancel;
        // 加载布局
        if(content!=null && content.length()>0){
            setContentView(R.layout.layout_dialog_loading);
            TextView message = (TextView) findViewById(R.id.message);
            message.setText(content);
        }else {
            setContentView(R.layout.layout_dialog_loaded);
        }
        ImageView progressImageView = (ImageView) findViewById(R.id.iv_image);
        //创建旋转动画
        animation = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        //动画的重复次数
        animation.setRepeatCount(10);
        //设置为true，动画转化结束后被应用
        animation.setFillAfter(true);
        //开始动画
        progressImageView.startAnimation(animation);
        // 设置Dialog参数
        Window window = getWindow();
        if(window!=null){
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        if (window!=null){
            window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onStop() {
        super.onStart();
        Window window = getWindow();
        if (window!=null){
            window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //Log.e("ViewLoading","onDetachedFromWindow");
        //注意需要销毁动画
        if (animation!=null){
            animation.cancel();
        }
        //onDetachedFromWindow  比 dismiss 方法中的 finally 先执行
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canNotCancel) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    public static void show(Context context) {
        show(context,"");
    }

    public static void show(Context context, String message) {
        show(context, message,false);
    }

    /**
     * 展示加载窗
     * @param context               上下文
     * @param message               内容
     * @param isCancel              是否可以取消
     */
    public static void show(Context context, String message, boolean isCancel) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new ViewLoading(context, message ,isCancel);
        loadDialog.show();
    }


    /**
     * 销毁加载窗
     * @param context               上下文
     */
    public static void dismiss(Context context) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    loadDialog = null;
                    return;
                }
            }
            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
                if (loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        loadDialog = null;
                        return;
                    }
                }
                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadDialog = null;
        } finally {
            //Log.e("ViewLoading","finally");
        }
    }

}
