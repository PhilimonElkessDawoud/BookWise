package com.bookwise.bookwise.controller;

import com.bookwise.bookwise.entity.Book;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        try {
            List<Book> books = bookService.findAllBooks();
            return new ResponseEntity<>(books, HttpStatus.OK);
        }  catch (RuntimeException e) {
            // Return an error response with status 500 and the error message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/books/{id}: Retrieve details of a specific book by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.findBookById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Return an error response with status 404 and the error message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            // Return an error response with status 500 and the error message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST /api/books: Add a new book to the library
    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        try {
            bookService.createBook(book);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Return an error response with status 500 and the error message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT /api/books/{id}: Update an existing book's information
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        try {
            bookService.updateBook(id, bookDetails);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            // Return an error response with status 500 and the error message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE /api/books/{id}: Remove a book from the library
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return new ResponseEntity<>("Book Deleted Successfully!",HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            // Return an error response with status 500 and the error message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}