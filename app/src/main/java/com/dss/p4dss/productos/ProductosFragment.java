package com.dss.p4dss.productos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dss.p4dss.R;


public class ProductosFragment extends Fragment {
//    RecyclerView deploysRecyclerView;
//    Button runContainerButton;
//    Button refreshContainerButton;
//    Button pruneContainerButton;

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
        View root = inflater.inflate(R.layout.fragment_deploys, container, false);

//        deploysRecyclerView = root.findViewById(R.id.deploysRecyclerView);
//        deploysRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        runContainerButton = root.findViewById(R.id.runContainerButton);
//        runContainerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImagesDialog imagesDialog = new ImagesDialog();
//                imagesDialog.show(getActivity().getSupportFragmentManager(), "Images");
//                imagesDialog.setOnSubmitCallback(new Function() {
//                    @Override
//                    public Object apply(Object input) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ProductosFragment.this.syncUpdate();
//                            }
//                        });
//                        return null;
//                    }
//                });
//            }
//        });
//
//        refreshContainerButton = root.findViewById(R.id.refreshContainerButton);
//        refreshContainerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                syncUpdate();
//            }
//        });
//
//        pruneContainerButton = root.findViewById(R.id.pruneContainerButton);
//        pruneContainerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TiendaManager.getInstance().pruneContainers();
//                syncUpdate();
//            }
//        });
//
//        TiendaManager.getInstance().statusChangedNotifier.addCallback(new Function() {
//            @Override
//            public Object apply(Object input) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ProductosFragment.this.updateUI();
//                    }
//                });
//                return null;
//            }
//        });
//
//        this.syncUpdate();
        return root;
    }

//    private void syncUpdate() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TiendaManager.getInstance().updateContainers();
//                    TiendaManager.getInstance().updateImages();
//                    Thread.sleep(350);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ProductosFragment.this.updateUI();
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    private void clickItem(int position) {
//        Container c = TiendaManager.getInstance().getContainers().get(position);
//        ContainerDialog containerDialog = new ContainerDialog(c);
//        containerDialog.show(getActivity().getSupportFragmentManager(), "Container");
//        containerDialog.setOnSubmitCallback(new Function() {
//            @Override
//            public Object apply(Object input) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ProductosFragment.this.syncUpdate();
//                    }
//                });
//                return null;
//            }
//        });
//
//    }
//
//    public void updateUI() {
//        ArrayList<Container> containers = TiendaManager.getInstance().getContainers();
//        ProductosAdapter adapter = new ProductosAdapter(getContext(), containers);
//        adapter.setOnItemClickListener(new ProductosAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                clickItem(position);
//            }
//        });
//
//        deploysRecyclerView.setAdapter(adapter);
//    }
}
