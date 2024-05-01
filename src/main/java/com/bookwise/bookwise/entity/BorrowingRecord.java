package com.bookwise.bookwise.entity;

import com.bookwise.bookwise.entity.embeddable.BorrowingRecordKey;
import com.bookwise.bookwise.entity.enums.BorrowOrReturn;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BorrowingRecord {
    @EmbeddedId
    BorrowingRecordKey id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    Book book;

    @ManyToOne
    @MapsId("patronId")
    @JoinColumn(name = "patron_id")
    Patron patron;

    @Enumerated(EnumType.STRING)
    private BorrowOrReturn action;

    @Column(name = "date_of_action", nullable = false)
    private LocalDateTime dateOfAction;

    public BorrowingRecord(BorrowingRecordKey id, Book book, Patron patron, BorrowOrReturn action, LocalDateTime dateOfAction) {
        this.id = id;
        this.book = book;
        this.patron = patron;
        this.action = action;
        this.dateOfAction = dateOfAction;
    }

    public BorrowingRecord() {
        super();
    }

    public BorrowingRecordKey getId() {
        return id;
    }

    public void setId(BorrowingRecordKey id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public BorrowOrReturn getAction() {
        return action;
    }

    public void setAction(BorrowOrReturn action) {
        this.action = action;
    }

    public LocalDateTime getDateOfAction() {
        return dateOfAction;
    }

    public void setDateOfAction(LocalDateTime dateOfAction) {
        this.dateOfAction = dateOfAction;
    }
}