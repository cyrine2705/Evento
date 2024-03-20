package com.app.evento.controller;

import com.app.evento.exceptions.*;
import com.app.evento.models.Workshop;
import com.app.evento.service.WorkshopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    @Autowired
    private WorkshopService workshopService;

    @PostMapping
    public ResponseEntity<Workshop> addWorkshop(@Valid @RequestBody Workshop workshop) {
        try {
            Workshop savedWorkshop = workshopService.addWorkshop(workshop);
            return new ResponseEntity<>(savedWorkshop, HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity("An error occurred" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkshop(@PathVariable String id) throws Exception {
        try {
            Workshop workshop = workshopService.getWorkshop(id);
            return new ResponseEntity<>(workshop, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Workshop>> getWorkshops() {
        List<Workshop> workshops = workshopService.getWorkshops();
        return new ResponseEntity<>(workshops, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkshop(@PathVariable String id, @Valid @RequestBody Workshop workshop) throws Exception {
        try {
            Workshop updatedWorkshop = workshopService.updateWorkShop(workshop, id);
            return new ResponseEntity<>(updatedWorkshop, HttpStatus.OK);
        }  catch (Exception e) {
            // Handle other unexpected exceptions (consider logging)
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkshop(@PathVariable String id) throws Exception {
        try {
            workshopService.DeleteWorkshop(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{workshopId}/trainees/{guestId}/payment")
    public ResponseEntity<?> changeGuestPaymentStatus(@PathVariable String workshopId, @PathVariable String guestId, @RequestBody boolean pmtStatus) throws Exception {
        try {
            boolean status = workshopService.changeGuestPaymentStatus(workshopId, guestId, pmtStatus);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{workshopId}/trainees/{guestId}/presence")
    public ResponseEntity<?> changeGuestPresence(@PathVariable String workshopId, @PathVariable String guestId, @RequestBody boolean presence) throws Exception {
        try {
            boolean status = workshopService.changeGuestPresence(workshopId, guestId, presence);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (WorkshopNotFoundException | GuestNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);}}}

