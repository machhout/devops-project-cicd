package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enables Mockito in JUnit 5 tests
class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository; // Mock dependency

    @InjectMocks
    private EtudiantServiceImpl etudiantService; // Service under test

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testRetrieveEtudiant() {
        // Arrange: Set up mock data
        Etudiant etudiant = new Etudiant("John", "Doe", 123456789L);
        when(etudiantRepository.findById(anyLong())).thenReturn(Optional.of(etudiant));

        // Act: Call the method under test
        Etudiant result = etudiantService.retrieveEtudiant(1L);

        // Assert: Verify the expected results
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        assertEquals("Doe", result.getPrenomEtudiant());
        assertEquals(123456789L, result.getCinEtudiant());

        // Verify the repository interaction
        verify(etudiantRepository).findById(anyLong());
    }

    @Test
    void testAddEtudiant() {
        // Arrange: Set up mock behavior
        Etudiant etudiant = new Etudiant("Jane", "Doe", 987654321L);
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // Act: Call the method under test
        Etudiant result = etudiantService.addEtudiant(etudiant);

        // Assert: Verify the addition was successful
        assertNotNull(result);
        assertEquals("Jane", result.getNomEtudiant());
        assertEquals("Doe", result.getPrenomEtudiant());

        // Verify that save was called on the repository
        verify(etudiantRepository).save(etudiant);
    }

    @Test
    void testRemoveEtudiant() {
        // Arrange: Mock the repository deleteById method
        Long etudiantId = 1L;
        doNothing().when(etudiantRepository).deleteById(etudiantId);

        // Act: Call the remove method in the service
        etudiantService.removeEtudiant(etudiantId);

        // Assert: Verify deleteById was called correctly
        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }
}
