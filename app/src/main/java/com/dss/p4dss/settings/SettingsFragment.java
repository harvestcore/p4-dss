package com.dss.p4dss.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dss.p4dss.R;
import com.dss.p4dss.TiendaManager;


public class SettingsFragment extends Fragment {
    Button loginButton;
    Button agregarProductoButton;
    Button agregarUsuarioButton;

    EditText credentialsUsername;
    EditText credentialsPassword;

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

        credentialsUsername = root.findViewById(R.id.credentialsUsername);
        credentialsPassword = root.findViewById(R.id.credentialsPassword);

        loginButton = root.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TiendaManager.getInstance().login(credentialsUsername.getText().toString(), credentialsPassword.getText().toString());
            }
        });

        agregarProductoButton = root.findViewById(R.id.agregarProductoButton);
        agregarProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductoDialog productoDialog = new ProductoDialog();
                productoDialog.show(getActivity().getSupportFragmentManager(), "Producto");
            }
        });

        agregarUsuarioButton = root.findViewById(R.id.agregarUsuarioButton);
        agregarUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioDialog usuarioDialog = new UsuarioDialog();
                usuarioDialog.show(getActivity().getSupportFragmentManager(), "Usuario");
            }
        });

        return root;
    }
}
