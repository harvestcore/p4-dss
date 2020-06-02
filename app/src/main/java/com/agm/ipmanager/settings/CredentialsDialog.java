package com.agm.ipmanager.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.credentials.Credentials;
import com.agm.ipmanager.R;

public class CredentialsDialog extends AppCompatDialogFragment {
    EditText credentialsUsername;
    EditText credentialsPassword;
    EditText credentialsHostname;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.credentials_dialog, null);

        builder.setView(view).setTitle("Set credentials").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Credentials credentials = new Credentials(
                    credentialsHostname.getText().toString(),
                    credentialsUsername.getText().toString(),
                    credentialsPassword.getText().toString()
                );

                IPManager.getInstance().saveCredentials(credentials);
                IPManager.getInstance().login();
            }
        });

        credentialsUsername = view.findViewById(R.id.credentialsUsername);
        credentialsPassword = view.findViewById(R.id.credentialsPassword);
        credentialsHostname = view.findViewById(R.id.nameInput);

        if (IPManager.getInstance().hasCredentials()) {
            Credentials credentials = IPManager.getInstance().getCredentials();
            credentialsUsername.setText(credentials.username);
            credentialsPassword.setText(credentials.password);
            credentialsHostname.setText(credentials.hostname);
        }

        return builder.create();
    }
}
