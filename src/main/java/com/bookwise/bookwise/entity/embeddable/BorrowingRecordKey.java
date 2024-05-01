package com.bookwise.bookwise.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BorrowingRecordKey implements Serializable {

    @Column(name = "book_id")
    Long bookId;

    @Column(name = "patron_id")
    Long patronId;

    public BorrowingRecordKey() {
        super();
    }

    public BorrowingRecordKey(Long bookId, Long patronId) {
        this.bookId = bookId;
        this.patronId = patronId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getPatronId() {
        return patronId;
    }

    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowingRecordKey that = (BorrowingRecordKey) o;
        return Objects.equals(getBookId(), that.getBookId()) && Objects.equals(getPatronId(), that.getPatronId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getPatronId());
    }
}