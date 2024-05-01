package com.bookwise.bookwise.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bookwise.bookwise.entity.Patron;
import com.bookwise.bookwise.exception.NotFoundException;
import com.bookwise.bookwise.repository.PatronRepository;
import com.bookwise.bookwise.service.impl.PatronServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;

class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllPatrons_ShouldReturnAllPatrons() {
        // Arrange
        when(patronRepository.findAll()).thenReturn(List.of(new Patron(), new Patron()));

        // Act
        List<Patron> result = patronService.findAllPatrons();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void findPatronById_WhenPatronExists_ShouldReturnPatron() {
        // Arrange
        Long patronId = 1L;
        Patron patron = new Patron();
        patron.setId(patronId); // Set the ID to match the test case
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));

        // Act
        Patron result = patronService.findPatronById(patronId);

        // Assert
        assertNotNull(result);
        assertEquals(patronId, result.getId());
    }

    @Test
    void findPatronById_WhenPatronDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        Long patronId = 1L;
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> patronService.findPatronById(patronId));
    }

    @Test
    void createPatron_ShouldSaveNewPatron() {
        // Arrange
        Patron newPatron = new Patron();

        // Act
        patronService.createPatron(newPatron);

        // Assert
        verify(patronRepository).save(newPatron);
    }

    @Test
    void updatePatron_WhenPatronExists_ShouldUpdatePatronDetails() {
        // Arrange
        Long patronId = 1L;
        Patron existingPatron = new Patron();
        existingPatron.setName("Old Name");
        Patron updatedPatronDetails = new Patron();
        updatedPatronDetails.setName("New Name");

        when(patronRepository.findById(patronId)).thenReturn(Optional.of(existingPatron));

        // Act
        patronService.updatePatron(patronId, updatedPatronDetails);

        // Assert
        assertEquals("New Name", existingPatron.getName());
        verify(patronRepository).save(existingPatron);
    }

    @Test
    void updatePatron_WhenPatronDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        Long patronId = 1L;
        Patron updatedPatronDetails = new Patron();
        updatedPatronDetails.setName("New Name");

        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> patronService.updatePatron(patronId, updatedPatronDetails));
    }

    @Test
    void deletePatron_WhenPatronExists_ShouldDeletePatron() {
        // Arrange
        Long patronId = 1L;
        Patron existingPatron = new Patron();
        existingPatron.setId(patronId); // Set the ID to match the test case
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(existingPatron));

        // Act
        patronService.deletePatron(patronId);

        // Assert
        verify(patronRepository).deleteById(patronId);
    }

    @Test
    void deletePatron_WhenPatronDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        Long patronId = 1L;
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> patronService.deletePatron(patronId));
    }
}