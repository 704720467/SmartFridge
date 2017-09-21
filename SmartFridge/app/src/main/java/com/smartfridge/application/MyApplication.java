package com.smartfridge.application;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.smartfridge.mvp.AppModule;
import com.smartfridge.util.Constants;
import com.smartfridge.util.DictionaryUtils;
import com.smartfridge.util.MyLogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;


public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static int NOTIFICATION_ID_COURT = 111;
    public static final int NOTIFICATION_ID_SIGN = 112;
    private static MyApplication app;
    public static boolean isForceToPlay = false;
    public static boolean isForceToLoad = false;
    public static int count = 0;
    private int activityCount;//activity的count数
    private boolean isForeground;//是否在前台
    private Date inBackgroundTime;//进入后台的时间


    public synchronized static MyApplication getAppContext() {
        return app;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initData();//重置数据
        AppModule.getInstance().init(this);
    }

    /**
     * 重置启动数据
     */
    private void initData() {
        DictionaryUtils.getInstance().putString(Constants.LOCALTION_LONG, "");
        DictionaryUtils.getInstance().putString(Constants.LOCALTION_LAT, "");
        DictionaryUtils.getInstance().putBoolean(Constants.LOCALTION_REFRESH, false);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
                MyLogUtil.showLogI(TAG, "新开启了个Activity，总个数为：" + activityCount);
                checkCanChangeLifecycleId();
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                MyLogUtil.showLogI(TAG, "停止了一个Activity，总个数为：" + activityCount);
                if (0 == activityCount) {
                    isForeground = false;
                    inBackgroundTime = new Date(System.currentTimeMillis());
                    MyLogUtil.showLogI(TAG, "程序进入了后台时间是" + inBackgroundTime);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (!isForeground && 0 == activityCount) {
                    MyLogUtil.showLogI(TAG, "onActivityDestroyed 应用被杀死了，重新更换生命周期函数！");
                }
            }
        });
    }

    /**
     * 检测是否更换生命周期Id
     */
    private void checkCanChangeLifecycleId() {
        if (inBackgroundTime != null) {
            Date newTime = new Date(System.currentTimeMillis());
            long inBgtime = inBackgroundTime.getTime();
            long outBgTime = newTime.getTime();
            long diff = outBgTime - inBgtime;
            inBackgroundTime = null;
        }
    }


    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            //e.printStackTrace();
            return Constants.OS_VERSION;
        }
    }
}
