package com.pedaily.yc.ycdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.pedaily.yc.ycdialoglib.bottomLayout.BottomDialog;
import com.pedaily.yc.ycdialoglib.bottomMenu.CustomBottomDialog;
import com.pedaily.yc.ycdialoglib.bottomMenu.CustomItem;
import com.pedaily.yc.ycdialoglib.bottomMenu.OnItemClickListener;
import com.pedaily.yc.ycdialoglib.customPopWindow.CustomPopupWindow;
import com.pedaily.yc.ycdialoglib.selector.CustomSelectDialog;
import com.pedaily.yc.ycdialoglib.toast.CustomToast;
import com.pedaily.yc.ycdialoglib.toast.ToastUtil;

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
        CustomToast.Config.reset();
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_23).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
        tv6 = (TextView) findViewById(R.id.tv_6);
        findViewById(R.id.tv_6).setOnClickListener(this);
        findViewById(R.id.tv_7).setOnClickListener(this);
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
                CustomToast.success(this, "分享").show();
                break;
            case R.id.tv_4:
                showCustomBottomDialog();
                break;
            case R.id.tv_5:
                showDialog5();
                break;
            case R.id.tv_6:
                showPopupWindow();
                break;
            case R.id.tv_7:
                showBuilder();
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


    private void showPopupWindow() {
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
                .enableBackgroundDark(true)         //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f)               //控制亮度
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
                }
                Toast.makeText(MainActivity.this,showContent,Toast.LENGTH_SHORT).show();
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
    }


    /*private void handleListView(View contentView){
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter();
        adapter.setData(mockData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private List<String> mockData(){
        List<String> data = new ArrayList<>();
        for (int i=0;i<100;i++){
            data.add("Item:"+i);
        }
        return data;
    }

    public class MyAdapter extends RecyclerView.Adapter{
        private List<String> mData;

        public void setData(List<String> data) {
            mData = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mTextView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0:mData.size();
        }

         class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mTextView;
            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.text_content);
            }
        }
    }*/


    private void showBuilder() {
        new BuilderDemo.UserBuilder("yc","10086")
                .age(24)
                .address("beijing")
                .phone("13667225184")
                .build();
    }


}
