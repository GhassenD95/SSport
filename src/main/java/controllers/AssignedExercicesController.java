package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import models.module2.Entrainment;
import models.module2.Exercice;
import models.module2.ExerciceEntrainment;
import services.events.EventBus;
import services.jdbc.module2.ServiceExerciceEntrainment;
import services.utilities.ImageLoader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignedExercicesController extends BaseController implements INavigation {

    @FXML
    private HBox exercicesImgContainer;

    private List<Exercice> exercices;
    private Entrainment entrainment; // Store the associated Entrainment

    @Override
    public void setData(Object data) {
        super.setData(data);
        if (data instanceof List) {
            this.exercices = (List<Exercice>) data;
            System.out.println(exercices);
            loadExerciceImages();
        }
    }


    public void initialize() {
        // Subscribe to the SET_ENTRAINMENT event
        EventBus.subscribe("SET_ENTRAINMENT", (entrainment) -> {
            if (entrainment instanceof Entrainment) {
                this.entrainment = (Entrainment) entrainment;
                System.out.println("Entrainment received in AssignedExercicesController: " + this.entrainment.getId());
            }
        });
    }

    private void loadExerciceImages() {
        exercicesImgContainer.getChildren().clear();
        for (Exercice exercice : exercices) {
            ImageView exerciceImg = new ImageView();
            exerciceImg.setFitHeight(50);
            exerciceImg.setFitWidth(50);
            exerciceImg.setImage(ImageLoader.loadImage("exercices", exercice.getImage_url()));

            // Add click event handler to delete the exercise
            exerciceImg.setOnMouseClicked(event -> deleteExercice(exercice, event));

            exercicesImgContainer.getChildren().add(exerciceImg);
        }
    }

    private void deleteExercice(Exercice exercice, MouseEvent event) {
        if (entrainment == null) {
            System.err.println("Error: Entrainment is not set.");
            return;
        }

        System.out.println("Deleting Exercice: " + exercice.getNom() +
                " from Entrainment: " + entrainment.getId());

        try {
            // Create an ExerciceEntrainment object to represent the association
            ExerciceEntrainment exEntrainment = new ExerciceEntrainment();
            exEntrainment.setExercice(exercice);
            exEntrainment.setEntrainment(entrainment);

            // Delete the association from the database
            new ServiceExerciceEntrainment().delete(exEntrainment);
            System.out.println("Exercice deleted");

            // Remove the exercise from the entrainment's list of exercises
            entrainment.getExercices().remove(exercice);

            // Notify that exercises have been updated
            EventBus.publish("EXERCISES_UPDATED", "Exercise deleted");

            // Reload the images
            loadExerciceImages();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
