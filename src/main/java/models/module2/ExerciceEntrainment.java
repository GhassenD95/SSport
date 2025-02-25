package models.module2;

public class ExerciceEntrainment {
    private int id;
    private Exercice exercice;
    private Entrainment entrainment;

    public ExerciceEntrainment(int id, Exercice exercice, Entrainment entrainment) {
        this.id = id;
        this.exercice = exercice;
        this.entrainment = entrainment;
    }

    public ExerciceEntrainment(Exercice exercice, Entrainment entrainment) {
        this.exercice = exercice;
        this.entrainment = entrainment;
    }

    public ExerciceEntrainment() {

    }

    public Exercice getExercice() {
        return exercice;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entrainment getEntrainment() {
        return entrainment;
    }

    public void setEntrainment(Entrainment entrainment) {
        this.entrainment = entrainment;
    }
}
