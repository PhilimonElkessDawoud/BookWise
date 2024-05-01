package com.bookwise.bookwise.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bookwise.bookwise.entity.Book;
import com.bookwise.bookwise.entity.BorrowingRecord;
import com.bookwise.bookwise.entity.Patron;
import com.bookwise.bookwise.entity.embeddable.BorrowingRecordKey;
import com.bookwise.bookwise.entity.enums.BorrowOrReturn;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.repository.BorrowingRecordRepository;
import com.bookwise.bookwise.repository.BookRepository;
import com.bookwise.bookwise.repository.PatronRepository;
import com.bookwise.bookwise.service.impl.BorrowingRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

class BorrowingRecordServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrowBook_WhenBookAndPatronExist_ShouldCreateBorrowingRecord() {
        // Arrange
        Long bookId = 1L;
        Long patronId = 2L;
        Book book = new Book();
        Patron patron = new Patron();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));

        // Act
        borrowingRecordService.borrowBook(bookId, patronId);

        // Assert
        verify(borrowingRecordRepository).save(any(BorrowingRecord.class));
    }

    @Test
    void borrowBook_WhenBookOrPatronDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        Long bookId = 1L;
        Long patronId = 2L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> borrowingRecordService.borrowBook(bookId, patronId));
    }

    @Test
    void returnBook_WhenBorrowingRecordExists_ShouldUpdateActionToReturned() {
        // Arrange
        Long bookId = 1L;
        Long patronId = 2L;
        BorrowingRecordKey borrowingRecordKey = new BorrowingRecordKey(bookId, patronId);
        BorrowingRecord existingRecord = new BorrowingRecord();
        existingRecord.setId(borrowingRecordKey);
        existingRecord.setAction(BorrowOrReturn.borrowed);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
        when(borrowingRecordRepository.findById(borrowingRecordKey)).thenReturn(Optional.of(existingRecord));

        // Act
        borrowingRecordService.returnBook(bookId, patronId);

        // Assert
        assertEquals(BorrowOrReturn.returned, existingRecord.getAction());
        assertNotNull(existingRecord.getDateOfAction());
        verify(borrowingRecordRepository).save(existingRecord);
    }

    @Test
    void returnBook_WhenBorrowingRecordDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        Long bookId = 1L;
        Long patronId = 2L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
        when(borrowingRecordRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> borrowingRecordService.returnBook(bookId, patronId));
    }
}