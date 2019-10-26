#### 目录介绍
- 01.关于loading加载窗使用建议



### 01.关于loading加载窗使用建议
- 关于loading加载窗
    - 在弹窗show和dismiss的时候，可以增强一下逻辑判断
    ```
    dialog.show();
    ```
    - 改进后代码。可以增强两部分逻辑，第一个判断content上下文是activity，第二个是在show或者dismiss时都对activity是否销毁作出一个判断，防止loading出现unable add to window等异常。
    ```
    /**
     * 展示加载窗
     * @param context               上下文
     * @param message               内容
     * @param isCancel              是否可以取消
     */
    public static void show(Context context, Dialog dialog) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog.show();
    }
    ```







