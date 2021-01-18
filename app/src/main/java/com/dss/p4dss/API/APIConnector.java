package com.dss.p4dss.API;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dss.p4dss.TiendaManager;
import com.dss.p4dss.credentials.Credentials;
import com.dss.p4dss.productos.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APIConnector {
    private RequestQueue requestQueue;
    private Context context;

    public APIConnector() {}

    public void setContext(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void login(String username, String password) {
        String url = TiendaManager.getInstance().getServerURL() + "/user/login";
        JSONObject query = new JSONObject();
        try {
            query.put("username", username);
            query.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomObjectRequest request = new CustomObjectRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int id = response.getInt("id");

                    if (id != 0) {
                        TiendaManager.getInstance().setCredentials(new Credentials(
                                response.getInt("id"),
                                response.getString("name"),
                                response.getString("username"),
                                response.getString("password"),
                                response.getBoolean("admin")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    public void getProductos() {
        String url = TiendaManager.getInstance().getServerURL() + "/product";

        CustomArrayRequest request = new CustomArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<Producto> prods = new ArrayList<>();
                    for (int i = 0; i < response.length(); ++i) {
                        JSONObject aux = response.getJSONObject(i);
                        prods.add(new Producto(
                                aux.getInt("id"),
                                aux.getString("name"),
                                aux.getString("family"),
                                aux.getDouble("price")
                        ));
                    }

                    TiendaManager.getInstance().setProductos(prods);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    public void agregarProductoACarrito(Producto p) {
        String url = TiendaManager.getInstance().getServerURL() + "/product";
        Credentials c = TiendaManager.getInstance().getCredentials();

        JSONObject query = new JSONObject();
        try {
            query.put("user", c.id);
            query.put("product", p.id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomObjectRequest request = new CustomObjectRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Actualizar UI carrito
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

//    public void updateServiceStatus() {
//        final HashMap<Service, Boolean> output = new HashMap<>();
//        if (TiendaManager.getInstance().hasCredentials()) {
//            String url = TiendaManager.getInstance().getCredentials().hostname + "/api/status";
//
//            CustomRequest request = new CustomRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        JSONObject mongo = response.getJSONObject("mongo");
//                        JSONObject docker = response.getJSONObject("docker");
//
//                        TiendaManager.getInstance().setServicesStatus(mongo, docker);
//
//                        output.put(Service.MONGO, mongo.getBoolean("is_up"));
//                        output.put(Service.DOCKER, docker.getBoolean("is_up"));
//
//                        TiendaManager.getInstance().setOnlineStatusServicesStatus(output);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(request);
//        }
//    }
//
//    public void setMachines() {
//        final ArrayList<Machine> output = new ArrayList<>();
//        if (TiendaManager.getInstance().hasCredentials()) {
//            String url = TiendaManager.getInstance().getCredentials().hostname + "/api/machine/query";
//            JSONObject query = new JSONObject();
//            try {
//                query.put("query", new JSONObject());
//                query.put("filter", new JSONObject());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            CustomRequest request = new CustomRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        try {
//                            String ipv6 = "";
//                            String mac = "";
//                            try {
//                                ipv6 = response.getString("ipv6");
//                                mac = response.getString("mac");
//                            } catch (JSONException ee) {}
//
//                            output.add(new Machine(
//                                response.getString("name"),
//                                response.getString("type"),
//                                response.getString("ipv4"),
//                                ipv6,
//                                mac
//                            ));
//                        } catch (JSONException e) {
//                            JSONArray items = response.getJSONArray("items");
//                            int total = response.getInt("total");
//
//                            for (int i = 0; i < total; ++i) {
//                                JSONObject aux = items.getJSONObject(i);
//                                String ipv6 = "";
//                                String mac = "";
//                                try {
//                                    ipv6 = aux.getString("ipv6");
//                                    mac = aux.getString("mac");
//                                } catch (JSONException ee) {}
//
//                                output.add(new Machine(
//                                        aux.getString("name"),
//                                        aux.getString("type"),
//                                        aux.getString("ipv4"),
//                                        ipv6,
//                                        mac
//                                ));
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    TiendaManager.getInstance().setMachines(output);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(request);
//        }
//    }
//
//    public void addMachine(Machine m) {
//        if (TiendaManager.getInstance().hasCredentials()) {
//            String url = TiendaManager.getInstance().getCredentials().hostname + "/api/machine";
//            JSONObject query = new JSONObject();
//            try {
//                query.put("name", m.name);
//                query.put("type", m.type);
//                query.put("ipv4", m.ipv4);
//
//                if (!m.ipv6.isEmpty()) {
//                    query.put("ipv6", m.ipv6);
//                }
//                if (!m.mac.isEmpty()) {
//                    query.put("mac", m.mac);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            CustomRequest request = new CustomRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        boolean status = response.getBoolean("ok");
//
//                        if (status) {
//                            setMachines();
//                            TiendaManager.getInstance().addEvent(new Event(EventType.MACHINE, "Machine added"));
//                        } else {
//                            TiendaManager.getInstance().addEvent(new Event(EventType.MACHINE, "Machine not added"));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(request);
//        }
//    }
//
//    public void removeMachine(Machine m) {
//        if (TiendaManager.getInstance().hasCredentials()) {
//            String url = TiendaManager.getInstance().getCredentials().hostname + "/api/machine/" + m.name;
//            JSONObject query = new JSONObject();
//            try {
//                query.put("name", m.name);
//                System.out.println(query);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            CustomRequest request = new CustomRequest(Request.Method.DELETE, url, query, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        boolean status = response.getBoolean("ok");
//
//                        if (status) {
//                            setMachines();
//                            TiendaManager.getInstance().addEvent(new Event(EventType.MACHINE, "Machine deleted"));
//                        } else {
//                            TiendaManager.getInstance().addEvent(new Event(EventType.MACHINE, "Machine not deleted"));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(request);
//        }
//    }
//
//    public void updateMachine(String name, Machine m) {
//        if (TiendaManager.getInstance().hasCredentials()) {
//            String url = TiendaManager.getInstance().getCredentials().hostname + "/api/machine";
//            JSONObject query = new JSONObject();
//            JSONObject data = new JSONObject();
//
//            try {
//                data.put("name", m.name);
//                data.put("type", m.type);
//                data.put("ipv4", m.ipv4);
//
//                if (!m.ipv6.isEmpty()) {
//                    query.put("ipv6", m.ipv6);
//                }
//                if (!m.mac.isEmpty()) {
//                    query.put("mac", m.mac);
//                }
//
//                query.put("name", name);
//                query.put("data", data);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            CustomRequest request = new CustomRequest(Request.Method.PUT, url, query, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        boolean status = response.getBoolean("ok");
//
//                        if (status) {
//                            setMachines();
//                            TiendaManager.getInstance().addEvent(new Event(EventType.MACHINE, "Machine updated"));
//                        } else {
//                            TiendaManager.getInstance().addEvent(new Event(EventType.MACHINE, "Machine not updated"));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(request);
//        }
//    }
//
//    public void updateContainers() {
//        if (TiendaManager.getInstance().hasCredentials()) {
//            String url = TiendaManager.getInstance().getCredentials().hostname + "/api/deploy/container";
//            JSONObject query = new JSONObject();
//            JSONObject data = new JSONObject();
//
//            final ArrayList<Container> output = new ArrayList<>();
//
//            try {
//                data.put("all", true);
//                query.put("data", data);
//                query.put("operation", "list");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            CustomRequest request = new CustomRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        try {
//                            String tag = "";
//                            try {
//                                tag = response.getJSONObject("image").getJSONArray("tags").getString(1);
//                            } catch (JSONException t) {}
//
//                            output.add(new Container(
//                                    response.getString("name"),
//                                    response.getString("status"),
//                                    response.getString("short_id"),
//                                    tag,
//                                    response.getString("id")
//                            ));
//                        } catch (JSONException e) {
//                            JSONArray items = response.getJSONArray("items");
//                            int total = response.getInt("total");
//
//                            for (int i = 0; i < total; ++i) {
//                                JSONObject aux = items.getJSONObject(i);
//
//                                String tag = "";
//                                try {
//                                    tag = response.getJSONObject("image").getJSONArray("tags").getString(1);
//                                } catch (JSONException t) {}
//
//                                output.add(new Container(
//                                        aux.getString("name"),
//                                        aux.getString("status"),
//                                        aux.getString("short_id"),
//                                        tag,
//                                        aux.getString("id")
//                                ));
//                            }
//                        }
//
//                        TiendaManager.getInstance().setContainers(output);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(request);
//        }
//    }
//
//    public void runContainerOperation(Container c, final String op) {
//        if (TiendaManager.getInstance().hasCredentials()) {
//            String url = TiendaManager.getInstance().getCredentials().hostname + "/api/deploy/container/single";
//            JSONObject query = new JSONObject();
//            JSONObject data = new JSONObject();
//
//            try {
//                query.put("container_id", c.uuid);
//                query.put("operation", op);
//                query.put("data", data);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            CustomRequest request = new CustomRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        boolean status = response.getBoolean("ok");
//
//                        if (status) {
//                            if (op.equals("pause")) {
//                                TiendaManager.getInstance().addEvent(new Event(EventType.DEPLOY, "Container paused"));
//                            } else if (op.equals("unpause")) {
//                                TiendaManager.getInstance().addEvent(new Event(EventType.DEPLOY, "Container unpaused"));
//                            } else if (op.equals("reload")) {
//                                TiendaManager.getInstance().addEvent(new Event(EventType.DEPLOY, "Container reloaded"));
//                            } else if (op.equals("restart")) {
//                                TiendaManager.getInstance().addEvent(new Event(EventType.DEPLOY, "Container restarted"));
//                            } else if (op.equals("kill")) {
//                                TiendaManager.getInstance().addEvent(new Event(EventType.DEPLOY, "Container killed"));
//                            } else if (op.equals("stop")) {
//                                TiendaManager.getInstance().addEvent(new Event(EventType.DEPLOY, "Container stopped"));
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(request);
//        }
//    }
//
//    public void pruneContainers() {
//        if (TiendaManager.getInstance().hasCredentials()) {
//            String url = TiendaManager.getInstance().getCredentials().hostname + "/api/deploy/container";
//            JSONObject query = new JSONObject();
//            JSONObject data = new JSONObject();
//
//            try {
//                query.put("operation", "prune");
//                query.put("data", data);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            CustomRequest request = new CustomRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        boolean status = response.getBoolean("ok");
//
//                        if (status) {
//                            TiendaManager.getInstance().addEvent(new Event(EventType.DEPLOY, "Containers pruned"));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(request);
//        }
//    }
//
//    public void updateImages() {
//        if (TiendaManager.getInstance().hasCredentials()) {
//            String url = TiendaManager.getInstance().getCredentials().hostname + "/api/deploy/image";
//            JSONObject query = new JSONObject();
//            JSONObject data = new JSONObject();
//
//            final ArrayList<Producto> output = new ArrayList<>();
//
//            try {
//                query.put("data", data);
//                query.put("operation", "list");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            CustomRequest request = new CustomRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        try {
//                            output.add(new Producto(
//                                    response.getString("short_id"),
//                                    response.getJSONArray("tags").getString(0),
//                                    response.getString("id")
//                            ));
//                        } catch (JSONException e) {
//                            JSONArray items = response.getJSONArray("items");
//                            int total = response.getInt("total");
//
//                            for (int i = 0; i < total; ++i) {
//                                JSONObject aux = items.getJSONObject(i);
//                                output.add(new Producto(
//                                        aux.getString("short_id"),
//                                        aux.getJSONArray("tags").getString(0),
//                                        aux.getString("id")
//                                ));
//                            }
//                        }
//
//                        TiendaManager.getInstance().setImages(output);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(request);
//        }
//    }
//
//    public void runImage(Producto producto) {
//        if (TiendaManager.getInstance().hasCredentials()) {
//            String url = TiendaManager.getInstance().getCredentials().hostname + "/api/deploy/container";
//            JSONObject query = new JSONObject();
//            JSONObject data = new JSONObject();
//
//            try {
//                data.put("image", producto.uuid);
//                query.put("operation", "run");
//                query.put("data", data);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            CustomRequest request = new CustomRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        boolean status = response.getBoolean("ok");
//
//                        if (status) {
//                            TiendaManager.getInstance().addEvent(new Event(EventType.DEPLOY, "Image running"));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(request);
//        }
//    }
}
