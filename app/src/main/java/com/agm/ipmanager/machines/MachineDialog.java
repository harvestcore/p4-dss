package com.agm.ipmanager.machines;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.R;
import com.agm.ipmanager.credentials.Credentials;
import com.agm.ipmanager.events.Event;
import com.agm.ipmanager.events.EventType;

public class MachineDialog extends AppCompatDialogFragment {
    EditText nameInput;
    EditText ipv4Input;
    EditText ipv6Input;
    EditText macInput;
    Switch localSwitch;
    Switch remoteSwitch;

    Machine m;

    MachineDialog(Machine m){
        if (m != null) {
            this.m = m;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.machines_dialog, null);

        String title = m == null ? "Create machine" : "See machine";

        builder.setView(view).setTitle(title).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String type = localSwitch.isChecked() ? "local" : "remote";
                IPManager.getInstance().addMachine(new Machine(
                    nameInput.getText().toString(),
                    type,
                    ipv4Input.getText().toString(),
                    ipv6Input.getText().toString(),
                    macInput.getText().toString()
                ));
            }
        });

        nameInput = view.findViewById(R.id.nameInput);
        ipv4Input = view.findViewById(R.id.ipv4Input);
        ipv6Input = view.findViewById(R.id.ipv6Input);
        macInput = view.findViewById(R.id.macInput);
        localSwitch = view.findViewById(R.id.localSwitch);
        remoteSwitch = view.findViewById(R.id.remoteSwitch);

        localSwitch.setChecked(true);

        localSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localSwitch.setChecked(isChecked);
                remoteSwitch.setChecked(!isChecked);
            }
        });

        remoteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                remoteSwitch.setChecked(isChecked);
                localSwitch.setChecked(!isChecked);
            }
        });

        if (this.m != null) {
            nameInput.setText(m.name);
            ipv4Input.setText(m.ipv4);
            ipv6Input.setText(m.ipv6);
            macInput.setText(m.mac);
            localSwitch.setChecked(m.type.equals("local"));
            remoteSwitch.setChecked(m.type.equals("remote"));
        }

        return builder.create();
    }
}
