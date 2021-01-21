package com.dss.p4dss;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dss.p4dss.API.CustomObjectRequest;
import com.dss.p4dss.credentials.Credentials;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private EditText txtLoginUser, txtLoginPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setTitle("Iniciar sesión");

        txtLoginUser = (EditText)findViewById(R.id.txtLoginUser);
        txtLoginPassword = (EditText)findViewById(R.id.txtLoginPassword);

        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtLoginUser.getText().toString().isEmpty() || txtLoginPassword.getText().toString().isEmpty()){
                    FragmentManager fragmentManager = getFragmentManager();
                    DialogoAlerta dialogo = new DialogoAlerta();
                    dialogo.setMensajeAlerta("Por favor, rellena todos los campos para continuar");
                    dialogo.setTituloAlerta("Campos vacíos");
                    dialogo.show(fragmentManager, "CamposVacios");
                } else {
                    login(txtLoginUser.getText().toString(), txtLoginPassword.getText().toString());
                }
            }
        });
    }

    private void login(String username, String password){
        RequestQueue requestQueue = Volley.newRequestQueue(TiendaManager.getInstance().getContext());

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

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle b = new Bundle();
                        intent.putExtras(b);
                        startActivity(intent);
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
}
