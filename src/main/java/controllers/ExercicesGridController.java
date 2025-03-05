package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.module2.Exercice;
import services.jdbc.module2.ServiceExercice;
import services.utilities.NavigationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ExercicesGridController extends BaseController implements INavigation {

    @FXML
    private GridPane gridContainer;

    public void initialize() {
        try {
            // Fetch the list of exercices from the database
            List<Exercice> exercices = new ServiceExercice().getAll();

            int column = 0;
            int row = 0;

            for (Exercice exercice : exercices) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/components/exercice-card.fxml"));
                VBox card = loader.load();

                // Access the controller of the card if needed
                ExerciceCardController cardController = loader.getController();
                cardController.setData(exercice);

                // Set margin

                // Add to the grid
                gridContainer.add(card, column, row);

                // Move to the next column/row
                column++;
                if (column == 3) { // Assuming 3 cards per row
                    column = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
