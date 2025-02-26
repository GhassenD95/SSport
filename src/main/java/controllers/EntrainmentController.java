package controllers;

import common.INavigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import services.utilities.NavigationService;

public class EntrainmentController extends BaseController implements INavigation {

    @FXML
    private VBox container;
    @FXML
    private VBox table;


    public void initialize() {
        this.navigationService = new NavigationService(container);
        navigationService.loadComponent("/views/components/title-banner.fxml", container, "Entrainment");
        navigationService.loadComponent("/views/components/entrainments-table.fxml", table );
    }



}
