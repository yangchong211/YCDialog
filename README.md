### 自定义对话框
- 使用方法
- 自定义对话框截图
- ![image](https://github.com/yangchong211/YCDialog/blob/master/pic/Screenshot1.png)
- ![image](https://github.com/yangchong211/YCDialog/blob/master/pic/Screenshot2.png)
- ![image](https://github.com/yangchong211/YCDialog/blob/master/pic/Screenshot3.png)

#### 使用方法
- 首先在项目build.gradlew中添加 

    compile 'cn.yc:YCDialogLib:3.0'



> 第一种自定义对话框，仿IOS底部弹窗

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

> 第二种弹窗使用方法

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









