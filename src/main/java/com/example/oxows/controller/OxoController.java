package com.example.oxows.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.oxows.dao.OxoRepository;
import com.example.oxows.entity.Oxo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sondages")
public class OxoController {

    @Autowired
    private OxoRepository oxoRepository;

    @Autowired
    private Validator validator;


    @GetMapping("/")
    public List<Oxo> getAllOxo() {
        return oxoRepository.findByDateClotureAfter(LocalDate.now());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oxo> getOxoById(@PathVariable(value = "id") Long id) {
        Optional<Oxo> oxo = oxoRepository.findById(id);
        if (oxo.isPresent()) {
            return ResponseEntity.ok().body(oxo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Oxo> addOxo(@RequestBody Oxo oxo) {

        Set<ConstraintViolation<Oxo>> violations = validator.validate(oxo);
        if (!violations.isEmpty()) {

            throw new ConstraintViolationException(violations);
        }

        Oxo savedOxo = oxoRepository.save(oxo);

        return ResponseEntity.ok().body(savedOxo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Oxo> updateOxo(@PathVariable(value = "id") Long id, @RequestBody Oxo oxoDetails) {
        Optional<Oxo> oxo = oxoRepository.findById(id);
        if (oxo.isPresent()) {
            Set<ConstraintViolation<Oxo>> violations = validator.validate(oxoDetails);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            Oxo existingOxo = oxo.get();
            existingOxo.setDescription(oxoDetails.getDescription());
            existingOxo.setQuestion(oxoDetails.getQuestion());
            existingOxo.setDateCloture(oxoDetails.getDateCloture());
            existingOxo.setCreateur(oxoDetails.getCreateur());
            Oxo updatedOxo = oxoRepository.save(existingOxo);

            return ResponseEntity.ok().body(updatedOxo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOxo(@PathVariable(value = "id") Long id) {
        Optional<Oxo> oxo = oxoRepository.findById(id);
        if (oxo.isPresent()) {
            oxoRepository.delete(oxo.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
