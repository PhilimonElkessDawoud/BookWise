package com.bookwise.bookwise.repository;

import com.bookwise.bookwise.entity.BorrowingRecord;
import com.bookwise.bookwise.entity.embeddable.BorrowingRecordKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, BorrowingRecordKey> {
}