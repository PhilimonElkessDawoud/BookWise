package com.bookwise.bookwise.service;

import com.bookwise.bookwise.entity.Book;

import java.util.List;

public interface BookService {

    public List<Book> findAllBooks();

    public Book findBookById(Long id);

    public void createBook(Book book);

    public void updateBook(Long id, Book bookDetails);

    public void deleteBook(Long id);
}