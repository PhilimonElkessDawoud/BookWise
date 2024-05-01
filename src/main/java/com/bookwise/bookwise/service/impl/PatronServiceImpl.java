package com.bookwise.bookwise.service.impl;

import com.bookwise.bookwise.entity.Patron;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.repository.PatronRepository;
import com.bookwise.bookwise.service.PatronService;
import org.springframework.cache.annotation.Cacheable;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronServiceImpl implements PatronService {

    final PatronRepository patronRepository;

    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Cacheable("allPatrons")
    @Override
    public List<Patron> findAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    public Patron findPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patron not found with ID %d", id)));
    }

    @Transactional
    @Override
    public void createPatron(Patron patron) {
        patronRepository.save(patron);
    }

    @Transactional
    @Override
    public void updatePatron(Long id, Patron patronDetails) {
        Patron updatedPatron = patronRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patron not found with ID %d", id)));

        updatedPatron.setEqualsTo(patronDetails);

        patronRepository.save(updatedPatron);
    }

    @Transactional
    @Override
    public void deletePatron(Long id) {
        var patron = patronRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patron not found with ID %d", id)));

        patronRepository.deleteById(patron.getId());
    }
}