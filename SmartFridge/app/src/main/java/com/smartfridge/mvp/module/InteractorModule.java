package com.smartfridge.mvp.module;


import com.smartfridge.mvp.interactor.Interactor;
import com.smartfridge.mvp.interactor.InteractorPriority;

/**
 * Created by zp on 2017/6/15.
 */
public class InteractorModule {

    private JobModule jobModule;
    private BusModule bus;

    public InteractorModule(JobModule job, BusModule bus) {
        this.jobModule = job;
        this.bus = bus;
    }

    public void execute(Interactor interactor) {
        this.execute(interactor, InteractorPriority.LOW);
    }

    public void execute(Interactor interactor, InteractorPriority priority) {
        jobModule.getJobManager().addJob(new InteractorWrapper(interactor, priority, bus));
    }
}
