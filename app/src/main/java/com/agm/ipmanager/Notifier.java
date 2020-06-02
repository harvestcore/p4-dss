package com.agm.ipmanager;

import androidx.arch.core.util.Function;

import java.util.ArrayList;

public class Notifier {
    ArrayList<Function> callbacks;

    public Notifier() {
        callbacks = new ArrayList<>();
    }

    public void addCallback(Function f) {
        callbacks.add(f);
    }

    public void executeCallbacks() {
        for (Function f: callbacks) {
            f.apply(null);
        }
    }
}
