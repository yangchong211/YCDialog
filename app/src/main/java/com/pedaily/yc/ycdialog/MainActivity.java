package com.pedaily.yc.ycdialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.pedaily.yc.ycdialoglib.bottomLayout.BottomDialogFragment;
import com.pedaily.yc.ycdialoglib.bottomMenu.CustomBottomDialog;
import com.pedaily.yc.ycdialoglib.bottomMenu.CustomItem;
import com.pedaily.yc.ycdialoglib.bottomMenu.OnItemClickListener;
import com.pedaily.yc.ycdialoglib.customPopWindow.CustomPopupWindow;
import com.pedaily.yc.ycdialoglib.selectDialog.CustomSelectDialog;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

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
    private CustomPopupWindow popWindow;
    private TextView tv6;

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
        findViewById(R.id.tv_23).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
        findViewById(R.id.tv_5_1).setOnClickListener(this);
        tv6 = (TextView) findViewById(R.id.tv_6);
        findViewById(R.id.tv_6).setOnClickListener(this);
        findViewById(R.id.tv_6_1).setOnClickListener(this);
        findViewById(R.id.tv_7).setOnClickListener(this);
        findViewById(R.id.tv_8).setOnClickListener(this);
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
            case R.id.tv_23:
                ToastUtil.showStart(this,0);
                break;
            case R.id.tv_4:
                showCustomBottomDialog();
                break;
            case R.id.tv_5:
                showDialog5();
                break;
            case R.id.tv_5_1:
                showDialog51();
                break;
            case R.id.tv_6:
                showPopupWindow1();
                break;
            case R.id.tv_6_1:
                showPopupWindow2();
                break;
            case R.id.tv_7:
                ToastUtil.showToast(this,"测试");
                showBuilder();
                break;
            case R.id.tv_8:

                break;
            case R.id.tv1:
                ToastUtil.showToast(this,"分享-------------");
                break;
            default:
                break;
        }
    }

    /**
     * 两种方式
     */
    private void showDialog5() {
        final BottomDialogFragment dialog = new BottomDialogFragment();
        dialog.setFragmentManager(getSupportFragmentManager());
        dialog.setViewListener(new BottomDialogFragment.ViewListener() {
            @Override
            public void bindView(View v) {
                TextView tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismissDialogFragment();
                    }
                });
            }
        });
        dialog.setLayoutRes(R.layout.dialog_bottom_layout);
        dialog.setDimAmount(0.5f);
        dialog.setTag("BottomDialog");
        dialog.setCancelOutside(true);
        //这个高度可以自己设置，十分灵活
        dialog.setHeight(getScreenHeight() / 2);
        dialog.show();
    }


    private void showDialog51() {
        final List<DialogBean> list = new ArrayList<>();
        for(int a=0 ; a<20 ; a++){
            DialogBean dialogBean = new DialogBean("ooo","yangchong","title");
            list.add(dialogBean);
        }

        BottomDialogFragment.create(getSupportFragmentManager())
                .setViewListener(new BottomDialogFragment.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        ImageView ivDownload = (ImageView) v.findViewById(R.id.iv_download);

                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        DialogListAdapter mAdapter = new DialogListAdapter(MainActivity.this, list);
                        recyclerView.setAdapter(mAdapter);
                        final RecycleViewItemLine line = new RecycleViewItemLine(
                                MainActivity.this, LinearLayout.HORIZONTAL, 2,
                                MainActivity.this.getResources().getColor(R.color.grayLine));
                        recyclerView.addItemDecoration(line);
                        mAdapter.setOnItemClickListener(new DialogListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                            }
                        });
                        View.OnClickListener listener = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.iv_cancel:

                                        break;
                                    case R.id.iv_download:
                                        ToastUtil.showToast(MainActivity.this,"下载");
                                        break;
                                    default:
                                        break;
                                }
                            }
                        };
                        ivCancel.setOnClickListener(listener);
                        ivDownload.setOnClickListener(listener);
                    }
                })
                .setLayoutRes(R.layout.dialog_bottom_layout_list)
                .setDimAmount(0.5f)
                .setTag("BottomDialog")
                .setCancelOutside(true)
                .setHeight(getScreenHeight() / 2)
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
    private CustomSelectDialog showDialog(CustomSelectDialog.SelectDialogListener listener, List<String> names) {
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


    /**
     * 这个位置可以自定义的，上下左右都行，很灵活
     */
    private void showPopupWindow1() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_layout,null);
        //处理popWindow 显示内容,自定义布局
        handleLogic(contentView);
        //处理popWindow 显示内容,recycleView
        //handleListView(contentView);
        //创建并显示popWindow
        popWindow = new CustomPopupWindow.PopupWindowBuilder(this)
                //.setView(R.layout.pop_layout)
                .setView(contentView)
                .setFocusable(true)
                //弹出popWindow时，背景是否变暗
                .enableBackgroundDark(true)
                //控制亮度
                .setBgDarkAlpha(0.7f)
                .setOutsideTouchable(true)
                .setAnimationStyle(R.style.popWindowStyle)
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //对话框销毁时
                    }
                })
                .create()
                .showAsDropDown(tv6,0,10);
    }


    private void showPopupWindow2() {
        CustomPopupWindow popWindow =
                new CustomPopupWindow.PopupWindowBuilder(this)
                .setView(R.layout.pop_layout)
                .create();
        popWindow .showAsDropDown(tv6,0,  - (tv6.getHeight() + popWindow.getHeight()));
        //popWindow.showAtLocation(mButton1, Gravity.NO_GRAVITY,0,0);
    }


    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popWindow!=null){
                    popWindow.dismiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.menu1:
                        showContent = "点击 Item菜单1";
                        break;
                    case R.id.menu2:
                        showContent = "点击 Item菜单2";
                        break;
                    default:
                        break;
                }
                Toast.makeText(MainActivity.this,showContent,Toast.LENGTH_SHORT).show();
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
    }



    private void showBuilder() {
        new BuilderDemo.UserBuilder("yc","10086")
                .age(24)
                .address("beijing")
                .phone("13667225184")
                .build();

    }


    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    public int getScreenWidth() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高px
     */
    public int getScreenHeight() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


}
