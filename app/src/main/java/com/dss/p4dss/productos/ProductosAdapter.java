package com.dss.p4dss.productos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.p4dss.R;

import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.Holder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Producto> data;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ProductosAdapter(Context context, ArrayList<Producto> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.fragment_producto_row, parent, false);
        return new Holder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Producto producto = data.get(position);
        holder.infoProducto.setText(producto.toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class Holder extends RecyclerView.ViewHolder {
        ConstraintLayout productoRowLayout;
        TextView infoProducto;


        public Holder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            productoRowLayout = itemView.findViewById(R.id.productoRowLayout);
            infoProducto = itemView.findViewById(R.id.productoInfo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
