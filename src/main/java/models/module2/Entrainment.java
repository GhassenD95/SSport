package models.module2;

import models.module1.Equipe;
import models.module6.InstallationSportive;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Entrainment {
    private int id;
    private String nom;
    private Equipe equipe;
    private InstallationSportive installationSportive;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private List<Exercice> exercices;


    public Entrainment(int id, String nom, Equipe equipe, InstallationSportive installationSportive, String description, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.id = id;
        this.nom = nom;
        this.equipe = equipe;
        this.installationSportive = installationSportive;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.exercices = new ArrayList<>();
    }

    public Entrainment(String nom, Equipe equipe, InstallationSportive installationSportive, String description, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.nom = nom;
        this.equipe = equipe;
        this.installationSportive = installationSportive;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.exercices = new ArrayList<>();
    }

    public Entrainment() {
        this.exercices = new ArrayList<>();

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

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public InstallationSportive getInstallationSportive() {
        return installationSportive;
    }

    public void setInstallationSportive(InstallationSportive installationSportive) {
        this.installationSportive = installationSportive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Exercice> getExercices() {
        return exercices;
    }

    public void setExercices(List<Exercice> exercices) {
        this.exercices = exercices;
    }

    @Override
    public String toString() {
        return "Entrainment{" +
                "nom='" + nom + '\'' +
                ", equipe=" + equipe +
                ", installationSportive=" + installationSportive +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", exercices=" + exercices +
                '}';
    }
}
