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
                        TiendaManager.getInstance().fetchCarrito();
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
        String url = TiendaManager.getInstance().getServerURL() + "/store";
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
                TiendaManager.getInstance().fetchCarrito();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    public void borrarProductoDeCarrito(final Producto p) {
        String url = TiendaManager.getInstance().getServerURL() + "/store";
        Credentials c = TiendaManager.getInstance().getCredentials();

        JSONObject query = new JSONObject();
        try {
            query.put("user", c.id);
            query.put("product", p.id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomObjectRequest request = new CustomObjectRequest(Request.Method.DELETE, url, query, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                TiendaManager.getInstance().fetchCarrito();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    public void getCarrito() {
        String url = TiendaManager.getInstance().getServerURL() + "/store/cart";
        Credentials c = TiendaManager.getInstance().getCredentials();

        if (c != null && c.id != 0) {
            JSONObject query = new JSONObject();
            try {
                query.put("id", c.id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomObjectRequest request = new CustomObjectRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        ArrayList<Producto> prods = new ArrayList<>();
                        JSONArray aux = response.getJSONArray("products");
                        for (int i = 0; i < aux.length(); ++i) {
                            JSONObject xd = aux.getJSONObject(i);
                            prods.add(new Producto(
                                    xd.getInt("id"),
                                    xd.getString("name"),
                                    xd.getString("family"),
                                    xd.getDouble("price")
                            ));
                        }

                        TiendaManager.getInstance().setCarrito(prods);
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
    }

    public void addUsuario(Credentials user) {
        String url = TiendaManager.getInstance().getServerURL() + "/user";
        Credentials c = TiendaManager.getInstance().getCredentials();

        if (c != null && c.id != 0) {
            JSONObject query = new JSONObject();
            try {
                query.put("name", user.name);
                query.put("username", user.username);
                query.put("password", user.password);
                query.put("admin", user.admin);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomObjectRequest request = new CustomObjectRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Update UI
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            requestQueue.add(request);
        }
    }

    public void addProducto(Producto p) {
        String url = TiendaManager.getInstance().getServerURL() + "/product";
        Credentials c = TiendaManager.getInstance().getCredentials();

        if (c != null && c.id != 0) {
            JSONObject query = new JSONObject();
            try {
                query.put("name", p.name);
                query.put("family", p.family);
                query.put("price", String.valueOf(p.price));
                query.put("user", c.id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomObjectRequest request = new CustomObjectRequest(Request.Method.POST, url, query, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Update UI
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            requestQueue.add(request);
        }
    }
}
