package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import services.utilities.NavigationService;

public class MainLayoutController {

    @FXML
    private VBox mainContent; // Main container for the entrainment view

    private NavigationService navigationService;

    public void initialize() {
        this.navigationService = new NavigationService(mainContent);
        navigationService.navigateTo("/views/entrainment/entrainment.fxml");
    }
}