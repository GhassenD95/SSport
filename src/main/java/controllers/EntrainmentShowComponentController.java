package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.module2.Entrainment;
import models.module2.Exercice;
import services.events.EventBus;
import services.utilities.DateConverter;
import services.utilities.ImageLoader;
import services.utilities.NavigationService;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

public class EntrainmentShowComponentController extends BaseController implements INavigation {
    @FXML
    private Label debut;

    @FXML
    private Label description;

    @FXML
    private Label fin;

    @FXML
    private ImageView image;

    @FXML
    private Label nom;

    @FXML
    private Label nomInstallation;

    @FXML
    private VBox title;

    @FXML
    private VBox exercices;

    @FXML
    private Label coach;

    @FXML
    private ImageView imageCoach;

    @FXML
    private Label equipe;

    @FXML
    private HBox mapIcon; // Add this line

    private List<Exercice> exercicesList;

    private Entrainment entrainment;

    @Override
    public void setData(Object data) {
        super.setData(data);
        System.out.println(data);
        if (data instanceof Entrainment) {
            this.entrainment = (Entrainment) data;
            nom.setText(entrainment.getNom());
            description.setText(entrainment.getDescription());
            nomInstallation.setText(entrainment.getInstallationSportive().getNom());
            debut.setText(DateConverter.formatDate(entrainment.getDateDebut()));
            fin.setText(DateConverter.formatDate(entrainment.getDateFin()));
            String imageName = entrainment.getInstallationSportive().getImage_url();
            image.setImage(ImageLoader.loadImage("batiments", imageName));
            coach.setText(entrainment.getEquipe().getCoach().getNom());
            imageCoach.setImage(ImageLoader.loadImage("profile", entrainment.getEquipe().getCoach().getImage_url()));
            equipe.setText("equipe concernee: " + entrainment.getEquipe().getNom());
            this.exercicesList = entrainment.getExercices();
            navigationService.loadComponent("/views/components/assigned-exercices.fxml", exercices, this.exercicesList);
        }
    }

    public void initialize() {
        System.out.println(this.exercicesList);
        this.navigationService = new NavigationService(title);
        navigationService.loadComponent("/views/components/title-banner.fxml", title, "Detail Entrainment");
    }

    public void navigateToAssignExercice(MouseEvent mouseEvent) {
        if (entrainment == null) {
            System.err.println("Error: Entrainment is null. Cannot navigate to assign exercises.");
            return;
        }
        System.out.println("Navigating to assign exercises for Entrainment: " + entrainment.getId());
        EventBus.publish("refresh-view", "/views/exercices/assign-entrainment-exercice.fxml", this.entrainment);
    }

    @FXML
    private void openMap(MouseEvent event) {
        if (entrainment == null || entrainment.getInstallationSportive() == null) {
            System.err.println("Error: Entrainment or installation sportive is null. Cannot open map.");
            return;
        }

        // Get the address from the installation sportive
        String address = entrainment.getInstallationSportive().getAdresse();
        if (address == null || address.isEmpty()) {
            System.err.println("Error: Address is null or empty. Cannot open map.");
            return;
        }

        // Encode the address for use in a URL
        String encodedAddress = address.replace(" ", "+");

        // Create a new stage (window) to display the map
        Stage mapStage = new Stage();
        mapStage.setTitle("Map: " + address);
        mapStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/map.png")))); // Load icon
        mapStage.initModality(Modality.APPLICATION_MODAL);

        // Create a WebView to load the map URL
        WebView webView = new WebView();
        webView.getEngine().load("https://www.google.com/maps/search/?api=1&query=" + encodedAddress);

        // Set the WebView as the content of the stage
        mapStage.setScene(new javafx.scene.Scene(webView, 800, 600));
        mapStage.show();
    }
}
