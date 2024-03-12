package com.app.evento.repositories;

import com.app.evento.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EventRepository extends MongoRepository<Event,String> {
}
