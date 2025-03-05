package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.module2.Entrainment;
import models.module2.Exercice;
import services.utilities.DateConverter;
import services.utilities.ImageLoader;
import services.utilities.NavigationService;

import java.util.List;

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

    private List<Exercice> exercicesList;

    @Override
    public void setData(Object data) {
        super.setData(data);
        System.out.println(data);
        if (data instanceof Entrainment) {
            Entrainment entrainment = (Entrainment) data;
            nom.setText(entrainment.getNom());
            description.setText(entrainment.getDescription());
            nomInstallation.setText(entrainment.getInstallationSportive().getNom());
            debut.setText(DateConverter.formatDate(entrainment.getDateDebut()));
            fin.setText(DateConverter.formatDate(entrainment.getDateFin()));
            String imageName = entrainment.getInstallationSportive().getImage_url();
            image.setImage(ImageLoader.loadImage("batiments", imageName));
            this.exercicesList = entrainment.getExercices();
            navigationService.loadComponent("/views/components/assigned-exercices.fxml", exercices, this.exercicesList);

        }
    }


    public void initialize() {
        System.out.println(this.exercicesList);
        this.navigationService = new NavigationService(title);
        navigationService.loadComponent("/views/components/title-banner.fxml", title, "Detail Entrainment");


    }

}
