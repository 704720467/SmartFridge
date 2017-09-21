package com.smartfridge.mvp.module;

import android.content.Context;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;


/**
 * Created by zp on 2017/6/15.
 */
public class JobModule {

    private JobManager jobManager;

    public JobModule(Context context) {
        init(context);
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    private void init(Context context) {
        jobManager = new JobManager(context, getConfiguration(context));
    }

    private Configuration getConfiguration(Context context) {
        return new Configuration.Builder(context)
                .minConsumerCount(1)    // always keep at least one consumer alive
                .maxConsumerCount(3)    // up to 3 consumers at NextOnEditorActionListener time
                .loadFactor(3)          // 3 jobs per consumer
                .consumerKeepAlive(120) // wait 2 minutes
                .build();
    }
}
