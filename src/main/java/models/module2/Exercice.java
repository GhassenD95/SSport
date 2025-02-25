package models.module2;

import enums.TypeExercice;

import java.util.ArrayList;
import java.util.List;

public class Exercice {
    private int id;
    private String nom;
    private TypeExercice typeExercice;
    private int dureeMinutes;
    private int sets;
    private int reps;
    private String image_url;

    private List<Entrainment> entrainments;

    public Exercice(int id, String nom, TypeExercice typeExercice, int dureeMinutes, int sets, int reps, String image_url) {
        this.id = id;
        this.nom = nom;
        this.typeExercice = typeExercice;
        this.dureeMinutes = dureeMinutes;
        this.sets = sets;
        this.reps = reps;
        this.image_url = image_url;
        entrainments = new ArrayList<>();
    }

    public Exercice(String nom, TypeExercice typeExercice, int dureeMinutes, int sets, int reps, String image_url) {
        this.nom = nom;
        this.typeExercice = typeExercice;
        this.dureeMinutes = dureeMinutes;
        this.sets = sets;
        this.reps = reps;
        this.image_url = image_url;
        entrainments = new ArrayList<>();
    }

    public Exercice() {
        entrainments = new ArrayList<>();
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

    public TypeExercice getTypeExercice() {
        return typeExercice;
    }

    public void setTypeExercice(TypeExercice typeExercice) {
        this.typeExercice = typeExercice;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public void setDureeMinutes(int dureeMinutes) {
        this.dureeMinutes = dureeMinutes;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Exercice{" +
                "nom='" + nom + '\'' +
                ", typeExercice=" + typeExercice +
                ", dureeMinutes=" + dureeMinutes +
                ", sets=" + sets +
                ", reps=" + reps +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
