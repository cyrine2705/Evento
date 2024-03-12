package com.app.evento.controller;


import com.app.evento.models.Guest;
import com.app.evento.service.GuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PostMapping
    public ResponseEntity<Guest> addGuest(@Valid @RequestBody Guest guest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(String.valueOf(bindingResult));
        }
        Guest addedGuest = guestService.addGuest(guest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedGuest);
    }

    @PostMapping("/many")
    public ResponseEntity<List<Guest>> addGuests(@RequestBody List<Guest> guests) {
        List<Guest> addedGuests = guestService.addGuests(guests);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedGuests);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable String id, @Valid @RequestBody Guest updatedGuest, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(String.valueOf(bindingResult));
        }
        try {
            Guest updated = guestService.updateGuest(id, updatedGuest);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Guest>> getGuests() {
        List<Guest> guests = guestService.getGuests();
        return ResponseEntity.ok(guests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuest(@PathVariable String id)  {
        try {
            Guest guest = guestService.getEvent(id);
            return ResponseEntity.ok(guest);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable String id) {
        try {
            guestService.deleteGuest(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

