package com.dss.p4dss.settings;

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

import com.dss.p4dss.R;
import com.dss.p4dss.TiendaManager;
import com.dss.p4dss.productos.Producto;


public class ProductoDialog extends AppCompatDialogFragment {
    EditText nombreProductoInput;
    EditText precioInput;
    EditText familiaInput;


    ProductoDialog(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.product_dialog, null);

        String title = "Crear producto";

        builder.setView(view).setTitle(title).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        }).setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TiendaManager.getInstance().agregarProducto(new Producto(
                        nombreProductoInput.getText().toString(),
                        familiaInput.getText().toString(),
                        Float.parseFloat(precioInput.getText().toString())
                ));
            }
        });

        nombreProductoInput = view.findViewById(R.id.nombreProductoInput);
        precioInput = view.findViewById(R.id.precioInput);
        familiaInput = view.findViewById(R.id.familiaInput);

        return builder.create();
    }
}

