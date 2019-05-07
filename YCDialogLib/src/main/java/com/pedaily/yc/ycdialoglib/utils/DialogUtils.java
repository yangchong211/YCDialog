package com.pedaily.yc.ycdialoglib.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pedaily.yc.ycdialoglib.R;
import com.pedaily.yc.ycdialoglib.fragment.CustomDialogFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *     @author
 *     blog  : https://github.com/yangchong211
 *     time  : 2016/05/1
 *     desc  : 弹窗工具类
 *     revise:
 * </pre>
 */
public final class DialogUtils {


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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


    public static void requestMsgPermission(final Context context) {
        if(context==null){
            return;
        }
        try {
            // 6.0以上系统才可以判断权限
            if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                CustomDialogFragment
                        .create(((FragmentActivity)context).getSupportFragmentManager())
                        .setTitle("")
                        .setContent("开启消息通知能够帮助你查看更多消息哦~")
                        .setCancelContent("下次再说")
                        .setOkContent("立即开启")
                        .setOkColor(context.getResources().getColor(R.color.color_000000))
                        .setCancelOutside(true)
                        .setCancelListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomDialogFragment.dismissDialogFragment();
                            }
                        })
                        .setOkListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomDialogFragment.dismissDialogFragment();
                                toSetting(context);
                            }
                        })
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 不知道为啥，竟然不管用了。后期深入研究下
     * @param context                       上下文
     * @return
     */
    private static boolean isNotificationEnabled(Context context) {
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
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
                        CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
                int value = (Integer) opPostNotificationValue.get(Integer.class);
                return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg)
                        == AppOpsManager.MODE_ALLOWED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("ObsoleteSdkInt")
    private static void toSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings",
                    "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }


}
