### 自定义对话框
#### 目录介绍
- **1.使用方法**
	- 1.1 添加库
	- 1.2 关于对话框的种类
	- 1.3 代码使用方法
- **2.自定义对话框截图**
- **3.关于版本更新情况**
- **4.关于弹窗系列博客[共7篇]**
- **5.关于弹窗遇到bug管理**
- **6.关于其他更多信息**



### 好消息
- 博客笔记大汇总【16年3月到至今】，包括Java基础及深入知识点，Android技术博客，Python学习笔记等等，还包括平时开发中遇到的bug汇总，当然也在工作之余收集了大量的面试题，长期更新维护并且修正，持续完善……开源的文件是markdown格式的！同时也开源了生活博客，从12年起，积累共计47篇[近20万字]，转载请注明出处，谢谢！
- **链接地址：https://github.com/yangchong211/YCBlogs**
- 如果觉得好，可以star一下，谢谢！当然也欢迎提出建议，万事起于忽微，量变引起质变！


#### 1.使用方法
- **1.1首先在项目build.gradlew中添加**
```
compile 'cn.yc:YCDialogLib:3.6.5'
```

- **1.2 关于对话框的种类**
	- 1.2.0 判断通知权限
	- 1.2.1 仿IOS底部弹窗Dialog
	- 1.2.2 自定义Toast
	- 1.2.3 自定义简易型PopupWindow
	- 1.2.4 自定义PopupWindow，builder模式
	- 1.2.5 自定义底部弹窗Dialog，builder模式【使用menu】
	- 1.2.6 自定义布局弹窗dialogFragment[填充普通布局]
	- 1.2.7 自定义布局弹窗dialogFragment[填充list布局]
	- 1.2.8 自定义常见弹窗，builder模式
	- 1.2.9 自定义loading加载窗
	- 1.3.0 自定义SnackBar


> **1.2.0 判断通知权限**
- 一行代码调用，建议加上，大部分手机通知权限是开启的。如果关闭了，则吐司是无法显示的，但是仍有部分手机，比如某型号小米手机，锤子手机等就权限需要手动开启。
```
//注意，建议加上这个判断
DialogUtils.requestMsgPermission(this);
```


> **1.2.1 仿IOS底部弹窗，自定义对话框**
- 支持设置弹窗主题，支持设置取消事件listener，支持设置名称和标题。有多个构造方法可以创建对象……
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
- 支持设置吐司的背景颜色，支持设置自定义吐司，同时支持设置吐司的多种属性等等，具体的用法如下所示：
```
//可以自由设置吐司的背景颜色，默认是纯黑色
ToastUtils.setToastBackColor(this.getResources().getColor(R.color.color_7f000000));

//直接设置最简单吐司，只有吐司内容
ToastUtils.showRoundRectToast("自定义吐司");

//设置吐司标题和内容
ToastUtils.showRoundRectToast("吐司一下","潇湘剑雨杨充是个逗比");

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
- 只需要继承BasePopDialog即可，实现其中的两个抽象方法
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
- 同1.2.7 具体可以先安装一下demo，看一下效果，使用方法如下所示：
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
- 这个是之前沙丘大学底部弹窗而定制的，可以设置自定义布局view或者layout，可以设置tag，设置是否可以cancel弹窗，并且最主要可以设置弹窗的高度。
- 比较常见，比如下载音乐，下载视频等等，弹窗页面可以滚动，ok，就可以使用这个，代码也不是很复杂，具体使用场景可以参考我的另外一个demo：https://github.com/yangchong211/YCVideoPlayer
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
- 非常常见的弹窗，由于原生自带的弹窗不太美观，因此就需要自己定制弹窗呢。可以设置弹窗标题，内容，以及下面取消，确定等按钮内容和颜色
- 注意，如果某个属性设置为空或者不设置，那么就会隐藏该布局。这个也是根据公司产品具体的业务场景，逐渐演变过来的，具体效果可以直接看项目demo
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
- 一行代码调用即可，个人感觉不需要太复杂的代码，就能实现这个功能，满足具体业务需求，也是不亦乐乎：
```
//开始loading
ViewLoading.show(this);
ViewLoading.show(this,"加载中");
//结束loading
ViewLoading.dismiss(this);
```

> **1.3.0 自定义SnackBar**
- 可以一行代码调用，也可以自己使用链式编程调用。支持设置显示时长属性；可以设置背景色；可以设置文字大小，颜色；可以设置action内容，文字大小，颜色，还有点击事件；可以设置icon；代码如下所示，更多内容可以直接运行demo哦！
```
//1.只设置text
SnackBarUtils.showSnackBar(this,"滚犊子");

//2.设置text，action，和点击事件
SnackBarUtils.showSnackBar(this, "滚犊子", "ACTION", new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ToastUtils.showRoundRectToast("滚犊子啦？");
    }
});

//3.设置text，action，和点击事件，和icon
SnackBarUtils.showSnackBar(this, "滚犊子", "ACTION",R.drawable.icon_cancel, new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ToastUtils.showRoundRectToast("滚犊子啦？");
    }
});

//4.链式调用
SnackBarUtils.builder()
    .setBackgroundColor(this.getResources().getColor(R.color.color_7f000000))
    .setTextSize(14)
    .setTextColor(this.getResources().getColor(R.color.white))
    .setTextTypefaceStyle(Typeface.BOLD)
    .setText("滚犊子")
    .setMaxLines(4)
    .centerText()
    .setActionText("收到")
    .setActionTextColor(this.getResources().getColor(R.color.color_f25057))
    .setActionTextSize(16)
    .setActionTextTypefaceStyle(Typeface.BOLD)
    .setActionClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtils.showRoundRectToast("滚犊子啦？");
        }
    })
    .setIcon(R.drawable.icon_cancel)
    .setActivity(MainActivity.this)
    .setDuration(SnackBarUtils.DurationType.LENGTH_INDEFINITE)
    .build()
    .show();
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
- v1.0 更新2016年6月2日
- v1.4 更新2017年8月9日
- v3.3 更新2018年1月12日
- v3.4 更新2018年1月18日
- v3.5 更新2018年1月31日
- v3.5.1 更新与2018年6月2日
- v3.5.3 更新于2018年9月10日
- v3.6.5 更新于2018年9月15日




#### 4.关于弹窗系列博客
- [02.Toast源码深度分析](https://github.com/yangchong211/YCBlogs/blob/master/android/Window/02.Toast%E6%BA%90%E7%A0%81%E6%B7%B1%E5%BA%A6%E5%88%86%E6%9E%90.md)
    - 最简单的创建，简单改造避免重复创建，show()方法源码分析，scheduleTimeoutLocked吐司如何自动销毁的，TN类中的消息机制是如何执行的，普通应用的Toast显示数量是有限制的，用代码解释为何Activity销毁后Toast仍会显示，Toast偶尔报错Unable to add window是如何产生的，Toast运行在子线程问题，Toast如何添加系统窗口的权限等等
- [03.DialogFragment源码分析](https://github.com/yangchong211/YCBlogs/blob/master/android/Window/03.DialogFragment%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.md)
    - 最简单的使用方法，onCreate(@Nullable Bundle savedInstanceState)源码分析，重点分析弹窗展示和销毁源码，使用中show()方法遇到的IllegalStateException分析
- [04.Dialog源码分析](https://github.com/yangchong211/YCBlogs/blob/master/android/Window/04.Dialog%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.md)
    - AlertDialog源码分析，通过AlertDialog.Builder对象设置属性，Dialog生命周期，Dialog中show方法展示弹窗分析，Dialog的dismiss销毁弹窗，Dialog弹窗问题分析等等
- [05.PopupWindow源码分析](https://github.com/yangchong211/YCBlogs/blob/master/android/Window/05.PopupWindow%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.md)
    - 显示PopupWindow，注意问题宽和高属性，showAsDropDown()源码，dismiss()源码分析，PopupWindow和Dialog有什么区别？为何弹窗点击一下就dismiss呢？
- [06.Snackbar源码分析](https://github.com/yangchong211/YCBlogs/blob/master/android/Window/06.Snackbar%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.md)
    - 最简单的创建，Snackbar的make方法源码分析，Snackbar的show显示与点击消失源码分析，显示和隐藏中动画源码分析，Snackbar的设计思路，为什么Snackbar总是显示在最下面
- [07.弹窗常见问题](https://github.com/yangchong211/YCBlogs/blob/master/android/Window/07.%E5%BC%B9%E7%AA%97%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98.md)
    - DialogFragment使用中show()方法遇到的IllegalStateException,什么常见产生的？Toast偶尔报错Unable to add window，Toast运行在子线程导致崩溃如何解决？
- [08.Builder模式](https://github.com/yangchong211/YCBlogs/blob/master/android/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F/02.Builder%E6%A8%A1%E5%BC%8F.md)
    - 你会发现，在这个弹窗封装库中，很多地方用到了builder模式，那么可以先了解下Builder模式使用场景，简单案例，Builder模式实际案例Demo展示，看看AlertDialog.Builder源代码如何实现，为什么AlertDialog要使用builder模式呢？builder模式优缺点分析。
 

#### 5.关于弹窗遇到bug管理
- 关于弹窗的常见问题，可以看07中的博客。后期持续记录更新。



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



