package com.bookwise.bookwise.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bookwise.bookwise.entity.Book;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.repository.BookRepository;
import com.bookwise.bookwise.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllBooks_ShouldReturnAllBooks() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(List.of(new Book(), new Book()));

        // Act
        List<Book> result = bookService.findAllBooks();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void findBookById_WhenBookExists_ShouldReturnBook() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.findBookById(bookId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void findBookById_WhenBookDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> bookService.findBookById(bookId));
    }

    @Test
    void createBook_ShouldSaveNewBook() {
        // Arrange
        Book newBook = new Book();

        // Act
        bookService.createBook(newBook);

        // Assert
        verify(bookRepository).save(newBook);
    }

    @Test
    void updateBook_WhenBookExists_ShouldUpdateBookDetails() {
        // Arrange
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setTitle("Old Title");
        Book updatedBookDetails = new Book();
        updatedBookDetails.setTitle("New Title");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        // Act
        bookService.updateBook(bookId, updatedBookDetails);

        // Assert
        assertEquals("New Title", existingBook.getTitle());
        verify(bookRepository).save(existingBook);
    }

    @Test
    void updateBook_WhenBookDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        Long bookId = 1L;
        Book updatedBookDetails = new Book();
        updatedBookDetails.setTitle("New Title");

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> bookService.updateBook(bookId, updatedBookDetails));
    }

    @Test
    void deleteBook_WhenBookExists_ShouldDeleteBook() {
        // Arrange
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        // Act
        bookService.deleteBook(bookId);

        // Assert
        verify(bookRepository).deleteById(bookId);
    }

    @Test
    void deleteBook_WhenBookDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> bookService.deleteBook(bookId));
    }
}