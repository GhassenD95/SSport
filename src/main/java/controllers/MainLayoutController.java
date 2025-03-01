package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.AppMsg;
import services.events.EventBus;
import services.utilities.NavigationService;

import java.util.List;

public class MainLayoutController {

    @FXML
    private VBox mainContent;
    @FXML
    private HBox appMsg;
    @FXML
    private Label msg;
    @FXML
    private StackPane overlay;

    private NavigationService navigationService;




    public void initialize() {

        //set visibility of info card to none
        appMsg.setVisible(false);
        appMsg.setManaged(false); // Ensure it doesn't take up space when hidden
        overlay.setVisible(false);
        overlay.setManaged(false);
        //event to show card
        EventBus.subscribe("show-app-msg", (data)->{
            AppMsg appMsg = (AppMsg)data;
            List<String> messages = appMsg.getMessages();
            boolean isError = appMsg.isError();
            showAppMsg(isError, messages);
        });

        EventBus.subscribe("hide-app-msg", (_)->{
            appMsg.setVisible(false);
            appMsg.setManaged(false);
            overlay.setVisible(false);
            overlay.setManaged(false);

        });

        //set nav service
        //main content where everything wll be injected
        this.navigationService = new NavigationService(mainContent);
        EventBus.subscribe("refresh-view", (String fxml) -> {
            mainContent.getChildren().clear();
            navigationService.navigateTo(fxml);
        });

        EventBus.subscribe("refresh-view", (String fxml, Object data) -> {
            mainContent.getChildren().clear();
            navigationService.navigateTo(fxml, data);
        });


        navigationService.navigateTo("/views/entrainment/entrainment.fxml");
    }

    public void showAppMsg(boolean isError, List<String> messages) {
        String message = "";
        for (String m : messages) {
            message += m + "\n";

        }
        msg.setText(message);
        if (isError) {
            msg.setStyle("-fx-text-fill: #DF2E38;");
        }else {
            msg.setStyle("-fx-text-fill: #5D9C59;");
        }

        appMsg.setVisible(true);
        appMsg.setManaged(true);
        overlay.setVisible(true);
        overlay.setManaged(true);

    }


    public void onClickHideAppMsg(MouseEvent event) {
        EventBus.publish("hide-app-msg", event);
    }
}
