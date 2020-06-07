package com.agm.ipmanager;

import android.content.Context;
import android.view.View;

import com.agm.ipmanager.API.APIConnector;
import com.agm.ipmanager.credentials.Credentials;
import com.agm.ipmanager.credentials.CredentialsManager;
import com.agm.ipmanager.events.Event;
import com.agm.ipmanager.events.EventManager;
import com.agm.ipmanager.machines.Machine;
import com.agm.ipmanager.machines.MachinesManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class IPManager {
    private static IPManager ipManager;
    private String serverName = "";
    private int updateInterval = 15;
    private HashMap<Service, Boolean> servicesStatus;

    public Notifier statusChangedNotifier;
    private JSONObject mongoStatus;
    private JSONObject dockerStatus;

    APIConnector apiConnector;
    EventManager eventManager;
    CredentialsManager credentialsManager;
    MachinesManager machinesManager;

    private IPManager() {
        apiConnector = new APIConnector();
        eventManager = new EventManager();
        credentialsManager = new CredentialsManager();
        machinesManager = new MachinesManager();

        statusChangedNotifier = new Notifier();
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
        apiConnector.updateServiceStatus();
    }

    public void setOnlineStatusServicesStatus(HashMap<Service, Boolean> data) {
        this.servicesStatus = data;
    }

    public void setServicesStatus(JSONObject mongo, JSONObject docker) {
        this.mongoStatus = mongo;
        this.dockerStatus = docker;
    }

    public JSONObject getMongoStatus() {
        return mongoStatus;
    }

    public JSONObject getDockerStatus() {
        return dockerStatus;
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
    public void addEvent(Event e) { eventManager.addEvent(e); }

    public ArrayList<Machine> getMachines() { return machinesManager.getMachines(); }

    public void addMachine(Machine m) {
        this.apiConnector.addMachine(m);
    }

    public void updateMachine(String name, Machine m) {
        this.apiConnector.updateMachine(name, m);
    }

    public void updateMachines() {
        this.apiConnector.setMachines();
    }

    public void setMachines(ArrayList<Machine> machines) {
        this.machinesManager.setMachines(machines);
    }

    public Machine getMachine(int position) {
        return this.machinesManager.getMachine(position);
    }
    public void removeMachine(Machine m) { this.apiConnector.removeMachine(m);}
}
