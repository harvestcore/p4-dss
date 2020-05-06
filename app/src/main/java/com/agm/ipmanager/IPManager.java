package com.agm.ipmanager;

public class IPManager {
    private static IPManager ipManager;

    private IPManager() {}

    public static IPManager getInstance() {
        if (ipManager == null) {
            ipManager = new IPManager();
        }

        return ipManager;
    }
}
