package com.agm.ipmanager.status;

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

import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.R;
import com.agm.ipmanager.Service;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class StatusFragment extends Fragment {
    RecyclerView statusRecyclerView;
    TextView serverStatusText;
    ImageView mongoStatusImage;
    ImageView dockerStatusImage;

    TextView imagesStatus;
    TextView totalContainersStatus;
    TextView containersRunning;
    TextView containersStopped;
    TextView containersPaused;

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

        // Containers/images labels
        imagesStatus = root.findViewById(R.id.imagesStatus);
        totalContainersStatus = root.findViewById(R.id.totalContainersStatus);
        containersRunning = root.findViewById(R.id.containersRunning);
        containersStopped = root.findViewById(R.id.containersStopped);
        containersPaused = root.findViewById(R.id.containersPaused);


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

        this.syncUpdate();
        return root;
    }

    private void syncUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (IPManager.getInstance().getServicesStatus() == null) {
                        Thread.sleep(1500);
                    }

                    IPManager.getInstance().recalculateServicesStatus();
                    // Wait for data to be set
                    Thread.sleep(500);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            StatusFragment.this.updateUI();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

        // Containers/images stuff
        JSONObject dockerdata = IPManager.getInstance().getDockerStatus();

        if (dockerdata != null) {
            try {
                JSONObject info = dockerdata.getJSONObject("info");
                if (info != null) {
                    imagesStatus.setText("Images: " + info.getInt("Images"));
                    totalContainersStatus.setText("Containers: " + info.getInt("Containers"));
                    containersRunning.setText("Running: " + info.getInt("ContainersRunning"));
                    containersStopped.setText("Stopped: " + info.getInt("ContainersStopped"));
                    containersPaused.setText("Paused: " + info.getInt("ContainersPaused"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        statusRecyclerView.setAdapter(new EventsAdapter(getContext(), IPManager.getInstance().getEvents()));
    }
}
