package controllers;

import common.INavigation;
import enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import models.AppMsg;
import models.module1.Utilisateur;
import models.module2.Entrainment;
import services.events.EventBus;
import services.jdbc.module2.ServiceEntrainment;
import services.utilities.DateConverter;
import services.utilities.ImageLoader;
import services.utilities.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @FXML
    private StackPane deleteBtn;

    @FXML
    private StackPane updateBtn;

    private Entrainment entrainment;

    @Override
    public void setData(Object data) {
        // Call the super method to set the data field
        super.setData(data);

        // Check if the data is an instance of Entrainment
        if (data instanceof Entrainment) {
            // Cast data to Entrainment
            this.entrainment = (Entrainment) data;

            // Set the UI elements with data from the entrainment object
            nom.setText(entrainment.getNom());
            description.setText(entrainment.getDescription());
            equipe.setText(entrainment.getEquipe().getNom());
            debut.setText(DateConverter.formatDate(entrainment.getDateDebut()));
            fin.setText(DateConverter.formatDate(entrainment.getDateFin()));

            // Load the coach's image
            String imageName = entrainment.getEquipe().getCoach().getImage_url();
            imageCoach.setImage(ImageLoader.loadImage("profile", imageName));

            // Set visibility of delete and update buttons based on user role and coach
            setButtonVisibility();
        } else {
            System.out.println("Data is not an instance of Entrainment");
        }
    }

    private void setButtonVisibility() {
        Utilisateur currentUser = Session.getInstance().getUtilisateur();
        if (currentUser == null || entrainment == null || entrainment.getEquipe() == null || entrainment.getEquipe().getCoach() == null) {
            deleteBtn.setVisible(false);
            updateBtn.setVisible(false);
            return;
        }

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isCoach = currentUser.equals(entrainment.getEquipe().getCoach());

        // Hide buttons if the user is not an admin AND not the coach
        deleteBtn.setVisible(isAdmin || isCoach);
        updateBtn.setVisible(isAdmin || isCoach);
    }

    public void onClickDelete(MouseEvent actionEvent) {
        if (entrainment == null) {
            System.out.println("Entrainment is null");
            return;
        }

        try {
            // Delete the entrainment
            new ServiceEntrainment().delete(entrainment);
            List<String> msg_success = new ArrayList<>();
            msg_success.add("Entrainment supprimé");
            AppMsg appMsg = new AppMsg(false, msg_success);
            EventBus.publish("show-app-msg", appMsg);

            // Publish the "refresh-table" event to notify the table to refresh
            EventBus.publish("refresh-table", null);
        } catch (SQLException e) {
            List<String> msg_error = new ArrayList<>();
            msg_error.add("Erreur lors de la suppression de l'entrainment");
            AppMsg appMsg = new AppMsg(true, msg_error);
            EventBus.publish("show-app-msg", appMsg);
            e.printStackTrace();
        }
    }

    public void onClickEdit(MouseEvent actionEvent) {
        if (entrainment == null) {
            System.out.println("Entrainment is null");
            return;
        }
        EventBus.publish("refresh-view", "/views/entrainment/edit-entrainment.fxml", entrainment);
    }

    public void onClickShowEntrainment(MouseEvent actionEvent) {
        if (entrainment == null) {
            System.out.println("Entrainment is null");
            return;
        }
        EventBus.publish("refresh-view", "/views/entrainment/entrainment-show-component.fxml", entrainment);
    }
}
