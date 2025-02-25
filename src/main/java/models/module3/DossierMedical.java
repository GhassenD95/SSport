package models.module3;

import enums.EtatAthlete;
import models.module1.Utilisateur;

import java.time.LocalDateTime;

public class DossierMedical {
    private int id;
    private Utilisateur athlete;
    private LocalDateTime dernierCheckup;
    private String allergies;
    private String vaccinations;
    private EtatAthlete etatAthlete;
    private String description;

    public DossierMedical() {
    }

    public DossierMedical(Utilisateur athlete, LocalDateTime dernierCheckup, String allergies, String vaccinations, EtatAthlete etatAthlete, String description) {
        this.athlete = athlete;
        this.dernierCheckup = dernierCheckup;
        this.allergies = allergies;
        this.vaccinations = vaccinations;
        this.etatAthlete = etatAthlete;
        this.description = description;
    }

    public DossierMedical(int id, Utilisateur athlete, LocalDateTime dernierCheckup, String allergies, String vaccinations, EtatAthlete etatAthlete, String description) {
        this.id = id;
        this.athlete = athlete;
        this.dernierCheckup = dernierCheckup;
        this.allergies = allergies;
        this.vaccinations = vaccinations;
        this.etatAthlete = etatAthlete;
        this.description = description;
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

    public LocalDateTime getDernierCheckup() {
        return dernierCheckup;
    }

    public void setDernierCheckup(LocalDateTime dernierCheckup) {
        this.dernierCheckup = dernierCheckup;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(String vaccinations) {
        this.vaccinations = vaccinations;
    }

    public EtatAthlete getEtatAthlete() {
        return etatAthlete;
    }

    public void setEtatAthlete(EtatAthlete etatAthlete) {
        this.etatAthlete = etatAthlete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
