package com.bookwise.bookwise.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bookwise.bookwise.controller.BookController;
import com.bookwise.bookwise.entity.Book;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getAllBooks_ShouldReturnBooks() throws Exception {
        // Arrange
        when(bookService.findAllBooks()).thenReturn(List.of(new Book(), new Book()));

        // Act & Assert
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() throws Exception {
        // Arrange
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenReturn(new Book());

        // Act & Assert
        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isOk());
    }

    @Test
    void getBookById_WhenBookDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenThrow(NotFoundException.class);

        // Act & Assert
        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBook_ShouldCreateNewBook() throws Exception {
        // Arrange
        String bookJson = """
                      {
                          "title": "Book Title",
                          "author": "Author Name",
                          "publication_year": 2005,
                          "isbn": "1234567890"
                      }
                      """;
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isCreated());

        // Capture the book object passed to the createBook method
        verify(bookService).createBook(bookCaptor.capture());
        Book capturedBook = bookCaptor.getValue();

        // Assert the fields of the captured Book object
        assertEquals("Book Title", capturedBook.getTitle());
        assertEquals("Author Name", capturedBook.getAuthor());
        assertEquals(Short.parseShort("2005"), capturedBook.getPublication_year());
        assertEquals("1234567890", capturedBook.getIsbn());
    }

    @Test
    void updateBook_WhenBookExists_ShouldUpdateBook() throws Exception {
        // Arrange
        Long bookId = 1L;
        String bookJson = """
                      {
                          "title": "Book Title",
                          "author": "Author Name",
                          "publication_year": 2005,
                          "isbn": "1234567890"
                      }
                      """;
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);

        // Act & Assert
        mockMvc.perform(put("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk());

        // Capture the book object passed to the updateBook method
        verify(bookService).updateBook(eq(bookId), bookCaptor.capture());
        Book capturedBook = bookCaptor.getValue();

        // Assert the fields of the captured Book object
        assertEquals("Book Title", capturedBook.getTitle());
        assertEquals("Author Name", capturedBook.getAuthor());
        assertEquals(Short.parseShort("2005"), capturedBook.getPublication_year());
        assertEquals("1234567890", capturedBook.getIsbn());
    }

    @Test
    void deleteBook_WhenBookExists_ShouldDeleteBook() throws Exception {
        // Arrange
        Long bookId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/books/{id}", bookId))
                .andExpect(status().isNoContent());
        verify(bookService).deleteBook(bookId);
    }
}