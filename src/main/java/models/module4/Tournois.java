package models.module4;

import enums.Sport;
import models.module5.MatchSportif;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tournois {
    private int id;
    private String nom;
    private Sport sport;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String adresse;

    private List<MatchSportif> matches;
    private List<PerformanceEquipe> performanceEquipes;

    public Tournois(int id, String nom, Sport sport, LocalDateTime dateDebut, LocalDateTime dateFin, String adresse) {
        this.id = id;
        this.nom = nom;
        this.sport = sport;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.adresse = adresse;
        this.matches = new ArrayList<>();
        this.performanceEquipes = new ArrayList<>();
    }

    public Tournois(String nom, Sport sport, LocalDateTime dateDebut, LocalDateTime dateFin, String adresse) {
        this.nom = nom;
        this.sport = sport;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.adresse = adresse;
        this.matches = new ArrayList<>();
        this.performanceEquipes = new ArrayList<>();

    }

    public Tournois() {
        this.matches = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<MatchSportif> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchSportif> matches) {
        this.matches = matches;
    }

    public List<PerformanceEquipe> getPerformanceEquipes() {
        return performanceEquipes;
    }

    public void setPerformanceEquipes(List<PerformanceEquipe> performanceEquipes) {
        this.performanceEquipes = performanceEquipes;
    }
}
