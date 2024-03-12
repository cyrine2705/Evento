package com.app.evento.repositories;

import com.app.evento.models.Workshop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkshopRepository extends MongoRepository<Workshop,String> {
}
