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
import com.agm.ipmanager.credentials.Credentials;
import com.agm.ipmanager.events.Event;
import com.agm.ipmanager.events.EventType;

public class SettingsFragment extends Fragment {
    EditText serverNameInput;
    EditText updateIntervalEditText;
    Button saveSettingsButton;

    EditText credentialsUsername;
    EditText credentialsPassword;
    EditText credentialsHostname;

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

        saveSettingsButton = root.findViewById(R.id.saveSettingsButton);
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Server name
                IPManager.getInstance().saveServerName(serverNameInput.getText().toString());

                // Credentials
                Credentials credentials = new Credentials(
                        credentialsHostname.getText().toString(),
                        credentialsUsername.getText().toString(),
                        credentialsPassword.getText().toString()
                );

                IPManager.getInstance().saveCredentials(credentials);
                IPManager.getInstance().login();


                // Update interval
                int value = 15;
                String strValue = updateIntervalEditText.getText().toString();
                if (!strValue.isEmpty()) {
                    value = Integer.parseInt(strValue);
                    if (value == 0 || value < 5) {
                        value = 5;
                    }
                }

                IPManager.getInstance().setUpdateInterval(value);
                updateIntervalEditText.setText(Integer.toString(value));

                IPManager.getInstance().addEvent(new Event(EventType.SETTINGS, "Settings updated"));
            }
        });

        // Set interval
        updateIntervalEditText = root.findViewById(R.id.updateIntervalEditText);
        updateIntervalEditText.setText(Integer.toString(IPManager.getInstance().getUpdateInterval()));

        credentialsUsername = root.findViewById(R.id.credentialsUsername);
        credentialsPassword = root.findViewById(R.id.credentialsPassword);
        credentialsHostname = root.findViewById(R.id.hostnameInput);

        if (IPManager.getInstance().hasCredentials()) {
            Credentials credentials = IPManager.getInstance().getCredentials();
            credentialsUsername.setText(credentials.username);
            credentialsPassword.setText(credentials.password);
            credentialsHostname.setText(credentials.hostname);
        }

        return root;
    }
}
