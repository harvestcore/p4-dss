package com.agm.ipmanager.machines;

import java.util.ArrayList;

public class MachinesManager {
    ArrayList<Machine> machines;

    public MachinesManager() {
        machines = new ArrayList<>();
        machines.add(new Machine("There are no machines", "", "", "", ""));
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public void addMachine(Machine m) {
        if (machines.get(0).name == "There are no machines") {
            machines.clear();
        }

        machines.add(0, m);
    }

    public void setMachines(ArrayList<Machine> machines) {
        this.machines = new ArrayList<>(machines);
    }

    public Machine getMachine(int position) {
        return this.machines.get(position);
    }
}
