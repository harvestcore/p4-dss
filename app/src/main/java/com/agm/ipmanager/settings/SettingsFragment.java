package com.agm.ipmanager.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.R;

public class SettingsFragment extends Fragment {
    EditText serverNameInput;
    EditText updateIntervalEditText;
    Button setServerNameButton;
    Button setServerCredentialsButton;
    Button setUpdateIntervalButton;

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

        // Server name
        serverNameInput = root.findViewById(R.id.serverNameInput);

        if (IPManager.getInstance().hasServerName()) {
            serverNameInput.setText(IPManager.getInstance().getServerName());
        }

        setServerNameButton = root.findViewById(R.id.setServerNameButton);
        setServerNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPManager.getInstance().saveServerName(serverNameInput.getText().toString());
            }
        });

        // Credentials dialog
        setServerCredentialsButton = root.findViewById(R.id.setServerCredentialsButton);
        setServerCredentialsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CredentialsDialog credentialsDialog = new CredentialsDialog();
                credentialsDialog.show(getActivity().getSupportFragmentManager(), "Credentials");
            }
        });

        // Set interval
        updateIntervalEditText = root.findViewById(R.id.updateIntervalEditText);
        updateIntervalEditText.setText(Integer.toString(IPManager.getInstance().getUpdateInterval()));

        setUpdateIntervalButton = root.findViewById(R.id.setUpdateIntervalButton);
        setUpdateIntervalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = 30;
                String strValue = updateIntervalEditText.getText().toString();
                if (!strValue.isEmpty()) {
                    value = Integer.parseInt(strValue);
                    if (value == 0 || value < 5) {
                        value = 5;
                    }
                }

                IPManager.getInstance().setUpdateInterval(value);
                updateIntervalEditText.setText(Integer.toString(value));
            }
        });

        return root;
    }
}
