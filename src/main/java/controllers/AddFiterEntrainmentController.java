package controllers;

import common.INavigation;
import javafx.scene.input.MouseEvent;
import services.events.EventBus;

public class AddFiterEntrainmentController extends BaseController implements INavigation {

    public void onClick(MouseEvent event) {
        EventBus.publish("refresh-view", "/views/entrainment/add-entrainment.fxml");
    }

}
