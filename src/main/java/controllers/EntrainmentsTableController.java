package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.module2.Entrainment;
import services.jdbc.module2.ServiceEntrainment;
import services.utilities.NavigationService;

import java.sql.SQLException;
import java.util.List;

public class EntrainmentsTableController extends BaseController implements INavigation {

    @FXML
    private VBox cardsContainer;

    public void initialize() {
        try {
            // Fetch all entrainments from the database
            List<Entrainment> entrainments = new ServiceEntrainment().getAll();
            // Initialize the NavigationService once
            NavigationService navigationService = new NavigationService(this.cardsContainer);

            cardsContainer.getChildren().clear();
            // Load a card for each entrainment
            for (Entrainment entrainment : entrainments) {
                navigationService.loadComponent("/views/components/entrainment-card.fxml", cardsContainer, entrainment);
                System.out.println(entrainment.getNom());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch entrainments from the database", e);
        }
    }



}
