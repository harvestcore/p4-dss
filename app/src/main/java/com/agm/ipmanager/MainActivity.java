package com.agm.ipmanager;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.net.Credentials;
import android.os.Bundle;
import android.view.View;

import com.agm.ipmanager.API.APIConnector;
import com.agm.ipmanager.credentials.CredentialsManager;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.agm.ipmanager.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int IPM_SERVICE_ID = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // Initialize managers
        APIConnector.getInstance().setContext(getBaseContext());
        CredentialsManager.getInstance().setView(findViewById(android.R.id.content).getRootView());

        // Run scheduled job
        scheduleJob();
    }

    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, IPMJobsService.class);
        JobInfo info = new JobInfo.Builder(IPM_SERVICE_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15 * 60000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(info);
    }

    public void cancelJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(IPM_SERVICE_ID);
    }
}