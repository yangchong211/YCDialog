### 自定义对话框
#### 目录介绍
- **1.使用方法**
	- 1.1 添加库
	- 1.2 关于对话框的种类
	- 1.3 代码使用方法
- **2.自定义对话框截图**
- **3.关于版本更新情况**
- **4.关于其他更多信息**


#### 1.使用方法
- **1.1首先在项目build.gradlew中添加 **
```
compile 'cn.yc:YCDialogLib:3.3'
```

- **1.2 关于对话框的种类**
	- 仿IOS底部弹窗
	- 自定义Toast
	- 自定义底部弹窗【使用menu】
	- 自定义底部弹窗【用布局，与上一种是不同的】
	- 自定义底部弹窗【在自定义布局中添加recyclerView】
	- 自定义PopupWindow弹窗【Builder模式】
	- 自定义loading加载窗
	

> **自定义对话框，仿IOS底部弹窗**

```
private void showCustomDialog() {
	final List<String> names = new ArrayList<>();
	names.add("拍照");
	names.add("相册");
	names.add("其他");
	showDialog(new CustomSelectDialog.SelectDialogListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		}
	}, names);
}
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
```

> **自定义底部弹窗【menu】**

```
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
```
> **自定义底部弹窗【布局】**
```
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
```

> **自定义PopupWindow弹窗【Builder模式】**
```
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
```

> **自定义loading加载窗**
- 直接一行代码搞定展示或者销毁
```
//展示
LoadDialog.show(this,"加载中",false,false);
//销毁
LoadDialog.dismiss(MainActivity.this);
```

- 源代码是这样写的，判断了当前宿主activity是否销毁，并且销毁对象为null，避免静态对象导致内存泄漏。
```
    /**
     * 展示加载窗
     * @param context               上下文
     * @param message               内容
     * @param showMsg               是否展示文字
     * @param isCancel              是否可以取消
     */
    public static void show(Context context, String message,boolean showMsg, boolean isCancel) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new LoadDialog(context, isCancel,showMsg, message);
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
        }
    }
```



#### 2.自定义对话框截图
- ![image](https://github.com/yangchong211/YCDialog/blob/master/pic/Screenshot1.png)
- ![image](https://github.com/yangchong211/YCDialog/blob/master/pic/Screenshot2.png)
- ![image](https://github.com/yangchong211/YCDialog/blob/master/pic/Screenshot3.png)
- ![image](https://github.com/yangchong211/YCDialog/blob/master/pic/Screenshot4.png)
- ![image](https://github.com/yangchong211/YCDialog/blob/master/pic/Screenshot5.png)
- ![image](https://github.com/yangchong211/YCDialog/blob/master/pic/Screenshot6.png)


#### 3.关于版本更新情况
- v1.0 更新2017年3月2日
- v3.3 更新2018年1月12日

#### 4.关于获取更多信息
- 知乎：https://www.zhihu.com/people/yang-chong-69-24/pins/posts
- 领英：https://www.linkedin.com/in/chong-yang-049216146/
- 简书：http://www.jianshu.com/u/b7b2c6ed9284
- csdn：http://my.csdn.net/m0_37700275
- 网易博客：http://yangchong211.blog.163.com/
- 新浪博客：http://blog.sina.com.cn/786041010yc
- github：https://github.com/yangchong211
- 喜马拉雅听书：http://www.ximalaya.com/zhubo/71989305/
- 脉脉：yc930211
- 360图书馆：http://www.360doc.com/myfiles.aspx
- 开源中国：https://my.oschina.net/zbj1618/blog
- 泡在网上的日子：http://www.jcodecraeer.com/member/content_list.php?channelid=1
- 邮箱：yangchong211@163.com
- 阿里云博客：https://yq.aliyun.com/users/article?spm=5176.100239.headeruserinfo.3.dT4bcV



