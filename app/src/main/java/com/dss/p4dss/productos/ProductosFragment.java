package com.dss.p4dss.productos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dss.p4dss.R;
import com.dss.p4dss.TiendaManager;

import java.util.ArrayList;


public class ProductosFragment extends Fragment {
    RecyclerView productosRecyclerView;

    public ProductosFragment() {
    }

    public static ProductosFragment newInstance() {
        ProductosFragment fragment = new ProductosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_productos, container, false);

        productosRecyclerView = root.findViewById(R.id.productosRecyclerView);
        productosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TiendaManager.getInstance().fetchProductos();

        updateUI();
        return root;
    }

    public void updateUI() {
        ArrayList<Producto> productos = TiendaManager.getInstance().getProductos();
        ProductosAdapter adapter = new ProductosAdapter(getContext(), productos);
        adapter.setOnItemClickListener(new ProductosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<Producto> productos = TiendaManager.getInstance().getProductos();
                TiendaManager.getInstance().agregarProductoACarrito(productos.get(position));
            }
        });

        productosRecyclerView.setAdapter(adapter);
    }
}
