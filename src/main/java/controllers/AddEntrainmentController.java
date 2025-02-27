package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import services.utilities.NavigationService;

public class AddEntrainmentController extends BaseController implements INavigation {
    @FXML
    private VBox title;

    public void initialize() {
        this.navigationService = new NavigationService(title);
        navigationService.loadComponent("/views/components/title-banner.fxml", title, "Ajouter entrainment");
    }
}
