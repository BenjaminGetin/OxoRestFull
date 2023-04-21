package com.example.oxows.dao;

import com.example.oxows.entity.Oxo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OxoRepository extends JpaRepository<Oxo, Long> {
    List<Oxo> findByDateClotureAfter(LocalDate date);
}
