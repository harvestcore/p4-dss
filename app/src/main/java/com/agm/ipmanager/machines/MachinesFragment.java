package com.agm.ipmanager.machines;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.R;

public class MachinesFragment extends Fragment {
    RecyclerView machinesRecyclerView;
    Button addMachineButton;
    Button refreshMachinesButton;

    public MachinesFragment() {
    }

    public static MachinesFragment newInstance() {
        MachinesFragment fragment = new MachinesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_machines, container, false);

        machinesRecyclerView = root.findViewById(R.id.machinesRecyclerView);
        machinesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MachinesAdapter adapter = new MachinesAdapter(getContext(), IPManager.getInstance().getMachines());
        adapter.setOnItemClickListener(new MachinesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                clickItem(position);
            }
        });
        machinesRecyclerView.setAdapter(adapter);

        addMachineButton = root.findViewById(R.id.addMachineButton);
        addMachineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MachineDialog machineDialog = new MachineDialog(null);
                machineDialog.show(getActivity().getSupportFragmentManager(), "Machine");
            }
        });

        refreshMachinesButton = root.findViewById(R.id.refreshMachinesButton);
        refreshMachinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPManager.getInstance().updateMachines();
                machinesRecyclerView.setAdapter(new MachinesAdapter(getContext(), IPManager.getInstance().getMachines()));
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        IPManager.getInstance().updateMachines();
        MachinesAdapter adapter = new MachinesAdapter(getContext(), IPManager.getInstance().getMachines());
        adapter.setOnItemClickListener(new MachinesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                clickItem(position);
            }
        });
        machinesRecyclerView.setAdapter(adapter);
    }

    private void clickItem(int position) {
        System.out.println("POSITON" + position);
        Machine m = IPManager.getInstance().getMachine(position);
        MachineDialog machineDialog = new MachineDialog(m);
        machineDialog.show(getActivity().getSupportFragmentManager(), "Machine");
    }
}
