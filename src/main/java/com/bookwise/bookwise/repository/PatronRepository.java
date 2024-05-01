package com.bookwise.bookwise.repository;

import com.bookwise.bookwise.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}