package com.bookwise.bookwise.service.impl;

import com.bookwise.bookwise.entity.Book;
import com.bookwise.bookwise.entity.BorrowingRecord;
import com.bookwise.bookwise.entity.Patron;
import com.bookwise.bookwise.entity.embeddable.BorrowingRecordKey;
import com.bookwise.bookwise.entity.enums.BorrowOrReturn;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.repository.BookRepository;
import com.bookwise.bookwise.repository.BorrowingRecordRepository;
import com.bookwise.bookwise.repository.PatronRepository;
import com.bookwise.bookwise.service.BorrowingRecordService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    final BookRepository bookRepository;

    final PatronRepository patronRepository;

    final BorrowingRecordRepository borrowingRecordRepository;

    public BorrowingRecordServiceImpl(BookRepository bookRepository, PatronRepository patronRepository, BorrowingRecordRepository borrowingRecordRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    @Transactional
    @Override
    public void borrowBook(Long bookId, Long patronId) {
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Patron> patron = patronRepository.findById(patronId);

        if (book.isPresent() && patron.isPresent()) {

            BorrowingRecordKey borrowingRecordKey = new BorrowingRecordKey(bookId, patronId);

            BorrowingRecord borrowingRecord = new BorrowingRecord();
            borrowingRecord.setId(borrowingRecordKey);
            borrowingRecord.setBook(book.get());
            borrowingRecord.setPatron(patron.get());
            borrowingRecord.setAction(BorrowOrReturn.borrowed);
            borrowingRecord.setDateOfAction(LocalDateTime.now());

            borrowingRecordRepository.save(borrowingRecord);

        } else {

            throw new NotFoundException("Book or Patron not found!");
        }
    }

    @Transactional
    @Override
    public void returnBook(Long bookId, Long patronId) {
            Optional<Book> book = bookRepository.findById(bookId);
            Optional<Patron> patron = patronRepository.findById(patronId);

            if (book.isPresent() && patron.isPresent()) {

                BorrowingRecordKey borrowingRecordKey = new BorrowingRecordKey(bookId, patronId);

                BorrowingRecord borrowingRecord = borrowingRecordRepository.findById(borrowingRecordKey)
                        .orElseThrow(() -> new NotFoundException("Borrowing not found"));

                borrowingRecord.setAction(BorrowOrReturn.returned);
                borrowingRecord.setDateOfAction(LocalDateTime.now());

                borrowingRecordRepository.save(borrowingRecord);
            } else {

                throw new NotFoundException("Book or Patron not found!");
            }
    }
}