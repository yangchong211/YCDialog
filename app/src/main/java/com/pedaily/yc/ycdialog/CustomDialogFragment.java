package com.pedaily.yc.ycdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;


public class CustomDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置样式
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CenterDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_fragment_dialog, container, false);
    }

    public static void showDialog(FragmentActivity activity){
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
        customDialogFragment.show(activity.getSupportFragmentManager(),"dialog");
    }

}
