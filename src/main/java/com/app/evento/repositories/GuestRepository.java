package com.app.evento.repositories;

import com.app.evento.models.Guest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GuestRepository extends MongoRepository<Guest,String> {
}
