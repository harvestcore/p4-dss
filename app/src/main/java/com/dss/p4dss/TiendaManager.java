package com.dss.p4dss;

import android.content.Context;

import com.dss.p4dss.API.APIConnector;

public class TiendaManager {
    private static TiendaManager tiendaManager;

    APIConnector apiConnector;

    private TiendaManager() {
        apiConnector = new APIConnector();
    }

    public static TiendaManager getInstance() {
        if (tiendaManager == null) {
            tiendaManager = new TiendaManager();
        }

        return tiendaManager;
    }

    public void setContext(Context context) {
        apiConnector.setContext(context);
    }
}
