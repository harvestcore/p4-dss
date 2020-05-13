package com.agm.ipmanager.events;

import java.util.ArrayList;

public class EventManager {
    private ArrayList<Event> events;

    public EventManager() {
        events = new ArrayList<>();
        events.add(new Event(EventType.NONE, "There are no events"));
        events.get(0).timestamp = "";
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void addEvent(Event e) {
        if (events.get(0).type == EventType.NONE) {
            events.clear();
        }

        events.add(0, e);
    }
}
