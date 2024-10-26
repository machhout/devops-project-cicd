package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    private Etudiant etudiant;

    @BeforeEach
    void setup() {
        // Initialize a sample Etudiant object to use in tests
        etudiant = new Etudiant("John", "Doe", 123456789L);
    }

    @Test
    void testRetrieveEtudiant() {
        // Arrange: Set up mock behavior
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Act: Call the method under test
        Etudiant result = etudiantService.retrieveEtudiant(1L);

        // Assert: Verify the expected results
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        assertEquals("Doe", result.getPrenomEtudiant());
        assertEquals(123456789L, result.getCinEtudiant());

        // Verify the repository interaction
        verify(etudiantRepository).findById(1L);
    }

    @Test
    void testAddEtudiant() {
        // Act: Call the method under test
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);
        Etudiant result = etudiantService.addEtudiant(etudiant);

        // Assert: Verify the addition was successful
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        assertEquals("Doe", result.getPrenomEtudiant());

        // Verify that save was called on the repository
        verify(etudiantRepository).save(etudiant);
    }

    @Test
    void testRemoveEtudiant() {
        Long etudiantId = 1L; // The ID of the Etudiant to remove

        // Act: Call the method under test
        etudiantService.removeEtudiant(etudiantId);

        // Assert: Verify deleteById was called correctly
        verify(etudiantRepository).deleteById(etudiantId);
    }
}
