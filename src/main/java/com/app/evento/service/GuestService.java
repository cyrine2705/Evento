package com.app.evento.service;

import com.app.evento.models.Guest;

import java.util.List;

public interface GuestService {
    Guest addGuest(Guest guest);
    List<Guest> addGuests(List<Guest> guests);
    Guest updateGuest(String id,Guest guest) throws Exception;
     List<Guest> getGuests();
     Guest getEvent(String id) throws Exception;
     void deleteGuest(String id) throws Exception;
}
