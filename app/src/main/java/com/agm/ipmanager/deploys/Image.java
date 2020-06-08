package com.agm.ipmanager.deploys;

public class Image {
    public String id;
    public String tag;
    public String uuid;

    public Image(String id, String tag, String uuid) {
        this.id = id;
        this.tag = tag;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return this.tag;
    }
}
