### 自定义对话框
#### 目录介绍
- **1.使用方法**
	- 1.1 添加库
	- 1.2 关于对话框的种类
	- 1.3 代码使用方法
- **2.自定义对话框截图**
- **3.关于版本更新情况**
- **4.关于弹窗系列博客**
- **5.关于弹窗遇到bug管理**
- **6.关于其他更多信息**


#### 1.使用方法
- **1.1首先在项目build.gradlew中添加**
```
compile 'cn.yc:YCDialogLib:3.6.4'
```

- **1.2 关于对话框的种类**
	- 1.2.1 仿IOS底部弹窗Dialog
	- 1.2.2 自定义Toast
	- 1.2.3 自定义简易型PopupWindow
	- 1.2.4 自定义PopupWindow，builder模式
	- 1.2.5 自定义底部弹窗Dialog，builder模式【使用menu】
	- 1.2.6 自定义布局弹窗dialogFragment[填充普通布局]
	- 1.2.7 自定义布局弹窗dialogFragment[填充list布局]
	- 1.2.8 自定义常见弹窗，builder模式
	- 1.2.9 自定义loading加载窗


> **1.2.1 仿IOS底部弹窗，自定义对话框**

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

> **1.2.2 自定义Toast**
- 采用builder构造者模式，链式编程，一行代码调用即可设置吐司Toast。注意：为了避免静态toast对象内存泄漏，固可以使用应用级别的上下文context。
- 具体的用法如下所示：
```
//可以自由设置吐司的背景颜色，默认是纯黑色
ToastUtils.setToastBackColor(this.getResources().getColor(R.color.color_7f000000));

//直接设置最简单吐司，只有吐司内容
ToastUtils.showRoundRectToast("自定义吐司");

//设置吐司标题和内容
ToastUtils.showRoundRectToast("吐司一下","他发的撒经济法的解放军");

//第三种直接设置自定义布局的吐司
ToastUtils.showRoundRectToast(R.layout.view_layout_toast_delete);

//或者直接采用bulider模式创建
ToastUtils.Builder builder = new ToastUtils.Builder(this.getApplication());
builder
        .setDuration(Toast.LENGTH_SHORT)
        .setFill(false)
        .setGravity(Gravity.CENTER)
        .setOffset(0)
        .setDesc("内容内容")
        .setTitle("标题")
        .setTextColor(Color.WHITE)
        .setBackgroundColor(this.getResources().getColor(R.color.blackText))
        .build()
        .show();
```

> **1.2.3 自定义简易型PopupWindow**
```
//第一步，自定义pop，继承BasePopDialog类，重新抽象方法
public class CustomPop extends BasePopDialog {

    public CustomPop(Context context) {
        super(context);
    }

    @Override
    public int getViewResId() {
        return R.layout.view_pop_custom;
    }

    @Override
    public void initData(View contentView) {
        TextView tv_pop = (TextView) contentView.findViewById(R.id.tv_pop);
        tv_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showRoundRectToast("滚犊子吧");
            }
        });
    }
}

//第二步，创建pop并且展示
CustomPop customPop = new CustomPop(this);
customPop.setDelayedMsDismiss(2500);
customPop.setBgAlpha(0.5f);
customPop.showAsDropDown(tv6, 0, -tv6.getMeasuredHeight() - tv6.getHeight());
```


> **1.2.4 自定义PopupWindow，builder模式**
- 如下所示，更加详细的用法的方法说明可以直接看项目demo的代码
```
View contentView = LayoutInflater.from(this).inflate(R.layout.pop_layout,null);
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
```




> **1.2.5 自定义底部弹窗Dialog，builder模式【使用menu】**
- 代码如下所示
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

> **1.2.6 自定义布局弹窗dialogFragment[填充普通布局]**
```
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
```

> **1.2.7 自定义布局弹窗dialogFragment[填充list布局]**
```
final List<DialogBean> list = new ArrayList<>();
for(int a=0 ; a<20 ; a++){
    DialogBean dialogBean = new DialogBean("ooo","杨充","title");
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
                            ToastUtils.showToast("下载");
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
```

> **1.2.8 自定义常见弹窗，builder模式**
```
CustomDialogFragment
        .create(getSupportFragmentManager())
        .setTitle("这个是是标题")
        .setContent("这个是弹窗的内容")
        .setOtherContent("其他")
        .setDimAmount(0.2f)
        .setTag("dialog")
        .setCancelOutside(true)
        .setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment.dismissDialogFragment();
                ToastUtils.showRoundRectToast("取消了");
            }
        })
        .setOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment.dismissDialogFragment();
                ToastUtils.showRoundRectToast("确定了");
            }
        })
        .setOtherListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogFragment.dismissDialogFragment();
                ToastUtils.showRoundRectToast("其他内容");
            }
        })
        .show();
```

> **1.2.9 自定义loading加载窗**
- 一行代码调用即可：
```
//开始loading
ViewLoading.show(this);
ViewLoading.show(this,"加载中");
//结束loading
ViewLoading.dismiss(this);
```



#### 2.自定义对话框截图
- ![image](https://github.com/yangchong211/YCDialog/blob/master/pic/1.jpg)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/2.png)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/3.png)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/4.png)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/5.png)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/6.png)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/7.png)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/8.png)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/9.png)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/10.png)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/11.png)
![image](https://github.com/yangchong211/YCDialog/blob/master/pic/12.png)



#### 3.关于版本更新情况
- v1.0 更新2017年3月2日
- v1.4 更新2017年8月9日
- v3.3 更新2018年1月12日
- v3.4 更新2018年1月18日
- v3.5 更新2018年1月31日
- v3.5.1 更新与2018年6月2日
- v3.5.3 更新于2018年9月10日
```
3.4.1 更新bottomLayout包下代码，添加了取消销毁dialog监听，还有就是在调用show方法时增加了对传值的判断，解决了空指针崩溃问题
3.4.2 更新了dialog包下的CustomSelectDialog代码，添加了多个创造对话框的构造方法，方便调用时适用不同的业务场景
3.4.3 不知道在什么情况下调用getWindow()获取的Window对象，会为null，所以增加了非空判断
3.4.4 增加了一些方法的注释

3.5.1 去除了toast工具类中无用的代码，整理了代码，采用builder模式
```

#### 6.关于获取更多信息
##### 01.关于博客汇总链接
- 1.[技术博客汇总](https://www.jianshu.com/p/614cb839182c)
- 2.[开源项目汇总](https://blog.csdn.net/m0_37700275/article/details/80863574)
- 3.[生活博客汇总](https://blog.csdn.net/m0_37700275/article/details/79832978)
- 4.[喜马拉雅音频汇总](https://www.jianshu.com/p/f665de16d1eb)
- 5.[其他汇总](https://www.jianshu.com/p/53017c3fc75d)



##### 02.关于我的博客
- 我的个人站点：www.yczbj.org，www.ycbjie.cn
- github：https://github.com/yangchong211
- 知乎：https://www.zhihu.com/people/yang-chong-69-24/pins/posts
- 简书：http://www.jianshu.com/u/b7b2c6ed9284
- csdn：http://my.csdn.net/m0_37700275
- 喜马拉雅听书：http://www.ximalaya.com/zhubo/71989305/
- 开源中国：https://my.oschina.net/zbj1618/blog
- 泡在网上的日子：http://www.jcodecraeer.com/member/content_list.php?channelid=1
- 邮箱：yangchong211@163.com
- 阿里云博客：https://yq.aliyun.com/users/article?spm=5176.100- 239.headeruserinfo.3.dT4bcV
- segmentfault头条：https://segmentfault.com/u/xiangjianyu/articles



