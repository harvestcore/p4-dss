package com.agm.ipmanager.API;

import android.util.Base64;

import androidx.annotation.Nullable;

import com.agm.ipmanager.IPManager;
import com.agm.ipmanager.credentials.Credentials;
import com.agm.ipmanager.credentials.CredentialsManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomRequest extends JsonObjectRequest {
    public CustomRequest(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public CustomRequest(String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (IPManager.getInstance().hasCredentials()) {
            HashMap<String, String> params = new HashMap<>();

            Credentials credentials = IPManager.getInstance().getCredentials();
            String creds = String.format("%s:%s", credentials.username, credentials.password);
            String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);

            params.put("Authorization", auth);
            params.put("x-access-token", credentials.token);
            params.put("Content-Type", "application/json");

            return params;
        }

        return Collections.emptyMap();
    }


}
