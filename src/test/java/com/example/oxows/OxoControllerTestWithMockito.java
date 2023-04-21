package com.example.oxows;

import com.example.oxows.controller.OxoController;
import com.example.oxows.dao.OxoRepository;
import com.example.oxows.entity.Oxo;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OxoControllerTestWithMockito {

    @Mock
    private OxoRepository oxoRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private OxoController oxoController;

    @Test
    public void testGetAllOxo() {
        List<Oxo> oxoList = new ArrayList<>();
        oxoList.add(new Oxo("Description 1", "Question 1", LocalDate.now(), LocalDate.now().plusDays(5), "Createur 1"));
        oxoList.add(new Oxo("Description 2", "Question 2", LocalDate.now().plusDays(1), LocalDate.now().plusDays(6), "Createur 2"));

        when(oxoRepository.findByDateClotureAfter(LocalDate.now())).thenReturn(oxoList);

        List<Oxo> result = oxoController.getAllOxo();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetOxoById() {
        Long oxoId = 1L;
        Oxo oxo = new Oxo("Description 1", "Question 1", LocalDate.now(), LocalDate.now().plusDays(5), "Createur 1");
        oxo.setId(oxoId);

        when(oxoRepository.findById(oxoId)).thenReturn(Optional.of(oxo));

        ResponseEntity<Oxo> result = oxoController.getOxoById(oxoId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(oxoId, result.getBody().getId());
    }

    @Test
    public void testAddOxo() {
        Oxo oxo = new Oxo("Description 1", "Question 1", LocalDate.now(), LocalDate.now().plusDays(5), "Createur 1");

        when(validator.validate(oxo)).thenReturn(Collections.emptyList());
        when(oxoRepository.save(oxo)).thenReturn(oxo);

        ResponseEntity<Oxo> result = oxoController.addOxo(oxo);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(oxo.getDescription(), result.getBody().getDescription());
    }


    @Test
    public void testUpdateOxo() {
        // create a mock Oxo object and set its values
        Oxo oxo = new Oxo("Description du sondage", "Question du sondage", LocalDate.now(), LocalDate.now().plusDays(5), "Benjamin");
        oxo.setId(1L);

        // create a mock updated Oxo object with new values
        Oxo updatedOxo = new Oxo("Nouvelle description du sondage", "Nouvelle question du sondage", LocalDate.now().plusDays(1), LocalDate.now().plusDays(6), "Thomas");
        updatedOxo.setId(1L);

        // mock the OxoRepository.findById() method to return the mock Oxo object
        Long oxoId = 1L;
        when(oxoRepository.findById(oxoId)).thenReturn(Optional.of(oxo));
        when(validator.validate(updatedOxo)).thenReturn(Collections.emptySet());
        when(oxoRepository.save(oxo)).thenReturn(updatedOxo);


        // call the updateOxo() method with the mock updated Oxo object
        ResponseEntity<Oxo> response = oxoController.updateOxo(oxoId, updatedOxo);

        // assert that the response has an HTTP OK status code and the updated Oxo object has the expected values
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedOxo.getDescription(), response.getBody().getDescription());
        assertEquals(updatedOxo.getQuestion(), response.getBody().getQuestion());
        assertEquals(updatedOxo.getCreateur(), response.getBody().getCreateur());
        assertEquals(updatedOxo.getDateCreation(), response.getBody().getDateCreation());
        assertEquals(updatedOxo.getDateCloture(), response.getBody().getDateCloture());
    }

    @Test
    void testDeleteOxo() {
        Long oxoId = 1L;
        Oxo oxo = new Oxo("Description du sondage", "Question du sondage", LocalDate.now(), LocalDate.now().plusDays(5), "Benjamin");
        when(oxoRepository.findById(oxoId)).thenReturn(Optional.of(oxo));

        ResponseEntity<?> response = oxoController.deleteOxo(oxoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(oxoRepository, times(1)).delete(oxo);
    }
}

