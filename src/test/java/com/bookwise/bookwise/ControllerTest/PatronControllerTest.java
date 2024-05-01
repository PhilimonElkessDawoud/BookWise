package com.bookwise.bookwise.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bookwise.bookwise.controller.PatronController;
import com.bookwise.bookwise.entity.Patron;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.service.PatronService;
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

class PatronControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    void getAllPatrons_ShouldReturnPatrons() throws Exception {
        // Arrange
        when(patronService.findAllPatrons()).thenReturn(List.of(new Patron(), new Patron()));

        // Act & Assert
        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getPatronById_WhenPatronExists_ShouldReturnPatron() throws Exception {
        // Arrange
        Long patronId = 1L;
        when(patronService.findPatronById(patronId)).thenReturn(new Patron());

        // Act & Assert
        mockMvc.perform(get("/api/patrons/{id}", patronId))
                .andExpect(status().isOk());
    }

    @Test
    void getPatronById_WhenPatronDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        Long patronId = 1L;
        when(patronService.findPatronById(patronId)).thenThrow(NotFoundException.class);

        // Act & Assert
        mockMvc.perform(get("/api/patrons/{id}", patronId))
                .andExpect(status().isNotFound());
    }

    @Test
    void addPatron_ShouldCreateNewPatron() throws Exception {
        // Arrange
        String patronJson = """
                      {
                          "name": "Patron Name",
                          "phone": "123456789",
                          "email": "patron@example.com",
                          "address": "123 Main St"
                      }
                      """;
        ArgumentCaptor<Patron> patronCaptor = ArgumentCaptor.forClass(Patron.class);

        // Act & Assert
        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronJson))
                .andExpect(status().isCreated());

        // Capture the patron object passed to the createPatron method
        verify(patronService).createPatron(patronCaptor.capture());
        Patron capturedPatron = patronCaptor.getValue();

        // Assert the fields of the captured Patron object
        assertEquals("Patron Name", capturedPatron.getName());
        assertEquals("123456789", capturedPatron.getPhone());
        assertEquals("patron@example.com", capturedPatron.getEmail());
        assertEquals("123 Main St", capturedPatron.getAddress());
    }

    @Test
    void updatePatron_WhenPatronExists_ShouldUpdatePatron() throws Exception {
        // Arrange
        Long patronId = 1L;
        String patronJson = """
                      {
                          "name": "Updated Patron Name",
                          "phone": "987654321",
                          "email": "updated.patron@example.com",
                          "address": "321 Main St"
                      }
                      """;
        Patron existingPatron = new Patron();
        existingPatron.setId(patronId);
        existingPatron.setName("Original Patron Name");
        existingPatron.setPhone("123456789");
        existingPatron.setEmail("patron@example.com");
        existingPatron.setAddress("123 Main St");

        when(patronService.findPatronById(patronId)).thenReturn(existingPatron);
        ArgumentCaptor<Patron> patronCaptor = ArgumentCaptor.forClass(Patron.class);

        // Act & Assert
        mockMvc.perform(put("/api/patrons/{id}", patronId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronJson))
                .andExpect(status().isOk());

        // Capture and assert the updated patron details
        verify(patronService).updatePatron(eq(patronId), patronCaptor.capture());
        Patron updatedPatron = patronCaptor.getValue();
        assertEquals("Updated Patron Name", updatedPatron.getName());
        assertEquals("987654321", updatedPatron.getPhone());
        assertEquals("updated.patron@example.com", updatedPatron.getEmail());
        assertEquals("321 Main St", updatedPatron.getAddress());
    }

    @Test
    void deletePatron_WhenPatronExists_ShouldDeletePatron() throws Exception {
        // Arrange
        Long patronId = 1L;
        willDoNothing().given(patronService).deletePatron(patronId);

        // Act & Assert
        mockMvc.perform(delete("/api/patrons/{id}", patronId))
                .andExpect(status().isNoContent());

        // Verify the patron service is called to delete the correct patron
        verify(patronService).deletePatron(patronId);
    }
}