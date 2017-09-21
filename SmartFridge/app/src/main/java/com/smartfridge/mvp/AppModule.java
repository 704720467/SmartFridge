package com.smartfridge.mvp;

import android.content.Context;

import com.smartfridge.mvp.module.BusModule;
import com.smartfridge.mvp.module.InteractorModule;
import com.smartfridge.mvp.module.JobModule;
import com.smartfridge.mvp.module.NetworkExecutor;
import com.smartfridge.mvp.network.HttpService;
import com.smartfridge.mvp.network.HttpsService;

/**
 * Created by zp on 2017/6/15.
 */
public class AppModule {

    private static AppModule module;
    private Context context;
    private BusModule bus;
    private NetworkExecutor data;
    private JobModule job;
    private InteractorModule interactor;

    private AppModule() {

    }

    public static AppModule getInstance() {
        if (module == null) {
            module = new AppModule();
        }
        return module;
    }

    public void init(Context context) {
        this.context = context;
        this.bus = new BusModule();
        this.data = new NetworkExecutor(context);
        this.job = new JobModule(context);
        this.interactor = new InteractorModule(job, bus);
    }

    public BusModule getBus() {
        return bus;
    }

    public JobModule getJob() {
        return job;
    }

    public NetworkExecutor getData() {
        return data;
    }

    public HttpService getHttp() {
        return data.httpService;
    }

    public HttpsService getHttps() {
        return data.httpsService;
    }

    public Context getContext() {
        return context;
    }

    public InteractorModule getInteractor() {
        return interactor;
    }
}
