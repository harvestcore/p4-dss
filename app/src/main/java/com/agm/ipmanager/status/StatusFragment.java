package com.agm.ipmanager.status;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agm.ipmanager.events.EventManager;
import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.R;
import com.agm.ipmanager.Service;

import java.util.HashMap;

public class StatusFragment extends Fragment {
    BroadcastReceiver updateReceiver;

    RecyclerView statusRecyclerView;
    TextView serverStatusText;
    ImageView mongoStatusImage;
    ImageView dockerStatusImage;

    public static StatusFragment newInstance() {
        StatusFragment fragment = new StatusFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_status, container, false);

        // Events recyclerView
        statusRecyclerView = root.findViewById(R.id.eventsRecyclerView);
        statusRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        EventsAdapter eventsAdapter = new EventsAdapter(getContext(), IPManager.getInstance().getEvents());
        statusRecyclerView.setAdapter(eventsAdapter);

        // Set server status
        serverStatusText = root.findViewById(R.id.serverStatusText);
        String serverName = IPManager.getInstance().getServerName();
        serverStatusText.setText(serverName.isEmpty() ? "Server status" : serverName);

        // Mongo service status icon
        mongoStatusImage = root.findViewById(R.id.mongoStatusImage);

        // Docker service status icon
        dockerStatusImage = root.findViewById(R.id.dockerStatusImage);

        IPManager.getInstance().statusChangedNotifier.addCallback(new Function() {
            @Override
            public Object apply(Object input) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StatusFragment.this.updateUI();
                    }
                });

                return null;
            }
        });

        this.updateUI();
        return root;
    }

    public void updateUI() {
        // Server online?
        HashMap<Service, Boolean> servicesStatus = IPManager.getInstance().getServicesStatus();
        boolean isOnline = (servicesStatus == null || servicesStatus.size() == 0) ? false : true;


        if (isOnline) {
            dockerStatusImage.setImageResource(servicesStatus.get(Service.DOCKER) ? R.drawable.up : R.drawable.down);
            mongoStatusImage.setImageResource(servicesStatus.get(Service.MONGO) ? R.drawable.up : R.drawable.down);
        } else {
            dockerStatusImage.setImageResource(R.drawable.offline);
            mongoStatusImage.setImageResource(R.drawable.offline);
        }

        statusRecyclerView.setAdapter(new EventsAdapter(getContext(), IPManager.getInstance().getEvents()));
    }
}
