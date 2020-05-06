package com.agm.ipmanager;

import java.util.ArrayList;

public class EventManager {
    private static EventManager eventManager;
    private ArrayList<Event> events;

    private EventManager() {
        events = new ArrayList<>();
        events.add(new Event(EventType.NONE, "There are no events"));
        events.get(0).timestamp = "";
    }

    public static EventManager getInstance() {
        if (eventManager == null) {
            eventManager = new EventManager();
        }

        return eventManager;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void addEvent(Event e) {
        if (events.get(0).type == EventType.NONE) {
            events.clear();
        }

        events.add(e);
    }
}
