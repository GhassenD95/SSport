package models.module1;

import enums.Division;
import enums.Sport;
import models.module2.Entrainment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Equipe {
    private int id;
    private String nom;
    private Division division;
    private Sport sport;
    private boolean isLocal;
    private Utilisateur coach;

    List<Utilisateur> athletes;
    List<Entrainment> entrainments;

    public Equipe() {
        this.athletes = new ArrayList<>();
        this.entrainments = new ArrayList<>();
    }


    public Equipe(int id, String nom, Sport sport, Division division, boolean isLocal, Utilisateur coach) {
        this.id = id;
        this.nom = nom;
        this.sport = sport;
        this.division = division;
        this.isLocal = isLocal;
        this.coach = coach;
        this.athletes = new ArrayList<>();
        this.entrainments = new ArrayList<>();

    }

    public Equipe(String nom, Sport sport, Division division, boolean isLocal, Utilisateur coach) {
        this.nom = nom;
        this.sport = sport;
        this.division = division;
        this.isLocal = isLocal;
        this.coach = coach;
        this.athletes = new ArrayList<>();
        this.entrainments = new ArrayList<>();
    }

    public Equipe(String nom, Sport sport, Division division, boolean isLocal) {
        this.nom = nom;
        this.division = division;
        this.sport = sport;
        this.isLocal = isLocal;
        this.athletes = new ArrayList<>();
        this.entrainments = new ArrayList<>();
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

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setIsLocal(boolean local) {
        isLocal = local;
    }

    public Utilisateur getCoach() {
        return coach;
    }

    public void setCoach(Utilisateur coach) {
        this.coach = coach;
    }

    public List<Utilisateur> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Utilisateur> athletes) {
        this.athletes = athletes;
    }

    public List<Entrainment> getEntrainments() {
        return entrainments;
    }

    public void setEntrainments(List<Entrainment> entrainments) {
        this.entrainments = entrainments;
    }


    @Override
    public String toString() {
        return "Equipe{" +
                "nom='" + nom + '\'' +
                ", division=" + division +
                ", sport=" + sport +
                ", isLocal=" + isLocal +
                ", coach=" + coach +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Equipe equipe)) return false;
        return id == equipe.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
