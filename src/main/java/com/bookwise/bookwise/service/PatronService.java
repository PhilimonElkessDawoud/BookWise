package com.bookwise.bookwise.service;

import com.bookwise.bookwise.entity.Patron;

import java.util.List;

public interface PatronService {

    public List<Patron> findAllPatrons();

    public Patron findPatronById(Long id);

    public void createPatron(Patron patron);

    public void updatePatron(Long id, Patron patron);

    public void deletePatron(Long id);
}