package com.dss.p4dss.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.dss.p4dss.R;
import com.dss.p4dss.TiendaManager;
import com.dss.p4dss.credentials.Credentials;


public class UsuarioDialog extends AppCompatDialogFragment {
    EditText nombreUsuarioInput;
    EditText usernameUsuarioInput;
    EditText passwordUsuarioInput;
    Switch adminSwitch;

    UsuarioDialog(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.user_dialog, null);

        String title = "Crear usuario";

        builder.setView(view).setTitle(title).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        }).setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TiendaManager.getInstance().agregarUsuario(new Credentials(
                        nombreUsuarioInput.getText().toString(),
                        usernameUsuarioInput.getText().toString(),
                        passwordUsuarioInput.getText().toString(),
                        adminSwitch.isChecked()
                ));
            }
        });

        nombreUsuarioInput = view.findViewById(R.id.nombreProductoInput);
        usernameUsuarioInput = view.findViewById(R.id.precioInput);
        passwordUsuarioInput = view.findViewById(R.id.familiaInput);
        adminSwitch = view.findViewById(R.id.adminSwitch);
        adminSwitch.setChecked(false);

        return builder.create();
    }
}
