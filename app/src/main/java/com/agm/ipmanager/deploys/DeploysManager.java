package com.agm.ipmanager.deploys;

import java.util.ArrayList;

public class DeploysManager {
    ArrayList<Container> containers;
    ArrayList<Image> images;

    public DeploysManager() {
        containers = new ArrayList<>();
        containers.add(new Container("There are no running containers", "", "", "", ""));
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = new ArrayList<>(containers);
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
