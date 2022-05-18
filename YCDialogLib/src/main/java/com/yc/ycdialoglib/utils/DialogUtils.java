package com.yc.ycdialoglib.utils;


import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/06/4
 *     desc  : Toast工具类
 *     revise: 具体看GitHub开源项目：https://github.com/yangchong211
 * </pre>
 */
public final class DialogUtils {


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void checkMainThread(){
        if (!isMainThread()){
            throw new IllegalStateException("请不要在子线程中做弹窗操作");
        }
    }

    private static boolean isMainThread(){
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 设置页面的透明度
     * 主要作用于：弹窗时设置宿主Activity的背景色
     * @param bgAlpha 1表示不透明
     */
    public static void setBackgroundAlpha(Activity activity , float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        Window window = activity.getWindow();
        if(window!=null){
            if (bgAlpha == 1) {
                //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            } else {
                //此行代码主要是解决在华为手机上半透明效果无效的bug
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
            window.setAttributes(lp);
        }
    }


    /**
     * 不知道为啥，竟然不管用了。后期深入研究下
     * @param context                       上下文
     * @return
     */
    private static boolean isNotificationEnabled(Context context) {
        String checkOpNoThrow = "checkOpNoThrow";
        String postNotification = "OP_POST_NOTIFICATION";
        AppOpsManager mAppOps = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        }
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass ;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                appOpsClass = Class.forName(AppOpsManager.class.getName());
                @SuppressWarnings("unchecked")
                Method checkOpNoThrowMethod = appOpsClass.getMethod(
                        checkOpNoThrow, Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField(postNotification);
                int value = (Integer) opPostNotificationValue.get(Integer.class);
                return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg)
                        == AppOpsManager.MODE_ALLOWED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    public static boolean checkNull(SoftReference softReference) {
        if (softReference == null || softReference.get() == null) {
            return true;
        }
        return false;
    }

}
