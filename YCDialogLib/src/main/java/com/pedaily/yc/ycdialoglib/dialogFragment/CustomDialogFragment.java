package com.pedaily.yc.ycdialoglib.dialogFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pedaily.yc.ycdialoglib.R;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/8/9
 *     desc  : 自定义布局弹窗DialogFragment
 *     revise: 1月10日
 *                  有bug，如果想通过点击弹窗控件销毁dialog，该如何处理。
 *                  使用场景：点击对话框中控件，不会销毁dialog，按返回键处理销毁逻辑。
 *                  其实质是DialogFragment，
 * </pre>
 */
public class CustomDialogFragment extends BaseDialogFragment {

    private static final String KEY_HEIGHT = "bottom_height";
    private static final String KEY_DIM = "bottom_dim";
    private static final String KEY_CANCEL_OUTSIDE = "bottom_cancel_outside";

    private FragmentManager mFragmentManager;
    private boolean mIsCancelOutside = false;
    private String mTag = super.getFragmentTag();
    private float mDimAmount = super.getDimAmount();
    private int mHeight = super.getHeight();
    private String title;
    private String content;
    private int color;
    private View.OnClickListener cancelListener;
    private View.OnClickListener okListener;
    private String otherContent;
    private View.OnClickListener otherListener;


    public static CustomDialogFragment create(FragmentManager manager) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.setFragmentManager(manager);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLocal(Local.CENTER);
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mHeight = savedInstanceState.getInt(KEY_HEIGHT);
            mDimAmount = savedInstanceState.getFloat(KEY_DIM);
            mIsCancelOutside = savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_HEIGHT, mHeight);
        outState.putFloat(KEY_DIM, mDimAmount);
        outState.putBoolean(KEY_CANCEL_OUTSIDE, mIsCancelOutside);
        super.onSaveInstanceState(outState);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.view_custom_dialog;
    }
    
    @Override
    public void bindView(View v) {
        TextView mTvTitle = (TextView) v.findViewById(R.id.tv_title);
        TextView mTvContent = (TextView) v.findViewById(R.id.tv_content);
        TextView mTvCancel = (TextView) v.findViewById(R.id.tv_cancel);
        TextView mTvOk = (TextView) v.findViewById(R.id.tv_ok);
        View mViewLine = v.findViewById(R.id.view_line);
        TextView mTvOther = (TextView) v.findViewById(R.id.tv_other);


        if (title!=null && title.length()>0){
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        }else {
            mTvTitle.setVisibility(View.GONE);
        }

        if (content!=null && content.length()>0){
            mTvContent.setVisibility(View.VISIBLE);
            mTvContent.setText(content);
        }else {
            mTvContent.setVisibility(View.GONE);
        }

        if(color!=0){
            mTvOk.setTextColor(color);
        }else {
            mTvOk.setTextColor(Color.parseColor("#ff666666"));
        }

        if(cancelListener!=null){
            mTvCancel.setOnClickListener(cancelListener);
        }
        if(okListener!=null){
            mTvOk.setOnClickListener(okListener);
        }

        if(otherContent!=null && otherContent.length()>0 && otherListener!=null){
            mViewLine.setVisibility(View.VISIBLE);
            mTvOther.setVisibility(View.VISIBLE);
            mTvOther.setOnClickListener(otherListener);
            dismissDialogFragment();
        }else {
            mViewLine.setVisibility(View.GONE);
            mTvOther.setVisibility(View.GONE);
        }
    }

    @Override
    protected boolean isCancel() {
        return mIsCancelOutside;
    }

    public CustomDialogFragment setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }

    public CustomDialogFragment setCancelOutside(boolean cancel) {
        mIsCancelOutside = cancel;
        return this;
    }

    public CustomDialogFragment setTag(String tag) {
        mTag = tag;
        return this;
    }

    public CustomDialogFragment setDimAmount(float dim) {
        mDimAmount = dim;
        return this;
    }

    public CustomDialogFragment setHeight(int heightPx) {
        mHeight = heightPx;
        return this;
    }

    public CustomDialogFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialogFragment setContent(String content) {
        this.content = content;
        return this;
    }

    public CustomDialogFragment setOkColor(@ColorInt int color) {
        this.color = color;
        return this;
    }

    public CustomDialogFragment setOtherContent(String content) {
        this.otherContent = content;
        return this;
    }

    public CustomDialogFragment setCancelListener(View.OnClickListener listener) {
        cancelListener = listener;
        return this;
    }

    public CustomDialogFragment setOkListener(View.OnClickListener listener) {
        okListener = listener;
        return this;
    }

    public CustomDialogFragment setOtherListener(View.OnClickListener listener) {
        otherListener = listener;
        return this;
    }

    @Override
    public float getDimAmount() {
        return mDimAmount;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public String getFragmentTag() {
        return mTag;
    }


    public BaseDialogFragment show() {
        show(mFragmentManager);
        return this;
    }

    public static void dismissDialogFragment(){
        dismissDialog();
    }

}
