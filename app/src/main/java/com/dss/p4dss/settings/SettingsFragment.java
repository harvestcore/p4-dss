package com.dss.p4dss.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dss.p4dss.R;


public class SettingsFragment extends Fragment {
    Button agregarProductoButton;


    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        agregarProductoButton = root.findViewById(R.id.agregarProductoButton);
        agregarProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductoDialog productoDialog = new ProductoDialog();
                productoDialog.show(getActivity().getSupportFragmentManager(), "Producto");
            }
        });

        return root;
    }
}
