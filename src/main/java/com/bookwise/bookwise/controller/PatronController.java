package com.bookwise.bookwise.controller;

import com.bookwise.bookwise.entity.Patron;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.service.PatronService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    // GET /api/patrons: Retrieve a list of all patrons
    @GetMapping
    public ResponseEntity<?> getAllPatrons() {
        try {
            List<Patron> patrons = patronService.findAllPatrons();
            return new ResponseEntity<>(patrons, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/patrons/{id}: Retrieve details of a specific patron by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatronById(@PathVariable Long id) {
        try {
            Patron patron = patronService.findPatronById(id);
            return new ResponseEntity<>(patron, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Return an error response with status 404 and the error message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST /api/patrons: Add a new patron to the library
    @PostMapping
    public ResponseEntity<?> addPatron(@RequestBody Patron patron) {
        try {
            patronService.createPatron(patron);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT /api/patrons/{id}: Update an existing patron's information
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatron(@PathVariable Long id, @RequestBody Patron patronDetails) {
        try {
            patronService.updatePatron(id, patronDetails);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE /api/patrons/{id}: Remove a patron from the library
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatron(@PathVariable Long id) {
        try {
            patronService.deletePatron(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}