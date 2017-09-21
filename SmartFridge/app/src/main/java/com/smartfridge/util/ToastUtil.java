package com.smartfridge.util;

import android.content.Context;
import android.widget.Toast;

import com.smartfridge.application.MyApplication;


public class ToastUtil {

    // public static void showDefaultNetWorkError(Context context) {
    // ToastUtil.showDefaultSuperToast(context, R.string.network_error);
    // }

    // public static void showDefaultNoConnectionError(Context context) {
    // ToastUtil.showDefaultSuperToast(context, R.string.no_connection_error);
    // }

    // public static void showNetworkError(VolleyError e) {
    // if (e instanceof NoConnectionError) {
    // showDefaultNoConnectionError(FMApplication.getAppContext());
    // } else {
    // showDefaultNetWorkError(FMApplication.getAppContext());
    // }
    // }

    private static final int TOAST_YOFFSET = 100;// ��λdp

    private static int getToastYoffset() {
        return DeviceUtil.dp2px(MyApplication.getAppContext(), TOAST_YOFFSET);
    }

    public static void showDefaultSuperToast(Context context, int resId) {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
        //		SuperToast.cancelAllSuperToasts();
        //		SuperToast s = new SuperToast(context, Style.getStyle(Style.GRAY));
        //		s.setAnimations(Animations.FADE);
        //		s.setGravity(Gravity.BOTTOM, 0, getToastYoffset());
        //		s.setText(context.getString(resId));
        //		s.show();
        //		s = null;
    }

    public static void showDefaultSuperToast(Context context, String str) {

        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

        //		SuperToast.cancelAllSuperToasts();
        //		SuperToast s = new SuperToast(context, Style.getStyle(Style.GRAY));
        //		s.setAnimations(Animations.FADE);
        //		s.setGravity(Gravity.BOTTOM, 0, getToastYoffset());
        //		s.setText(str);
        //		s.show();
        //		s = null;
    }

    //	public static void showDefaultSuperToastNoCancel(Context context, int resId) {
    //		SuperToast s = new SuperToast(context, Style.getStyle(Style.GRAY));
    //		s.setAnimations(Animations.FADE);
    //		s.setGravity(Gravity.BOTTOM, 0, getToastYoffset());
    //		s.setText(context.getString(resId));
    //		s.show();
    //		s = null;
    //	}
    //
    //	public static void showDefaultSuperToastNoCancel(Context context, String str) {
    //		SuperToast s = new SuperToast(context, Style.getStyle(Style.GRAY));
    //		s.setAnimations(Animations.FADE);
    //		s.setGravity(Gravity.BOTTOM, 0, getToastYoffset());
    //		s.setText(str);
    //		s.show();
    //		s = null;
    //	}

}
