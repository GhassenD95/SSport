package controllers;

import common.INavigation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.module2.Entrainment;
import models.module2.Exercice;
import services.events.EventBus;
import services.jdbc.module2.ServiceExercice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;



public class AssignmentGridController extends BaseController implements INavigation {
    @FXML
    private GridPane gridContainer;

    private List<Exercice> allExercices; // Store full list
    private Entrainment entrainment; // Store the current Entrainment

    @Override
    public void setData(Object data) {
        if (data instanceof Entrainment) {
            this.entrainment = (Entrainment) data;
            System.out.println("Entrainment set in AssignmentGridController: " + this.entrainment.getId());
            loadUnassignedExercices(); // Load unassigned exercises
        } else {
            System.err.println("Error: Invalid data type passed to AssignmentGridController. Expected Entrainment.");
        }
    }

    public void initialize() {
        try {
            // Load all exercises
            allExercices = new ServiceExercice().getAll();

            // Listen for filter events
            EventBus.subscribe("FILTER_CHANGED", (String keyword) -> {
                Platform.runLater(() -> filterExercices(keyword));
            });

            // Listen for EXERCISES_UPDATED event
            EventBus.subscribe("EXERCISES_UPDATED", (String data) -> {
                Platform.runLater(this::loadUnassignedExercices); // Reload unassigned exercises
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUnassignedExercices() {
        System.out.println("Loading unassigned exercises for Entrainment: " + entrainment.getId());
        if (entrainment == null) {
            System.err.println("Error: Entrainment is null. Cannot load unassigned exercises.");
            return;
        }

        // Get the list of exercises already assigned to the Entrainment
        List<Exercice> assignedExercices = entrainment.getExercices();
        System.out.println("Assigned exercises: " + assignedExercices.size());

        // Filter out assigned exercises from the full list
        List<Exercice> unassignedExercices = allExercices.stream()
                .filter(ex -> !assignedExercices.contains(ex)) // Exclude assigned exercises
                .collect(Collectors.toList());

        System.out.println("Unassigned exercises: " + unassignedExercices.size());

        // Load the grid with unassigned exercises
        loadGrid(unassignedExercices);
    }

    private void loadGrid(List<Exercice> exercices) {
        gridContainer.getChildren().clear(); // Clear previous items

        int column = 0, row = 0;
        for (Exercice exercice : exercices) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/components/assignment-card.fxml"));
                VBox card = loader.load();

                AssignmentCardController cardController = loader.getController();
                cardController.setData(exercice);
                cardController.setEntrainment(entrainment); // Pass the Entrainment object

                gridContainer.add(card, column, row);

                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void filterExercices(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            loadUnassignedExercices(); // Reload unassigned exercises when input is empty
            return;
        }

        // Get the list of exercises already assigned to the Entrainment
        List<Exercice> assignedExercices = entrainment.getExercices();

        // Filter unassigned exercises by keyword
        List<Exercice> filtered = allExercices.stream()
                .filter(ex -> !assignedExercices.contains(ex)) // Exclude assigned exercises
                .filter(ex -> ex.getNom().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        loadGrid(filtered); // Reload grid with filtered items
    }
}
