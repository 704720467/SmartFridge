package com.smartfridge.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.support.annotation.DimenRes;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class DeviceUtil {

    private static final String TAG = "DeviceUtil";

    /**
     * dp转像素
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 像素转dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, int px) {
        return (int) (px / context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 获取屏幕宽单位是像素
     *
     * @param context
     * @return
     */
    public static int getScreenWidthSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高单位是像素
     *
     * @param context
     * @return
     */
    public static int getScreenHeightSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }


    /**
     * 横屏时获取屏幕宽单位是像素
     *
     * @param context
     * @return
     */
    public static int getLandscapeScreenWidthSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels + getNavigationBarHeight(context);
    }

    /**
     * 获取屏幕高单位是像素
     *
     * @param context
     * @return
     */
    public static int getLandscapeScreenHeightSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels + getStatusBarHeight(context);
    }


    /**
     * 获取屏幕高单位是像素
     * <p>
     * 高根据宽以16:9算出来的
     *
     * @param context
     * @return
     */
    public static int getPlayerHeightSize(Context context) {
        int screenWidth = getScreenWidthSize(context);
        return Math.round((float) screenWidth / 16 * 9);
    }

    /**
     * 获取屏幕的尺寸
     *
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int[] size = {dm.widthPixels, dm.heightPixels};
        return size;
    }

    /**
     * 获取版本
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
        }
        return "";
    }

    public static int getVersionCode(Context context) {
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
        }
        return 0;
    }

    /**
     * 获取顶部status bar 高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取底部 navigation bar 高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, height = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("navigation_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            height = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return height;
    }

    /*
     * 设置控件所在的位置YY，并且不改变宽高， XY为绝对位置
     */
    public static void setLayout(View view, int x, int y) {
        MarginLayoutParams margin = new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, x + margin.width, y + margin.height);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }


    /**
     * 获取application中指定的meta-data如: <meta-data android:name="UMENG_CHANNEL"
     * android:value="@string/channel" > </meta-data>其中的key就是UMENG_CHANNEL
     *
     * @param ctx 上下文对象
     * @param key 要查找的key
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }


    /**
     * 获取版本号是否低于4.3,低于4.3不让编辑，只能观看
     */
    public static boolean getVersion() {
        String vison = Build.VERSION.RELEASE;
        boolean canEdit = true;
        if (vison != null && Integer.parseInt(vison.substring(0, 1) + vison.substring(2, 3)) < 43) {
            canEdit = false;
        }
        return canEdit;
    }

    /**
     * 获取手机基本信息
     *
     * @return
     */
    public static HashMap<String, String> getPhoneInfo(Context context, String videoResoultion) {
        // http://www.devstore.cn/code/info/425.html
        HashMap<String, String> deviceMap = new HashMap<String, String>(1);
        String deviceModel = Build.MODEL;// 手机型号
        String osVersion = Build.VERSION.RELEASE;
        String manufacturer = Build.MANUFACTURER;// 制造商
        String display = Build.DISPLAY;// 厂商显示的版本
        int[] screenSize = getScreenSize(context);
        String deviceResoultion = screenSize[0] + "*" + screenSize[1];
        String appVersion = getVersionName(context);
        String product = Build.PRODUCT;// 产品名称
        String cpuModel = Build.CPU_ABI;// cup型号
        String brand = Build.BRAND;// 品牌
        String board = Build.BOARD;// 主板
        String deviceDriver = Build.DEVICE;// 设备驱动

        deviceMap.put("deviceModel", TextUtils.isEmpty(deviceModel) ? "-" : deviceModel);
        deviceMap.put("osVersion", TextUtils.isEmpty(osVersion) ? "-" : osVersion);
        deviceMap.put("manufacturer", TextUtils.isEmpty(manufacturer) ? "-" : manufacturer);
        deviceMap.put("display", TextUtils.isEmpty(display) ? "-" : display);
        deviceMap.put("deviceResoultion", TextUtils.isEmpty(deviceResoultion) ? "-" : deviceResoultion);
        deviceMap.put("videoResoultion", TextUtils.isEmpty(videoResoultion) ? "-" : videoResoultion);
        // deviceMap.put("appVersion", TextUtils.isEmpty(appVersion) ? "-"
        // : appVersion);
        deviceMap.put("product", TextUtils.isEmpty(product) ? "-" : product);
        deviceMap.put("cpuModel", TextUtils.isEmpty(cpuModel) ? "-" : cpuModel);
        deviceMap.put("brand", TextUtils.isEmpty(brand) ? "-" : brand);
        deviceMap.put("board", TextUtils.isEmpty(board) ? "-" : board);
        // deviceMap.put("deviceDriver", TextUtils.isEmpty(deviceDriver) ? "-"
        // : deviceDriver);

        return deviceMap;
    }

    /**
     * 获取osVersion，deviceModel，brand用于deviceToken提交
     *
     * @return
     */
    public static String getDeviceInfor() {
        // 设备操作系统版本号
        String osVersion = (Build.VERSION.RELEASE).toLowerCase();
        // 设备的型号
        String deviceModel = (Build.MODEL).toLowerCase();
        // 设备品牌
        String brand = (Build.BRAND).toLowerCase();
        String deviceInfo = osVersion + "|" + deviceModel + "|" + brand;
        return deviceInfo;
    }

    /**
     * 请求设置deviceToken
     *
     * @param context 上下文队形
     * @param stp
     */
    //    public static void setDeviceToken(final Context context, final SetDeviceTokenPresenter stp) {
    //        new Thread(new Runnable() {
    //            @Override
    //            public void run() {
    //                boolean tokenOk = false;
    //                long startTime = System.currentTimeMillis();
    //                while (!tokenOk) {
    //                    String deviceToken = JPushInterface.getRegistrationID(context);
    //                    String deviceInfo = DeviceUtil.getDeviceInfor();
    //                    if (!TextUtils.isEmpty(deviceToken)) {
    //                        tokenOk = true;
    //                        MyLogUtil.showLogI(TAG, "deviceToken=" + deviceToken + ";deviceInfo=" + deviceInfo);
    //                        stp.setDeviceToken(deviceToken, deviceInfo);
    //                        Log.i(TAG, "deviceToken=" + deviceToken);
    //                    } else {
    //                        MyLogUtil.showLogE(TAG, "DeviceToken获取失败");
    //                        // 设置超时时间，超过五分钟还没有获取到deviceToken，结束获取
    //                        if ((System.currentTimeMillis() - startTime) > 2 * 60 * 1000) {
    //                            tokenOk = true;
    //                        }
    //                        // 每次执行完，sleep1秒
    //                        try {
    //                            Thread.sleep(2000);
    //                        } catch (InterruptedException e) {
    //                            e.printStackTrace();
    //                        }
    //                    }
    //                }
    //            }
    //        }).start();
    //    }

    /**
     * 设置别名
     *
     * @param context
     */
    //    public static void setAlias(final Context context) {
    //        // 获取上一次的别名
    //
    //        if (UserUtil.getUser() != null && !TextUtils.isEmpty(UserUtil.getUser().get_id())) {
    //            String oldAlias = DictionaryUtils.getInstance().getString(Const.KEY_ALIAS);
    //            final String newAlias = UserUtil.getUser().get_id();// 新的别名
    //            if (TextUtils.isEmpty(oldAlias) || !newAlias.equals(oldAlias)) {
    //                JPushInterface.setAlias(context, newAlias, new TagAliasCallback() {
    //                    @Override
    //                    public void gotResult(int responseCode, String alias, Set<String> tags) {
    //                        if (responseCode == 0) {
    //                            DictionaryUtils.getInstance().putString(Const.KEY_ALIAS, newAlias);
    //                            Log.e("Alias", "Alias 成功置成功，别名：" + newAlias);
    //                        }
    //                    }
    //                });
    //            }
    //        }
    //    }

    /**
     * 判断是否有某获取权限
     *
     * @param permission 权限名字
     * @return
     */
    public static boolean checkPermission(Context context, String permission) {
        PackageManager packageManager = context.getPackageManager();
        boolean permissionResult = (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(permission, context.getPackageName()));
        return permissionResult;
    }

    /**
     * @param context
     * @return
     */
    //    public static Intent getIntentForPush(Context context, HashMap<String, String> customMap) {
    //        Intent intent = null;
    //        if (checkBrowser("com.rootsports.reee.activity", context)) {
    //            String type = customMap.get("type");
    //            if (type.equals(Const.PUSH_TYPE_TEXT)) {
    //                intent = new Intent(context, SystemNewsActivity.class);
    //                DictionaryUtils.getInstance().putInt(Constants.MSG_SYSTEM, 0);
    //            } else if (type.equals(Const.PUSH_TYPE_LINK)) {
    //                intent = new Intent(context, WebViewActivity.class);
    //                intent.putExtra(WebViewActivity.WEBVIEW_TITLE, "");
    //                intent.putExtra(WebViewActivity.WEBVIEW_URL, customMap.get("href"));
    //            } else if (type.equals(Const.PUSH_TYPE_FEEDBACK)) {
    //                intent = new Intent(context, FeedbackActivity.class);
    //                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //            } else if (type.equals(Const.PUSH_TYPE_POST)) {
    //                intent = new Intent(context, PlayDetailActivity.class);
    //                intent.putExtra(Const.PLAY_DETAIL_ACT_PARAMS, customMap.get("id"));
    //                intent.putExtra(Const.ACT_KEY_WHICH, Const.ACT_VALUE_POST);
    //                intent.putExtra(Const.FROM_WHICH_PAGE, "push");
    //                intent.putExtra("position", -100);
    //                intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
    //
    //            } else if (type.equals(Const.PUSH_TYPE_MATCHLIVE)) {
    //                intent = new Intent(context, PlayDetailActivity.class);
    //                intent.putExtra(Const.PLAY_DETAIL_ACT_PARAMS, customMap.get("id"));
    //                intent.putExtra(Const.ACT_KEY_WHICH, Const.ACT_VALUE_MATCH);
    //                intent.putExtra("openType", Const.LIVE_OPEN_NOTIFY);
    //
    //            } else if (type.equals(Const.PUSH_TYPE_TAGLIST)) {
    //                intent = new Intent(context, HotTagActivity.class);
    //                intent.putExtra(Const.HOT_TAG_ACT_PARAMS, customMap.get("id"));
    //                intent.putExtra("isredtag", true);
    //
    //            } else if (type.equals(Const.PUSH_TYPE_STADIUMPROFILE)) {
    //                // 暂无放在3.2版本
    //            } else if (type.equals(Const.PUSH_TYPE_NATIVEALERT)) {
    //                // 弹框
    //                // PopupWindowUtil.showJPushPopup(customMap.get("text"));
    //                intent = new Intent(context, ReeePushActivity.class);
    //                intent.putExtra("text", customMap.get("text"));
    //            } else {
    //                MyLogUtil.showLogE(TAG, "推送类型不匹配！type=" + customMap.get("type"));
    //            }
    //            // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    //            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //        } else {// 应用没有存活
    //            boolean foreground = isForeground(context, "com.rootsports.reee.activity.AccountActivity");
    //            intent = new Intent(context, foreground ? AccountActivity.class : StartActivity.class);
    //            Bundle bundle = new Bundle();
    //            // 将参数传递给指定界面
    //            bundle.putSerializable("extra", customMap);
    //            intent.putExtras(bundle);
    //            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //        }
    //        // if (intent != null) {
    //        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
    //        // Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //        // }
    //        return intent;
    //    }

    /**
     * 判断Activity是否存在
     *
     * @param packageName
     * @param context
     * @return
     */
    public static boolean checkBrowser(String packageName, Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        for (RunningTaskInfo info : list) {
            if (info.topActivity.getShortClassName().contains(packageName) && !info.topActivity.getShortClassName().contains("AccountActivity")) {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            String className2 = cpn.getClassName();
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取textview的宽度
     */
    public static float getTextWidth(Context context, String text, int textSize) {
        TextPaint paint = new TextPaint();
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float top = metrics.top;
        float bottom = metrics.bottom;
        int count = 1;
        int mixPosition = 0;//获取最小长度的位置
        if (!TextUtils.isEmpty(text)) {
            String[] content = text.split("\n");
            for (int i = 0; i < content.length; i++) {
                if (content[mixPosition].length() < content[i].length()) {
                    mixPosition = i;
                }
            }
            return paint.measureText(content[mixPosition]);
        }
        return paint.measureText(text);
    }

    /**
     * 获取textview的宽度
     */
    public static float getTextHeight(Context context, String text, int textSize) {
        TextPaint paint = new TextPaint();
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float top = metrics.top;
        float bottom = metrics.bottom;
        int count = 1;
        if (!TextUtils.isEmpty(text)) {
            count = text.split("\n").length;
        }
        return Math.round(bottom - top) * count;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    /***/
    /**
     * 设置输入法,如果当前页面输入法打开则关闭
     *
     * @param activity
     */
    public static void hideInputMethod(Activity activity, View view) {
        View a = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "键盘隐藏异常！");
            }
        }
    }


    /**
     * 强制显示输入法
     */
    public static void toggleSoftInput(Activity activity, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        } catch (Exception e) {
            Log.e(TAG, "键盘弹出异常！");
        }
    }

    /* *
     * 从Assets中读取图片
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 更换本次生命周期的Id
     */
    public static void changeLifeCycleId(String functionName) {
        String lifecycleId = UUID.randomUUID().toString().replace("-", "");
        DictionaryUtils.getInstance().putString(Const.KEY_LIFE_CYCLE_ID, lifecycleId);
        MyLogUtil.showLogI(TAG, functionName + ":Change LifecycleId  Id=" + lifecycleId + ";IdLength=" + lifecycleId.length());
    }


    /**
     * 设置为全屏状态，沉浸式模式
     *
     * @param activity
     */
    public static void dimStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int layout = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            activity.getWindow().getDecorView().setSystemUiVisibility(layout);
        }
    }

    public static float getDimen(Context context, @DimenRes int resId) {
        return context.getResources().getDimension(resId);
    }
}
