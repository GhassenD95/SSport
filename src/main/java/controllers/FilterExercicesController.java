package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import services.events.EventBus;

public class FilterExercicesController extends BaseController implements INavigation {

    @FXML
    private TextField filter;

    public void initialize() {
        filter.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                EventBus.publish("FILTER_CHANGED", newValue);
            }
        });
    }
}
