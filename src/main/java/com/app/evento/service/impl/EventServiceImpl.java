package com.app.evento.service.impl;

import com.app.evento.models.Event;
import com.app.evento.repositories.EventRepository;
import com.app.evento.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository eventRepository;
    @Override
    public Event addEvent(Event e) {
        return eventRepository.save(e);
    }

    @Override
    public Event updateEvent(String id, Event e) throws Exception {
        Optional<Event> event=eventRepository.findById(id);
        if(event.isEmpty())
        {
            throw new Exception("pas de event avec cet Id");

        }
        Event eventUpdate =event.get();
        if(e.getName()!=null){
            eventUpdate.setName(e.getName());
        }
        if(e.getPlace()!=null){
            eventUpdate.setPlace(e.getPlace());
        }
        if(e.getDescription()!=null){
            eventUpdate.setDescription(e.getDescription());
        }
        if(e.getWorkshop()!=null){
            eventUpdate.setWorkshop(e.getWorkshop());
        }
        return eventRepository.save(eventUpdate);
    }

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEvent(String id) throws Exception {
        Optional<Event> event=eventRepository.findById(id);
        if(event.isEmpty())
        {
            throw new Exception("pas de event avec cet Id");

        }
        return event.get();
    }

    @Override
    public void deleteEvent(String id) throws Exception {
        Optional<Event> event=eventRepository.findById(id);
        if(event.isEmpty())
        {
            throw new Exception("pas de event avec cet Id");

        }
         eventRepository.deleteById(id);
    }
}
