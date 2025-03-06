package controllers;

import common.INavigation;
import enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import models.AppMsg;
import models.module1.Utilisateur;
import models.module2.Exercice;
import services.events.EventBus;
import services.jdbc.module2.ServiceExercice;
import services.utilities.ImageLoader;
import services.utilities.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExerciceCardController extends BaseController implements INavigation {

    @FXML
    private Label duree;

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label setsXreps;

    @FXML
    private Label type;

    @FXML
    private HBox btns; // Buttons container

    private Exercice exercice;

    @Override
    public void setData(Object data) {
        super.setData(data);
        if (data instanceof Exercice) {
            this.exercice = (Exercice) data;
            name.setText(exercice.getNom());
            duree.setText(String.valueOf(exercice.getDureeMinutes()) + " min");
            type.setText(exercice.getTypeExercice().name());
            setsXreps.setText(exercice.getSets() + "X" + exercice.getReps());
            String imageUrl = exercice.getImage_url();
            image.setImage(ImageLoader.loadImageFromUrl(imageUrl));

            // Set button visibility based on user role and coach
            setButtonVisibility();
        }
    }

    private void setButtonVisibility() {
        Utilisateur currentUser = Session.getInstance().getUtilisateur();
        if(currentUser.getRole() != Role.ADMIN || currentUser.getRole() != Role.COACH) {
            btns.setVisible(false);
        }
    }

    public void onClickDelete(MouseEvent event) {
        if (exercice == null) {
            System.out.println("Exercice is null");
            return;
        }

        try {
            new ServiceExercice().delete(exercice);
            List<String> success = new ArrayList<>();
            success.add("Exercice supprimé.");
            AppMsg appMsg = new AppMsg(false, success);
            EventBus.publish("show-app-msg", appMsg);

            EventBus.publish("refresh-view", "/views/exercices/list-exercices.fxml");
        } catch (SQLException e) {
            List<String> error = new ArrayList<>();
            error.add("Erreur lors de la suppression de l'exercice.");
            AppMsg appMsg = new AppMsg(true, error);
            EventBus.publish("show-app-msg", appMsg);
            e.printStackTrace();
        }
    }

    public void onClickUpdateExercise(MouseEvent event) {
        if (exercice == null) {
            System.out.println("Exercice is null");
            return;
        }
        EventBus.publish("refresh-view", "/views/exercices/update-exercice.fxml", exercice);
    }
}
