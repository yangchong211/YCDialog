package com.pedaily.yc.ycdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.pedaily.yc.ycdialoglib.ToastUtil;
import com.pedaily.yc.ycdialoglib.bottom.BottomDialog;
import com.pedaily.yc.ycdialoglib.bottom.CustomBottomDialog;
import com.pedaily.yc.ycdialoglib.bottom.CustomItem;
import com.pedaily.yc.ycdialoglib.bottom.OnItemClickListener;
import com.pedaily.yc.ycdialoglib.selector.CustomSelectDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/6/21
 * 描    述：主页面
 * 修订历史：
 * ================================================
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
        findViewById(R.id.tv_6).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_1:
                showCustomDialog();
                break;
            case R.id.tv_2:
                ToastUtil.showToast(this,"自定义吐司");
                break;
            case R.id.tv_3:
                ToastUtil.showDelete(this,0);
                break;
            case R.id.tv_4:
                showCustomBottomDialog();
                break;
            case R.id.tv_5:
                showDialog5();
                break;
            case R.id.tv_6:

                break;
            case R.id.tv1:
                ToastUtil.showToast(this,"分享-------------");
                break;
        }
    }

    private void showDialog5() {
        BottomDialog.create(getSupportFragmentManager())
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        tv1 = (TextView) v.findViewById(R.id.tv1);
                        tv1.setOnClickListener(MainActivity.this);
                    }
                })
                .setLayoutRes(R.layout.dialog_bottom_layout)
                .setDimAmount(0.5f)
                .setTag("BottomDialog")
                .show();
    }

    private void showCustomDialog() {
        final List<String> names = new ArrayList<>();
        names.add("拍照");
        names.add("相册");
        names.add("其他");
        showDialog(new CustomSelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, names.get(position), Toast.LENGTH_SHORT).show();
            }
        }, names);
    }

    /**
     * 展示对话框视图，构造方法创建对象
     */
    private CustomSelectDialog showDialog(CustomSelectDialog.SelectDialogListener listener,
                                          List<String> names) {
        CustomSelectDialog dialog = new CustomSelectDialog(this,
                R.style.transparentFrameWindowStyle, listener, names);
        dialog.setItemColor(R.color.colorAccent,R.color.colorPrimary);
        //判断activity是否finish
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


    private void showCustomBottomDialog() {
        new CustomBottomDialog(MainActivity.this)
                .title("这个是标题")
                .setCancel(true,"取消选择")
                .orientation(CustomBottomDialog.VERTICAL)
                .inflateMenu(R.menu.menu_share, new OnItemClickListener() {
                    @Override
                    public void click(CustomItem item) {

                    }
                })
                .show();
    }

}
