package com.bookwise.bookwise.service.impl;

import com.bookwise.bookwise.entity.Book;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.repository.BookRepository;
import com.bookwise.bookwise.service.BookService;
import org.springframework.cache.annotation.Cacheable;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable("books")
    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book not found with ID %d", id)));
    }

    @Transactional
    @Override
    public void createBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void updateBook(Long id, Book bookDetails) {
        Book updatedBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book not found with ID %d", id)));

        updatedBook.setEqualsTo(bookDetails);

        bookRepository.save(updatedBook);
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book not found with ID %d", id)));

        bookRepository.deleteById(book.getId());
    }
}