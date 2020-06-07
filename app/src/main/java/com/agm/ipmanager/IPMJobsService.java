package com.agm.ipmanager;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.agm.ipmanager.events.Event;
import com.agm.ipmanager.events.EventType;

public class IPMJobsService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        this.updateIPManager(params);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private void updateIPManager(JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (IPManager.getInstance().isOnline()) {
                        IPManager.getInstance().recalculateServicesStatus();
                        IPManager.getInstance().updateMachines();
                    }

                    if (!IPManager.getInstance().hasServerName()) {
                        IPManager.getInstance().restoreServerName();
                    }

                    try {
                        // Wait 1000 ms so API calls can be performed and the data can be set
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        try {
                            // Execute all callbacks that depend on this service
                            IPManager.getInstance().statusChangedNotifier.executeCallbacks();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(1000 * (IPManager.getInstance().getUpdateInterval() - 1));
                        IPManager.getInstance().addEvent(new Event(EventType.SERVER_UPDATE, "Server status updated"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
