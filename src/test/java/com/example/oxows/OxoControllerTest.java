package com.example.oxows;

import com.example.oxows.dao.OxoRepository;
import com.example.oxows.entity.Oxo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OxoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    private static Oxo testOxo;

    @Test
    @Order(1)
    public void testAddOxo() {
        Oxo oxo = new Oxo("Description du sondage", "Question du sondage", LocalDate.now(), LocalDate.now().plusDays(5), "Benjamin");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Oxo> request = new HttpEntity<>(oxo, headers);
        ResponseEntity<Oxo> response = restTemplate.postForEntity("/api/sondages/", request, Oxo.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(oxo.getDescription(), response.getBody().getDescription());
        assertEquals(oxo.getQuestion(), response.getBody().getQuestion());
        assertEquals(oxo.getCreateur(), response.getBody().getCreateur());

        testOxo = response.getBody();
    }

    @Test
    @Order(2)
    public void testGetAllOxo() {
        ResponseEntity<List<Oxo>> response = restTemplate.exchange(
                "/api/sondages/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Oxo>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @  Order(3)
    public void testUpdateOxo() {

        testOxo.setDescription("Nouvelle description du sondage");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Oxo> requestUpdate = new HttpEntity<>(testOxo, headers);
        ResponseEntity<Oxo> responseUpdate = restTemplate.exchange("/api/sondages/" + testOxo.getId(), HttpMethod.PUT, requestUpdate, Oxo.class);
        assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());
        Oxo updatedOxo = responseUpdate.getBody();

        assertEquals(testOxo.getId(), updatedOxo.getId());
        assertEquals("Nouvelle description du sondage", updatedOxo.getDescription());

        testOxo = updatedOxo;
    }

    @Test
    @Order(4)
    public void testGetOxyById() {
        ResponseEntity<Oxo> response = restTemplate.getForEntity("/api/sondages/" + testOxo.getId(), Oxo.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testOxo.getId(), response.getBody().getId());
    }

    @Test
    @Order(5)
    public void testDeleteOxo() {
        restTemplate.delete("/api/sondages/" + testOxo.getId());

        ResponseEntity<Oxo> responseGet = restTemplate.getForEntity("/api/sondages/" + testOxo.getId(), Oxo.class);
        assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }
}