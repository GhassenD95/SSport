package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import services.events.EventBus;
import services.utilities.NavigationService;

public class MainLayoutController {

    @FXML
    private VBox mainContent;

    private NavigationService navigationService;




    public void initialize() {
        this.navigationService = new NavigationService(mainContent);
        EventBus.subscribe("refresh-view", (String fxml) -> {
            mainContent.getChildren().clear();
            navigationService.navigateTo(fxml);
        });
        navigationService.navigateTo("/views/entrainment/entrainment.fxml");
    }

}
