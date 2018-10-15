package com.pedaily.yc.ycdialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.pedaily.yc.ycdialoglib.toast.ToastUtils;


public class TestActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView mTv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        mTv1 = (TextView) findViewById(R.id.tv_1);


        mTv1.setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_1:
                showPopupWindow();
                break;
            case R.id.tv_2:
                Toast.makeText(this,"吐司",Toast.LENGTH_SHORT).show();
                showDialogFragment();
                break;
            case R.id.tv_3:
                showToast();
                break;
            case R.id.tv_4:
                showSnackBar(v);
                break;
        }
    }

    private void showPopupWindow() {
        //创建对象
        PopupWindow popupWindow = new PopupWindow(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.view_pop_custom, null);
        //设置view布局
        popupWindow.setContentView(inflate);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置动画的方法
        popupWindow.setAnimationStyle(R.style.BottomDialog);
        //设置PopUpWindow的焦点，设置为true之后，PopupWindow内容区域，才可以响应点击事件
        popupWindow.setTouchable(true);
        //设置背景透明
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //点击空白处的时候让PopupWindow消失
        popupWindow.setOutsideTouchable(true);
        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键，默认是false
        popupWindow.setFocusable(false);
        //设置dismiss事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        boolean showing = popupWindow.isShowing();
        if (!showing){
            //show，并且可以设置位置
            popupWindow.showAsDropDown(mTv1);
        }
        //popupWindow.dismiss();
    }


    private void showDialogFragment() {
        CustomDialogFragment.showDialog(this);
    }


    private void showToast() {
        ToastUtils.showRoundRectToast("潇湘剑雨-杨充");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void showSnackBar(View v){
        Snackbar sb = Snackbar.make(v,"潇湘剑雨",Snackbar.LENGTH_LONG)
                .setAction("删除吗？", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击了"是吗？"字符串操作
                        ToastUtils.showRoundRectToast("逗比");
                    }
                })
                .setActionTextColor(Color.RED)
                .setText("杨充是个逗比")
                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        switch (event) {
                            case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
                            case Snackbar.Callback.DISMISS_EVENT_MANUAL:
                            case Snackbar.Callback.DISMISS_EVENT_SWIPE:
                            case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:
                                ToastUtils.showRoundRectToast("删除成功");
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_ACTION:
                                ToastUtils.showRoundRectToast("撤销了删除操作");
                                break;
                        }
                        Log.d("MainActivity","onDismissed");
                    }
                    @Override
                    public void onShown(Snackbar transientBottomBar) {
                        super.onShown(transientBottomBar);
                        Log.d("MainActivity","onShown");
                    }
                });
        sb.show();
        //sb.dismiss();
    }

}
