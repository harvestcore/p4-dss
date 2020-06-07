package com.agm.ipmanager.deploys;

import android.os.Bundle;

import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.R;
import com.agm.ipmanager.machines.MachinesFragment;

import java.util.ArrayList;


public class DeploysFragment extends Fragment {
    RecyclerView deploysRecyclerView;
    Button runContainerButton;
    Button refreshContainerButton;
    Button pruneContainerButton;

    public DeploysFragment() {
    }

    public static DeploysFragment newInstance() {
        DeploysFragment fragment = new DeploysFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_deploys, container, false);

        deploysRecyclerView = root.findViewById(R.id.deploysRecyclerView);
        deploysRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        runContainerButton = root.findViewById(R.id.runContainerButton);
        runContainerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        refreshContainerButton = root.findViewById(R.id.refreshContainerButton);
        refreshContainerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncUpdate();
            }
        });

        pruneContainerButton = root.findViewById(R.id.pruneContainerButton);
        pruneContainerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPManager.getInstance().pruneContainers();
                syncUpdate();
            }
        });

        IPManager.getInstance().statusChangedNotifier.addCallback(new Function() {
            @Override
            public Object apply(Object input) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DeploysFragment.this.updateUI();
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
                    IPManager.getInstance().updateContainers();
                    Thread.sleep(350);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DeploysFragment.this.updateUI();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void clickItem(int position) {
        Container c = IPManager.getInstance().getContainers().get(position);
        ContainerDialog containerDialog = new ContainerDialog(c);
        containerDialog.show(getActivity().getSupportFragmentManager(), "Container");
        containerDialog.setOnSubmitCallback(new Function() {
            @Override
            public Object apply(Object input) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DeploysFragment.this.syncUpdate();
                    }
                });
                return null;
            }
        });

    }

    public void updateUI() {
        ArrayList<Container> containers = IPManager.getInstance().getContainers();
        DeploysAdapter adapter = new DeploysAdapter(getContext(), containers);
        adapter.setOnItemClickListener(new DeploysAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                clickItem(position);
            }
        });

        deploysRecyclerView.setAdapter(adapter);
    }
}
