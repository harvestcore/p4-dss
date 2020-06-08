package com.agm.ipmanager.deploys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.arch.core.util.Function;

import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.R;

import java.util.ArrayList;

public class ImagesDialog extends AppCompatDialogFragment {
    Function f;

    Spinner imagesSpinner;
    Button runImageButton;

    ImagesDialog(){}

    public void setOnSubmitCallback(Function f) {
        this.f = f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.images_dialog, null);

        String title = "Run images";

        builder.setView(root).setTitle(title).setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (f != null) {
                    f.apply(null);
                }
            }
        });

        // Images spinner
        imagesSpinner = root.findViewById(R.id.imagesSpinner);

        ArrayList<Image> images = IPManager.getInstance().getImages();
        ArrayAdapter<Image> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, images);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imagesSpinner.setAdapter(adapter);

        // Run button
        runImageButton = root.findViewById(R.id.runImageButton);
        runImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Image selected = (Image) imagesSpinner.getSelectedItem();
                if (selected != null) {
                    IPManager.getInstance().runImage(selected);
                }
            }
        });


        return builder.create();
    }
}
