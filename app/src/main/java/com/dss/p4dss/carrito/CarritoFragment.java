package com.dss.p4dss.carrito;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dss.p4dss.R;

public class CarritoFragment extends Fragment {
//    RecyclerView machinesRecyclerView;
//    Button addMachineButton;
//    Button refreshMachinesButton;
//    TextView machinesInfo;

    public CarritoFragment() {
    }

    public static CarritoFragment newInstance() {
        CarritoFragment fragment = new CarritoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_machines, container, false);

//        addMachineButton = root.findViewById(R.id.addMachineButton);
//        addMachineButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MachineDialog machineDialog = new MachineDialog(null);
//                machineDialog.show(getActivity().getSupportFragmentManager(), "Machine");
//                machineDialog.setOnSubmitCallback(new Function() {
//                    @Override
//                    public Object apply(Object input) {
//
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                MachinesFragment.this.syncUpdate();
//                            }
//                        });
//
//                        return null;
//                    }
//                });
//            }
//        });
//
//        refreshMachinesButton = root.findViewById(R.id.refreshMachinesButton);
//        refreshMachinesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                syncUpdate();
//            }
//        });
//
//        machinesInfo = root.findViewById(R.id.machinesInfo);
//
//        machinesRecyclerView = root.findViewById(R.id.machinesRecyclerView);
//        machinesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        TiendaManager.getInstance().statusChangedNotifier.addCallback(new Function() {
//            @Override
//            public Object apply(Object input) {
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        MachinesFragment.this.updateUI();
//                    }
//                });
//
//                return null;
//            }
//        });
//
//        this.syncUpdate();
        return root;
    }
}
