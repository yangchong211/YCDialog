package com.pedaily.yc.ycdialoglib.customToast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pedaily.yc.ycdialoglib.R;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/6/28
 * 描    述：Toast工具类
 * 修订历史：
 * ================================================
 */
public class ToastUtil {

    private ToastUtil() {}

    /**
     * 正在开通
     * @param context               上下文
     * @param viewId                视图id
     */
    public static void showStart(Context context , int viewId) {
        if (context == null) {
            return;
        }
        final Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        View view ;
        if(viewId==0){
            view = LayoutInflater.from(context).inflate(R.layout.view_layout_toast_load,null,false);
        }else {
            view = LayoutInflater.from(context).inflate(viewId,null,false);
        }
        LinearLayout llToast = (LinearLayout) view.findViewById(R.id.toast);
        //布局文件中设置的宽高不顶用，需要重新设置;注意:不能设置最外层控件的宽高，会报空指针，可以设置第二层控件的宽高
        Activity activity = (Activity) context;
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        llToast.getLayoutParams().width = (int) (screenWidth*0.411);
        llToast.getLayoutParams().height = (int) (screenHeight*0.18);
        //设置吐司居中显示
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }


    /**
     * 删除
     * @param context               上下文
     * @param viewId                视图id
     */
    public static void showDelete(Context context, int viewId) {
        if (context == null) {
            return;
        }
        final Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        View view ;
        if(viewId==0){
            view = LayoutInflater.from(context).inflate(R.layout.view_layout_toast_delete,null,false);
        }else {
            view = LayoutInflater.from(context).inflate(viewId,null,false);
        }
        LinearLayout llToast = (LinearLayout) view.findViewById(R.id.toast);
        //布局文件中设置的宽高不顶用，需要重新设置;注意:不能设置最外层控件的宽高，会报空指针，可以设置第二层控件的宽高
        Activity activity = (Activity) context;
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        llToast.getLayoutParams().width = (int) (screenWidth*0.411);
        llToast.getLayoutParams().height = (int) (screenHeight*0.18);
        //设置吐司居中显示
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }


    /**
     * 吐司工具类    避免点击多次导致吐司多次，最后导致Toast就长时间关闭不掉了
     * @param context       注意：这里如果传入context会报内存泄漏；传递activity..getApplicationContext()
     * @param content       吐司内容
     */
    private static Toast toast;
    @SuppressLint("ShowToast")
    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }


}
