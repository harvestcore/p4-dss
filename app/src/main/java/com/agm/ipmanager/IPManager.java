package com.agm.ipmanager;

import android.content.Context;
import android.view.View;

import com.agm.ipmanager.API.APIConnector;
import com.agm.ipmanager.credentials.Credentials;
import com.agm.ipmanager.credentials.CredentialsManager;
import com.agm.ipmanager.events.Event;
import com.agm.ipmanager.events.EventManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class IPManager {
    private static IPManager ipManager;
    private String serverName = "";
    private int updateInterval = 30;
    private HashMap<Service, Boolean> servicesStatus;

    APIConnector apiConnector;
    EventManager eventManager;
    CredentialsManager credentialsManager;

    private IPManager() {
        apiConnector = new APIConnector();
        eventManager = new EventManager();
        credentialsManager = new CredentialsManager();
    }

    public static IPManager getInstance() {
        if (ipManager == null) {
            ipManager = new IPManager();
        }

        return ipManager;
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public boolean isOnline() {
        return credentialsManager.isOnline();
    }

    public HashMap<Service, Boolean> getServicesStatus() {
        return this.servicesStatus;
    }

    public void recalculateServicesStatus() {
        this.servicesStatus = apiConnector.getServiceStatus();
    }

    public boolean hasCredentials() {
        return credentialsManager.hasCredentials();
    }

    public Credentials getCredentials() {
        return credentialsManager.getCredentials();
    }

    public void saveCredentials(Credentials credentials) {
        try {
            credentialsManager.saveCredentials(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setToken(String token) {
        credentialsManager.setToken(token);
    }

    public void restoreServerName() {
        try {
            credentialsManager.restoreServerName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasServerName() {
        return !serverName.isEmpty();
    }

    public void saveServerName(String serverName) {
        try {
            credentialsManager.saveServerName(serverName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUpdateInterval(int updateInterval) {

    }

    public void setView(View view) {
        credentialsManager.setView(view);
    }

    public void login() {
        apiConnector.login();
    }

    public void setContext(Context context) {
        apiConnector.setContext(context);
    }

    public ArrayList<Event> getEvents(){
        return eventManager.getEvents();
    }
}
