package com.agm.ipmanager;

public class IPManager {
    private static IPManager ipManager;
    private String serverName = "";

    private IPManager() {}

    public static IPManager getInstance() {
        if (ipManager == null) {
            ipManager = new IPManager();
        }

        return ipManager;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public boolean getServiceStatus(Service service) {
        if (service == Service.DOCKER) {
            return true;
        } else if (service == Service.MONGO) {
            return false;
        }

        return false;
    }
}
