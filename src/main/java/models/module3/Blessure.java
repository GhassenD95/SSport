package models.module3;

import enums.TypeBlessure;
import models.module1.Utilisateur;

import java.time.LocalDateTime;

public class Blessure {
    private int id;
    private Utilisateur athlete;
    private TypeBlessure typeBlessure;
    private String description;
    private LocalDateTime dateBlessure;
    private LocalDateTime dateReprise;

    public Blessure(int id, Utilisateur athlete, TypeBlessure typeBlessure, String description, LocalDateTime dateBlessure, LocalDateTime dateReprise) {
        this.id = id;
        this.athlete = athlete;
        this.typeBlessure = typeBlessure;
        this.description = description;
        this.dateBlessure = dateBlessure;
        this.dateReprise = dateReprise;
    }

    public Blessure(Utilisateur athlete, TypeBlessure typeBlessure, String description, LocalDateTime dateBlessure, LocalDateTime dateReprise) {
        this.athlete = athlete;
        this.typeBlessure = typeBlessure;
        this.description = description;
        this.dateBlessure = dateBlessure;
        this.dateReprise = dateReprise;
    }

    public Blessure() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getAthlete() {
        return athlete;
    }

    public void setAthlete(Utilisateur athlete) {
        this.athlete = athlete;
    }

    public TypeBlessure getTypeBlessure() {
        return typeBlessure;
    }

    public void setTypeBlessure(TypeBlessure typeBlessure) {
        this.typeBlessure = typeBlessure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateBlessure() {
        return dateBlessure;
    }

    public void setDateBlessure(LocalDateTime dateBlessure) {
        this.dateBlessure = dateBlessure;
    }

    public LocalDateTime getDateReprise() {
        return dateReprise;
    }

    public void setDateReprise(LocalDateTime dateReprise) {
        this.dateReprise = dateReprise;
    }
}
