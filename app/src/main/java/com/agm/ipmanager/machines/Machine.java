package com.agm.ipmanager.machines;

public class Machine {
    public String name;
    public String type;
    public String ipv4;
    public String ipv6;
    public String mac;

    public Machine(String name, String type, String ipv4, String ipv6, String mac) {
        this.name = name;
        this.type = type;
        this.ipv4 = ipv4;
        this.ipv6 = ipv6;
        this.mac = mac;
    }
}
