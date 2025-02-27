package controllers;

import com.almasb.fxgl.entity.action.Action;
import common.INavigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.w3c.dom.events.MouseEvent;

public class HelloController extends BaseController implements INavigation {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onClickNavigateTo(ActionEvent event) {
        navigationService.navigateTo("/main-layout.fxml");
    }

}