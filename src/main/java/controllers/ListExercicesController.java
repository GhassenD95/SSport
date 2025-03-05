package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import services.utilities.NavigationService;

public class ListExercicesController extends BaseController implements INavigation {

    @FXML
    private VBox content;
    @FXML
    private VBox title;
    @FXML
    private VBox filter;

    public void initialize() {
        this.navigationService = new NavigationService(content);
        navigationService.loadComponent("/views/components/title-banner.fxml", title, "Exercices");
        navigationService.loadComponent("/views/components/filter-exercices.fxml", filter);
        navigationService.loadComponent("/views/components/exercices-grid.fxml", content);
    }

}
