package com.agm.ipmanager.deploys;

public class Container {
    public String name;
    public String status;
    public String id;
    public String uuid;
    public String image;

    public Container(String name, String status, String id, String image, String uuid) {
        this.name = name;
        this.status = status;
        this.id = id;
        this.image = image;
        this.uuid = uuid;
    }
}
