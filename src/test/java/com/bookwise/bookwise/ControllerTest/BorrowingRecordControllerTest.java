package com.bookwise.bookwise.ControllerTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bookwise.bookwise.controller.BorrowingRecordController;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.service.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
public class BorrowingRecordControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingRecordController).build();
    }

    @Test
    public void testBorrowBook() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        doNothing().when(borrowingRecordService).borrowBook(bookId, patronId);

        mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Book borrowed successfully."));

        verify(borrowingRecordService, times(1)).borrowBook(bookId, patronId);
    }

    @Test
    public void testReturnBook() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        doNothing().when(borrowingRecordService).returnBook(bookId, patronId);

        mockMvc.perform(put("/api/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Book returned successfully."));

        verify(borrowingRecordService, times(1)).returnBook(bookId, patronId);
    }



// Existing BorrowingRecordControllerTest class...

    @Test
    public void testBorrowBookNotFoundException() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        doThrow(new NotFoundException("Book or Patron not found!"))
                .when(borrowingRecordService).borrowBook(bookId, patronId);

        MockHttpServletResponse response = mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse();

        assertThat(response.getContentAsString()).isEqualTo("Book or Patron not found!");
    }

    @Test
    public void testReturnBookNotFoundException() throws Exception {
        Long bookId = 1L;
        Long patronId = 1L;

        doThrow(new NotFoundException("Borrowing not found")).when(borrowingRecordService).returnBook(bookId, patronId);

        mockMvc.perform(put("/api/return/{bookId}/patron/{patronId}", bookId, patronId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Borrowing not found"));
    }
}