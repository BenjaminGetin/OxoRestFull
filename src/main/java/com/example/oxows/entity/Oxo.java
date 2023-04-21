package com.example.oxows.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "oxo")
public class Oxo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 120)
    private String description;

    @Size(max = 120)
    private String question;

    @NotNull
    @Column(name = "date_creation", updatable = false)
    private LocalDate dateCreation = LocalDate.now();

    @Future
    @NotNull
    @Column(name = "date_cloture")
    private LocalDate dateCloture;

    @NotBlank
    @Size(max = 64)
    private String createur;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate creation) {
        this.dateCreation = creation;
    }

    public LocalDate getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDate cloture) {
        this.dateCloture = cloture;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public Oxo() {

    }

    public Oxo(String description, String question, LocalDate creation, LocalDate cloture, String createur) {
        this.description = description;
        this.question = question;
        this.dateCreation = creation;
        this.dateCloture = cloture;
        this.createur = createur;
    }
}
