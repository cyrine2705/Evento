package com.app.evento.service;

import com.app.evento.models.Event;

import java.util.List;

public interface EventService {
    Event addEvent(Event event);
    Event updateEvent(String id,Event e) throws Exception;
    List<Event> getEvents();
    Event getEvent(String id) throws Exception;
    void deleteEvent(String id) throws Exception;
}
