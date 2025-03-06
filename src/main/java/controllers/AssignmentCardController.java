package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.module2.Entrainment;
import models.module2.Exercice;
import models.module2.ExerciceEntrainment;
import services.events.EventBus;
import services.jdbc.module2.ServiceExerciceEntrainment;
import services.utilities.ImageLoader;

import java.sql.SQLException;

public class AssignmentCardController extends BaseController implements INavigation {
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

    private Entrainment entrainment;
    private ExerciceEntrainment ex;

    // Initialize ex in the constructor
    public AssignmentCardController() {
        this.ex = new ExerciceEntrainment();
    }

    public void setEntrainment(Entrainment entrainment) {
        this.entrainment = entrainment;
        ex.setEntrainment(entrainment); // Set the Entrainment in ExerciceEntrainment
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        if (data instanceof Exercice) {
            Exercice ex = (Exercice) data;
            name.setText(ex.getNom());
            duree.setText(String.valueOf(ex.getDureeMinutes()) + " min");
            type.setText(ex.getTypeExercice().name());
            setsXreps.setText(ex.getSets() + "X" + ex.getReps());
            String imageUrl = ex.getImage_url();
            image.setImage(ImageLoader.loadImageFromUrl(imageUrl));
            this.ex.setExercice(ex); // Set the Exercice in ExerciceEntrainment
        }
    }

    public void onClickAssignExercice(MouseEvent event) {
        if (ex.getEntrainment() == null) {
            System.err.println("Error: Entrainment is not set in ExerciceEntrainment.");
            return;
        }

        System.out.println("Assigning Exercice: " + ex.getExercice().getNom() +
                " to Entrainment: " + ex.getEntrainment().getId());

        try {
            new ServiceExerciceEntrainment().add(ex);
            System.out.println("Exercice added");

            // Add the exercise to the entrainment object
            ex.getEntrainment().getExercices().add(ex.getExercice());

            // Notify that exercises have been updated
            System.out.println("Publishing EXERCISES_UPDATED event");
            EventBus.publish("EXERCISES_UPDATED", "Exercise assigned");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
