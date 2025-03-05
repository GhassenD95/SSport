package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import models.module2.Entrainment;
import services.events.EventBus;
import services.jdbc.module2.ServiceEntrainment;
import services.utilities.NavigationService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EntrainmentsTableController extends BaseController implements INavigation {

    @FXML
    private VBox cardsContainer;

    // Declare navigationService as a class-level field
    private NavigationService navigationService;

    public void initialize() {
        try {
            // Initialize the NavigationService with the cardsContainer
            navigationService = new NavigationService(this.cardsContainer);

            // Fetch all entrainments from the database
            List<Entrainment> entrainments = new ServiceEntrainment().getAll();
            loadEntrainments(entrainments);

            // Subscribe to the filter event
            EventBus.subscribe("filter-entrainment", (filters) -> {
                try {
                    // Fetch filtered entrainments from the database
                    List<Entrainment> filteredEntrainments = new ServiceEntrainment().getFilteredEntrainments(
                            (Map<String, Object>) filters
                    );
                    loadEntrainments(filteredEntrainments);
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to fetch filtered entrainments from the database", e);
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch entrainments from the database", e);
        }
    }

    private void loadEntrainments(List<Entrainment> entrainments) {
        // Clear the current cards
        cardsContainer.getChildren().clear();

        // Load a card for each entrainment
        for (Entrainment entrainment : entrainments) {
            navigationService.loadComponent("/views/components/entrainment-card.fxml", cardsContainer, entrainment);
        }
    }
}