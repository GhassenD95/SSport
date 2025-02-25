package models.module5;

import models.module1.Equipe;
import models.module4.Tournois;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MatchSportif {
    private int id;
    private Tournois tournois;
    private Equipe equipe1;
    private Equipe equipe2;
    private LocalDateTime date;
    private String lieu;

    private List<PerformanceAthlete> performanceAthletes;

    public MatchSportif(int id, Tournois tournois, Equipe equipe1, Equipe equipe2, LocalDateTime date, String lieu) {
        this.id = id;
        this.tournois = tournois;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.date = date;
        this.lieu = lieu;
        this.performanceAthletes = new ArrayList<>();
    }

    public MatchSportif(Tournois tournois, Equipe equipe1, Equipe equipe2, LocalDateTime date, String lieu) {
        this.tournois = tournois;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.date = date;
        this.lieu = lieu;
        this.performanceAthletes = new ArrayList<>();

    }

    public MatchSportif() {
        this.performanceAthletes = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tournois getTournois() {
        return tournois;
    }

    public void setTournois(Tournois tournois) {
        this.tournois = tournois;
    }

    public Equipe getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(Equipe equipe1) {
        this.equipe1 = equipe1;
    }

    public Equipe getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(Equipe equipe2) {
        this.equipe2 = equipe2;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }



    public List<PerformanceAthlete> getPerformanceAthletes() {
        return performanceAthletes;
    }

    public void setPerformanceAthletes(List<PerformanceAthlete> performanceAthletes) {
        this.performanceAthletes = performanceAthletes;
    }
}
