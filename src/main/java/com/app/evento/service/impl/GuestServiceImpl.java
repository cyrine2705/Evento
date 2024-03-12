package com.app.evento.service.impl;

import com.app.evento.models.Guest;
import com.app.evento.repositories.GuestRepository;
import com.app.evento.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {
    @Autowired
    GuestRepository guestRepository;
    @Override
    public Guest addGuest(Guest guest) {

        return guestRepository.save(guest);
    }
    @Override
    public List<Guest> addGuests(List<Guest> guests){

        return guestRepository.saveAll(guests);
    }

    @Override
    public Guest updateGuest(String id, Guest g) throws Exception {
        Optional<Guest> guest=guestRepository.findById(id);
        if(guest.isEmpty())
        {
            throw new Exception("pas de guest avec cet Id");

        }
        Guest guestUpdate =guest.get();
        if(g.getFirstName()!=null){
            guestUpdate.setFirstName(g.getFirstName());
        }
        if(g.getLastName()!=null){
            guestUpdate.setLastName(g.getLastName());
        }
        if(g.getAddress()!=null){
            guestUpdate.setAddress(g.getAddress());
        }
        if(g.getService()!=null){
            guestUpdate.setService(g.getService());
        }
        if(g.getEmail()!=null){
            guestUpdate.setEmail(g.getEmail());
        }
        if(g.getType()!=null){
            guestUpdate.setType(g.getType());
        }
        if(g.getGender()!=null){
            guestUpdate.setGender(g.getGender());
        }
        if(g.getPhone()!=null){
            guestUpdate.setPhone(g.getPhone());
        }
        if(g.getWorkPlace()!=null){
            guestUpdate.setWorkPlace(g.getWorkPlace());
        }
        if(g.getProfession()!=null){
            guestUpdate.setProfession(g.getProfession());
        }
        return guestRepository.save(guestUpdate);
    }
    @Override
    public List<Guest> getGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest getEvent(String id) throws Exception {
        Optional<Guest> guest=guestRepository.findById(id);
        if(guest.isEmpty())
        {
            throw new Exception("pas de guest avec cet Id");

        }
        return guest.get();
    }
    @Override
    public void deleteGuest(String id) throws Exception {
        Optional<Guest> guest=guestRepository.findById(id);
        if(guest.isEmpty())
        {
            throw new Exception("pas de guest avec cet Id");

        }
        guestRepository.deleteById(id);
    }
}
