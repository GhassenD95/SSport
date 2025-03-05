package controllers;

import common.INavigation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.module2.Exercice;
import services.events.EventBus;
import services.jdbc.module2.ServiceExercice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ExercicesGridController extends BaseController implements INavigation {

    @FXML
    private GridPane gridContainer;

    private List<Exercice> allExercices; // Store full list

    public void initialize() {
        try {
            allExercices = new ServiceExercice().getAll();
            loadGrid(allExercices);

            // Listen for filter events
            EventBus.subscribe("FILTER_CHANGED", (String data) -> {
                Platform.runLater(() -> filterExercices(data));
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadGrid(List<Exercice> exercices) {
        gridContainer.getChildren().clear(); // Clear previous items

        int column = 0, row = 0;
        for (Exercice exercice : exercices) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/components/exercice-card.fxml"));
                VBox card = loader.load();

                ExerciceCardController cardController = loader.getController();
                cardController.setData(exercice);

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
            loadGrid(allExercices); // Reload full list when input is empty
            return;
        }

        List<Exercice> filtered = allExercices.stream()
                .filter(ex -> ex.getNom().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        loadGrid(filtered); // Reload grid with filtered items
    }
}
