package com.app.evento.controller;

import com.app.evento.models.Event;
import com.app.evento.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping("api/events")
public class EventController {
    @Autowired
    private EventService eventService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public Event  createEvent(@RequestBody Event e) {
       log.info("nheb nasna3 event"+e);
        return eventService.addEvent(e);
    }
    @GetMapping("/{id}")
    public Event getOneEvent(@PathVariable(name = "id") String id) throws Exception {
        return eventService.getEvent(id);
    }
    @GetMapping("")
    public List<Event> getAllEvents() throws Exception {
        return eventService.getEvents();
    }
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable(name = "id") String id,@RequestBody Event event) throws Exception {
        return eventService.updateEvent(id,event);
    }
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable(name = "id") String id) throws Exception{
        eventService.deleteEvent(id);
    }
}
