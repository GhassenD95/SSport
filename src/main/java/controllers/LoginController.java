package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.module1.Utilisateur;
import services.jdbc.module1.ServiceUtilisateur;
import services.utilities.Session;
import services.websockets.NotificationServer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController extends BaseController implements INavigation {

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private Label error;

    private NotificationServer server; // Reference to the WebSocket server

    public void initialize() {
        // Initialization code (if needed)
    }

    public void login() {
        Utilisateur utilisateur = null;
        try {
            utilisateur = new ServiceUtilisateur().getUtilisateurByEmailPassword(email.getText(), password.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (utilisateur != null) {
            Session session = Session.getInstance(); // Ensure Session is a singleton
            session.setUtilisateur(utilisateur);

            Stage loginStage = (Stage) email.getScene().getWindow();
            loginStage.close();

            // Open the main layout stage
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main-layout.fxml"));
                Scene mainScene = new Scene(fxmlLoader.load(), 1000, 600);
                Stage mainStage = new Stage();
                mainStage.setResizable(false);  // Prevent resizing
                mainStage.setMaximized(false);  // Prevent maximizing
                mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sports.png")))); // Load icon
                mainStage.setTitle("Hello SSport!");
                mainStage.setScene(mainScene);
                mainStage.show();

                // Start the WebSocket server after the main layout is loaded
                startWebSocketServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            error.setText("Invalid credentials");
        }
    }

    /**
     * Starts the WebSocket server in a separate thread.
     */
    private void startWebSocketServer() {
        if (server == null) { // Ensure the server is started only once
            Thread serverThread = new Thread(() -> {
                server = new NotificationServer();
                server.start();
                System.out.println("WebSocket server started on port 8888.");
            });
            serverThread.setDaemon(true); // Set as daemon thread to stop when the application exits
            serverThread.start();
        }
    }
}