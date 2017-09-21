package com.smartfridge.mvp.presenter;

import com.smartfridge.mvp.AppModule;
import com.smartfridge.mvp.interactor.Interactor;
import com.smartfridge.mvp.module.BusModule;
import com.smartfridge.mvp.module.InteractorModule;


/**
 * Created by zp on 2017/6/15.
 */
public abstract class Presenter<V extends PresenterView> {

    protected V view;
    BusModule bus;
    InteractorModule executor;


    public Presenter(V view) {

        this.view = view;
        this.bus = AppModule.getInstance().getBus();
        this.executor = AppModule.getInstance().getInteractor();
    }

    public void onExecute(Interactor interactor) {
        executor.execute(interactor);
    }


    public void onResume() {
        if (!bus.isRegistered(this)) {
            bus.register(this);
        }
    }

    public void onPause() {
        bus.unregister(this);
    }


}
