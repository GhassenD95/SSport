package controllers;

import common.INavigation;
import enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import models.module1.Utilisateur;
import services.events.EventBus;
import services.utilities.Session;

public class FilterExercicesController extends BaseController implements INavigation {

    @FXML
    private TextField filter;
    @FXML
    private HBox btnAdd;

    public void initialize() {
        setButtonVisibility();

        filter.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                EventBus.publish("FILTER_CHANGED", newValue);
            }
        });
    }

    public void onClickNavigateToAddExercice(MouseEvent event) {
        EventBus.publish("refresh-view", "/views/exercices/add-exercice.fxml");
    }

    private void setButtonVisibility() {
        Utilisateur currentUser = Session.getInstance().getUtilisateur();
        if (currentUser.getRole() != Role.ADMIN || currentUser.getRole() != Role.ADMIN) {
            btnAdd.setVisible(false);
        }
    }

}
