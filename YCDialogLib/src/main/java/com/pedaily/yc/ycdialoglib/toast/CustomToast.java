package com.pedaily.yc.ycdialoglib.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pedaily.yc.ycdialoglib.R;


@SuppressLint("InflateParams")
public class CustomToast {

    @ColorInt
    private static int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
    @ColorInt
    private static int ERROR_COLOR = Color.parseColor("#D50000");
    @ColorInt
    private static int INFO_COLOR = Color.parseColor("#3F51B5");
    @ColorInt
    private static int SUCCESS_COLOR = Color.parseColor("#388E3C");
    @ColorInt
    private static int WARNING_COLOR = Color.parseColor("#FFA900");
    @ColorInt
    private static int NORMAL_COLOR = Color.parseColor("#353A3E");

    private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
    private static Typeface currentTypeface = LOADED_TOAST_TYPEFACE;
    private static int textSize = 16;

    private static boolean tintIcon = true;

    private CustomToast() {}

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message) {
        return normal(context, message, android.widget.Toast.LENGTH_SHORT, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, Drawable icon) {
        return normal(context, message, Toast.LENGTH_SHORT, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return normal(context, message, duration, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration, Drawable icon) {
        return normal(context, message, duration, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration, Drawable icon, boolean withIcon) {
        return custom(context, message, icon, NORMAL_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message) {
        return warning(context, message, android.widget.Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return warning(context, message, duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastUtil.getDrawable(context, R.drawable.ic_error_outline_white_48dp), WARNING_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message) {
        return info(context, message, android.widget.Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return info(context, message, duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastUtil.getDrawable(context, R.drawable.ic_info_outline_white_48dp), INFO_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message) {
        return success(context, message, android.widget.Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return success(context, message, duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastUtil.getDrawable(context, R.drawable.ic_check_white_48dp), SUCCESS_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message) {
        return error(context, message, android.widget.Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return error(context, message, duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastUtil.getDrawable(context, R.drawable.ic_clear_white_48dp), ERROR_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon, int duration, boolean withIcon) {
        return custom(context, message, icon, -1, duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, @DrawableRes int iconRes, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        return custom(context, message, ToastUtil.getDrawable(context, iconRes),
                tintColor, duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        final android.widget.Toast currentToast = new android.widget.Toast(context);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_toast_layout, null);
        final ImageView toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;
        if (shouldTint)
            drawableFrame = ToastUtil.tint9PatchDrawableFrame(context, tintColor);
        else
            drawableFrame = ToastUtil.getDrawable(context, R.drawable.toast_frame);
        ToastUtil.setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            if (tintIcon)
                icon = ToastUtil.tintIcon(icon, DEFAULT_TEXT_COLOR);
            ToastUtil.setBackground(toastIcon, icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setTextColor(DEFAULT_TEXT_COLOR);
        toastTextView.setText(message);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);
        return currentToast;
    }

    public static class Config {
        @ColorInt
        private int DEFAULT_TEXT_COLOR = CustomToast.DEFAULT_TEXT_COLOR;
        @ColorInt
        private int ERROR_COLOR = CustomToast.ERROR_COLOR;
        @ColorInt
        private int INFO_COLOR = CustomToast.INFO_COLOR;
        @ColorInt
        private int SUCCESS_COLOR = CustomToast.SUCCESS_COLOR;
        @ColorInt
        private int WARNING_COLOR = CustomToast.WARNING_COLOR;

        private Typeface typeface = CustomToast.currentTypeface;
        private int textSize = CustomToast.textSize;

        private boolean tintIcon = CustomToast.tintIcon;

        private Config() {
        }

        @CheckResult
        public static Config getInstance() {
            return new Config();
        }

        public static void reset() {
            CustomToast.DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
            CustomToast.ERROR_COLOR = Color.parseColor("#D50000");
            CustomToast.INFO_COLOR = Color.parseColor("#3F51B5");
            CustomToast.SUCCESS_COLOR = Color.parseColor("#388E3C");
            CustomToast.WARNING_COLOR = Color.parseColor("#FFA900");
            CustomToast.currentTypeface = LOADED_TOAST_TYPEFACE;
            CustomToast.textSize = 16;
            CustomToast.tintIcon = true;
        }

        @CheckResult
        public Config setTextColor(@ColorInt int textColor) {
            DEFAULT_TEXT_COLOR = textColor;
            return this;
        }

        @CheckResult
        public Config setErrorColor(@ColorInt int errorColor) {
            ERROR_COLOR = errorColor;
            return this;
        }

        @CheckResult
        public Config setInfoColor(@ColorInt int infoColor) {
            INFO_COLOR = infoColor;
            return this;
        }

        @CheckResult
        public Config setSuccessColor(@ColorInt int successColor) {
            SUCCESS_COLOR = successColor;
            return this;
        }

        @CheckResult
        public Config setWarningColor(@ColorInt int warningColor) {
            WARNING_COLOR = warningColor;
            return this;
        }

        @CheckResult
        public Config setToastTypeface(@NonNull Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

        @CheckResult
        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

        @CheckResult
        public Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        public void apply() {
            CustomToast.DEFAULT_TEXT_COLOR = DEFAULT_TEXT_COLOR;
            CustomToast.ERROR_COLOR = ERROR_COLOR;
            CustomToast.INFO_COLOR = INFO_COLOR;
            CustomToast.SUCCESS_COLOR = SUCCESS_COLOR;
            CustomToast.WARNING_COLOR = WARNING_COLOR;
            CustomToast.currentTypeface = typeface;
            CustomToast.textSize = textSize;
            CustomToast.tintIcon = tintIcon;
        }
    }
}
