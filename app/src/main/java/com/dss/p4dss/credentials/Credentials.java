package com.dss.p4dss.credentials;

public class Credentials {
    public int id;
    public String name;
    public String username;
    public String password;
    public boolean admin;

    public Credentials(int id, String name, String username, String password, boolean admin) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public Credentials(String name, String username, String password, boolean admin) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }
}
