package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.AppMsg;
import models.module1.Utilisateur;
import services.events.EventBus;
import services.utilities.ImageLoader;
import services.utilities.NavigationService;
import services.utilities.Session;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainLayoutController {

    @FXML
    private VBox mainContent;
    @FXML
    private HBox appMsg;
    @FXML
    private Label msg;
    @FXML
    private StackPane overlay;

    @FXML
    private ImageView imageUser;

    @FXML
    private Label nomUser;

    @FXML
    private Label roleUser;

    private NavigationService navigationService;




    public void initialize() {
        Utilisateur utilisateur = Session.getInstance().getUtilisateur(); // Ensure Session is a singleton

        if (utilisateur != null) {
            nomUser.setText(utilisateur.getNom());
            roleUser.setText(utilisateur.getRole().toString());
            imageUser.setImage(ImageLoader.loadImage("profile", utilisateur.getImage_url()));
        } else {
            // Handle the case where utilisateur is null
            nomUser.setText("Guest");
            roleUser.setText("No Role");
            imageUser.setImage(ImageLoader.loadImage("profile", "default_image_url")); // Provide a default image URL
        }

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

    public void onClickNavigateToEntrainments(MouseEvent event) {
        EventBus.publish("refresh-view", "/views/entrainment/entrainment.fxml");
    }

    public void onClickNavigateToExercices(MouseEvent event) {
        EventBus.publish("refresh-view", "/views/exercices/list-exercices.fxml");
    }

    public void logout(MouseEvent event) {
        Stage mainStage = (Stage) mainContent.getScene().getWindow();
        mainStage.close();

        // Clear the session (if needed)
        Session.getInstance().setUtilisateur(null);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login.fxml")); // Path to your login.fxml
            Scene loginScene = new Scene(fxmlLoader.load(), 600, 400); // Adjust dimensions as needed
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(loginScene);
            loginStage.setResizable(false);  // Prevent resizing
            loginStage.setMaximized(false);  // Prevent maximizing
            loginStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sports.png")))); // Load icon

            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
