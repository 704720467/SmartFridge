package com.smartfridge.mvp.module;

import android.os.Handler;
import android.os.Looper;

import com.smartfridge.R;
import com.smartfridge.application.MyApplication;
import com.smartfridge.mvp.event.NetEvent;
import com.smartfridge.util.ToastUtil;

import de.greenrobot.event.EventBus;


/**
 * Created by zp on 2017/6/15.
 */
public class BusModule extends EventBus {

    Handler mainThread = new Handler(Looper.getMainLooper());

    public void post(final Object event) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                BusModule.super.post(event);
            }
        });
    }

    public void showError(final int resId) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showDefaultSuperToast(MyApplication.getAppContext(), resId);
            }
        });
    }

    public void postTimeOut() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showDefaultSuperToast(MyApplication.getAppContext(), R.string.timeout_msg);
            }
        });
    }

    public void reLogin(final int resId) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showDefaultSuperToast(MyApplication.getAppContext(), resId);
            }
        });
    }

    public void postError(final Object event) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                if (event == null)
                    return;
                ((NetEvent) event).setCode(0);
                ((NetEvent) event).setData(null);
                ((NetEvent) event).setMessage("网络报错，检查网络！");
                BusModule.super.post(event);
            }
        });

    }
}
