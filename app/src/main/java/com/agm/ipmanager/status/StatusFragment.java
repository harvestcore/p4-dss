package com.agm.ipmanager.status;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agm.ipmanager.EventManager;
import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.R;
import com.agm.ipmanager.Service;

public class StatusFragment extends Fragment {
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
        EventsAdapter eventsAdapter = new EventsAdapter(getContext(), EventManager.getInstance().getEvents());
        statusRecyclerView.setAdapter(eventsAdapter);

        // Set server status
        serverStatusText = root.findViewById(R.id.serverStatusText);
        String serverName = IPManager.getInstance().getServerName();
        serverStatusText.setText(serverName.isEmpty() ? "Server status" : serverName + " status");

        // Mongo service status icon
        mongoStatusImage = root.findViewById(R.id.mongoStatusImage);
        boolean mongoStatus = IPManager.getInstance().getServiceStatus(Service.MONGO);
        mongoStatusImage.setImageResource(mongoStatus ? R.drawable.up : R.drawable.down);

        // Docker service status icon
        dockerStatusImage = root.findViewById(R.id.dockerStatusImage);
        boolean dockerStatus = IPManager.getInstance().getServiceStatus(Service.MONGO);
        mongoStatusImage.setImageResource(dockerStatus ? R.drawable.up : R.drawable.down);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        statusRecyclerView.setAdapter(new EventsAdapter(getContext(), EventManager.getInstance().getEvents()));
    }
}
