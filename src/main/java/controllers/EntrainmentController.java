package controllers;

import common.INavigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import services.events.EventBus;
import services.utilities.NavigationService;

public class EntrainmentController extends BaseController implements INavigation {

    @FXML
    private VBox container;
    @FXML
    private VBox table;
    @FXML
    private VBox filters;


    public void initialize() {
        this.navigationService = new NavigationService(container);
        navigationService.loadComponent("/views/components/title-banner.fxml", container, "Entrainment");
        navigationService.loadComponent("/views/components/add-fiter-entrainment.fxml", filters);
        navigationService.loadComponent("/views/components/entrainments-table.fxml", table );
        // Subscribe to the "refresh-table" event
        EventBus.subscribe("refresh-table", (Object message) -> {
            // When the event is triggered, refresh the table
            refreshTable();
        });

    }

    private void refreshTable() {
        // This method will reload the table components, effectively refreshing it
        try {
            // Clear the existing content in the table (if necessary)
            table.getChildren().clear();

            // Reload the entrainments data into the table
            navigationService.loadComponent("/views/components/entrainments-table.fxml", table);

            // Optionally, you could also reload data from the database if needed
        } catch (Exception e) {
            System.err.println("Error refreshing table: " + e.getMessage());
        }
    }




}
