package com.example.oxows;

import com.example.oxows.dao.OxoRepository;
import com.example.oxows.entity.Oxo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OxoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OxoRepository oxoRepository;

    @Test
    public void testAddOxo() {
        Oxo oxo = new Oxo("Description du sondage", "Question du sondage", LocalDate.now(), LocalDate.now().plusDays(7), "John Doe");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Oxo> request = new HttpEntity<>(oxo, headers);
        ResponseEntity<Oxo> response = restTemplate.postForEntity("/api/oxos/", request, Oxo.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(oxo.getDescription(), response.getBody().getDescription());
        assertEquals(oxo.getQuestion(), response.getBody().getQuestion());
        assertEquals(oxo.getCreateur(), response.getBody().getCreateur());
    }

}

