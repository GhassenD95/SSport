package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.module2.Entrainment;
import services.jdbc.module2.ServiceEntrainment;
import services.utilities.DateConverter;
import services.utilities.ImageLoader;

import java.sql.SQLException;

public class EntrainmentCardController extends BaseController implements INavigation {
    @FXML
    private Label debut;

    @FXML
    private Label description;

    @FXML
    private Label equipe;

    @FXML
    private Label fin;

    @FXML
    private ImageView imageCoach;

    @FXML
    private Label nom;




    @Override
    public void setData(Object data) {
        // Call the super method to set the data field
        super.setData(data);

        // Check if the data is an instance of Entrainment
        if (data instanceof Entrainment) {
            // Cast data to Entrainment
            Entrainment entrainment = (Entrainment) data;

            // Set the UI elements with data from the entrainment object
            nom.setText(entrainment.getNom());
            description.setText(entrainment.getDescription());
            equipe.setText(entrainment.getEquipe().getNom());
            debut.setText(DateConverter.formatDate(entrainment.getDateDebut()));
            fin.setText(DateConverter.formatDate(entrainment.getDateFin()));

            // Load the coach's image
            String imageName = entrainment.getEquipe().getCoach().getImage_url();
            imageCoach.setImage(ImageLoader.loadImage("profile", imageName));
        } else {
            System.out.println("Data is not an instance of Entrainment");
        }
    }

    public void onClickDelete(MouseEvent actionEvent) {

    }

}
