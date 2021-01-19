package com.dss.p4dss.carrito;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dss.p4dss.R;
import com.dss.p4dss.TiendaManager;
import com.dss.p4dss.productos.Producto;

import java.util.ArrayList;

public class CarritoFragment extends Fragment {
    RecyclerView carritoRecyclerView;

    public CarritoFragment() {
    }

    public static CarritoFragment newInstance() {
        CarritoFragment fragment = new CarritoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_carrito, container, false);

        carritoRecyclerView = root.findViewById(R.id.carritoRecyclerView);
        carritoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TiendaManager.getInstance().fetchCarrito();

        updateUI();
        return root;
    }

    public void updateUI() {
        ArrayList<Producto> carrito = TiendaManager.getInstance().getCarrito();
        CarritoAdapter adapter = new CarritoAdapter(getContext(), carrito);
        adapter.setOnItemClickListener(new CarritoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<Producto> productos = TiendaManager.getInstance().getCarrito();
                TiendaManager.getInstance().borrarProductoDeCarrito(productos.get(position));
            }
        });

        carritoRecyclerView.setAdapter(adapter);
    }
}
