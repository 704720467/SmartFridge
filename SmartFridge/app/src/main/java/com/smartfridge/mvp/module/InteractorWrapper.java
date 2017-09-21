package com.smartfridge.mvp.module;


import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import com.smartfridge.mvp.interactor.Interactor;
import com.smartfridge.mvp.interactor.InteractorCanBackError;
import com.smartfridge.mvp.interactor.InteractorPriority;
import com.smartfridge.mvp.network.RetroritErrorHandler;


/**
 * Created by zp on 2017/6/15.
 */
public class InteractorWrapper extends Job {

    private Interactor interactor;
    private BusModule bus;

    public InteractorWrapper(Interactor interactor, InteractorPriority priority, BusModule bus) {
        super(new Params(priority.getValue()));
        this.interactor = interactor;
        this.bus = bus;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Object event = interactor.invoke();
        if (event != null)
            RetroritErrorHandler.tokenOverdue = false;
        bus.post(event);
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        throwable.printStackTrace();
        bus.postError(((InteractorCanBackError) interactor).getErrorEvent());
        return false;
    }
}
