package com.bookwise.bookwise.service;

public interface BorrowingRecordService {

    public void borrowBook(Long bookId, Long patronId);

    public void returnBook(Long bookId, Long patronId);
}