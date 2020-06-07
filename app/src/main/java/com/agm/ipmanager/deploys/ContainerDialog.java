package com.agm.ipmanager.deploys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.arch.core.util.Function;

import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.R;

public class ContainerDialog extends AppCompatDialogFragment {
    Function f;
    Container c;

    Button pauseButton;
    Button unpauseButton;
    Button restartButton;
    Button reloadButton;
    Button killButton;
    Button stopButton;

    ContainerDialog(Container c){
        if (c != null) {
            this.c = c;
        }
    }

    public void setOnSubmitCallback(Function f) {
        this.f = f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.container_dialog, null);

        String title = "Manage container";

        builder.setView(root).setTitle(title).setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            if (f != null) {
                f.apply(null);
            }
            }
        });

        // Buttons
        pauseButton = root.findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPManager.getInstance().runContainerOperation(c, "pause");
            }
        });

        unpauseButton = root.findViewById(R.id.unpauseButton);
        unpauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPManager.getInstance().runContainerOperation(c, "unpause");
            }
        });

        restartButton = root.findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPManager.getInstance().runContainerOperation(c, "restart");
            }
        });

        reloadButton = root.findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPManager.getInstance().runContainerOperation(c, "reload");
            }
        });

        killButton = root.findViewById(R.id.killButton);
        killButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPManager.getInstance().runContainerOperation(c, "kill");
            }
        });

        stopButton = root.findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IPManager.getInstance().runContainerOperation(c, "stop");
            }
        });

        return builder.create();
    }
}
