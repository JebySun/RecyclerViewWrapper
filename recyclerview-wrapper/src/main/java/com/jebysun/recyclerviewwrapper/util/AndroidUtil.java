package com.jebysun.recyclerviewwrapper.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Msrlin on 2017/9/14.
 */

public final class AndroidUtil {

    private AndroidUtil() {}

    public static void toastShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * dp转换为px
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转换为dp
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * sp转换为px
     * @param context
     * @pa
     * @return
     */
    public static int sp2px(Context context, float sp) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }
    /**
     * px转换为sp
     * @param context
     * @param px
     * @return
     */
    public static int px2sp(Context context, float px) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }


    /**
     * 获取屏幕宽度（px）
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕高度（px）
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }


    /**
     * 兼容方式获取颜色资源
     * @param context
     * @param colorResId
     * @return
     */
    public static int getColor(Context context, int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(colorResId);
        }
        return context.getResources().getColor(colorResId);
    }

    /**
     * 兼容方式获取Drawable资源
     * @param context
     * @param drawableResId
     * @return
     */
    public static Drawable getDrawable(Context context, int drawableResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getDrawable(drawableResId);
        }
        return context.getResources().getDrawable(drawableResId);
    }


    /**
     * 从assets目录读取文本信息
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getAssetsString(Context context, String fileName) {
        String string = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            string = new String(buffer);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取App版本名称
     * @return
     */
    public static String getAppVersionName(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        return packageInfo.versionName;
    }


    /**
     * 获取App版本代码
     * @return
     */
    public static int getAppVersionCode(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
        return packageInfo.versionCode;
    }


    /**
     * 安装apk文件
     */
    public static void installApk(Context context, String filePath) {
        File apkFile = new File(filePath);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /**
     * 从Manifest文件中获取配置信息
     * @param context
     * @return key
     */
    public static String getMetaData(Context context, String key) {
        String value = null;
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (appInfo != null && appInfo.metaData != null) {
            value = appInfo.metaData.getString(key);
        }
        return value;
    }

    /**
     * 获取状态栏高度(px)
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    /**
     * 打开输入法软键盘
     */
    public static void openKeybord(Context mContext, View editTextView) {
        editTextView.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextView, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 关闭输入法软键盘
     */
    public static void closeKeybord(Context context, View view) {
        IBinder windowToken = view.getWindowToken();
        if (windowToken != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    /**
     * 输入法是否打开
     * @param context
     * @return
     */
    public static boolean isKeybordOpen(Context context) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return im.isActive();
    }


    /**
     * 设置Activity的背景透明度
     * @param bgAlpha 取值0.0~1.0
     */
    public static void setActivityBackgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams winLayoutParam = context.getWindow().getAttributes();
        winLayoutParam.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(winLayoutParam);
    }


    /**
     * 为视图设置外间距
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }



}
