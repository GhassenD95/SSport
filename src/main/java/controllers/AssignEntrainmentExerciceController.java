package controllers;

import common.INavigation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import models.module2.Entrainment;
import models.module2.Exercice;
import services.events.EventBus;
import services.jdbc.module2.ServiceEntrainment;
import services.utilities.NavigationService;

import java.sql.SQLException;
import java.util.List;

public class AssignEntrainmentExerciceController extends BaseController implements INavigation {
    @FXML
    private VBox title;
    @FXML
    private VBox content;
    @FXML
    private VBox filter;
    @FXML
    private VBox exercices;

    private List<Exercice> exerciseList;
    private Entrainment entrainment; // Store entrainment for reloading
    private NavigationService navigationService;

    @Override
    public void setData(Object data) {
        super.setData(data);
        if (data instanceof Entrainment) {
            this.entrainment = (Entrainment) data;
            System.out.println("Entrainment received: " + this.entrainment.getId());
            loadExercises();
            reloadComponents(); // Reload components after setting data
        } else {
            System.err.println("Error: Invalid data type passed to AssignEntrainmentExerciceController. Expected Entrainment.");
        }
    }


    public void initialize() {
        this.navigationService = new NavigationService(content);

        navigationService.loadComponent("/views/components/title-banner.fxml", title, "Attribuer des exercices à une séance d'entraînement");
        navigationService.loadComponent("/views/components/search-bar.fxml", filter);

        // Subscribe to "EXERCISES_UPDATED" event to refresh exercises when triggered
        EventBus.subscribe("EXERCISES_UPDATED", (String data) -> {
            loadExercises(); // Reload exercises
            Platform.runLater(this::reloadComponents); // Update UI
        });
    }

    private void loadExercises() {
        try {
            if (entrainment != null) {
                this.exerciseList = new ServiceEntrainment().get(entrainment.getId()).getExercices();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void reloadComponents() {
        content.getChildren().clear();
        exercices.getChildren().clear();
        System.out.println("########" + this.entrainment);

        // Load the assignment grid
        navigationService.loadComponent("/views/components/assignment-grid.fxml", content, this.entrainment);

        // Load the assigned exercises
        navigationService.loadComponent("/views/components/assigned-exercices.fxml", exercices, exerciseList);

        // Publish the entrainment object via EventBus
        EventBus.publish("SET_ENTRAINMENT", this.entrainment);
    }

}
