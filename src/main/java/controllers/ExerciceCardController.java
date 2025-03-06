package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.AppMsg;
import models.module2.Exercice;
import services.events.EventBus;
import services.jdbc.module2.ServiceExercice;
import services.utilities.ImageLoader;

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

    private Exercice exercice;
    @Override
    public void setData(Object data) {
        super.setData(data);
        if(data instanceof Exercice){
            this.exercice = (Exercice)data;
            name.setText(exercice.getNom());
            duree.setText(String.valueOf(exercice.getDureeMinutes()) + " min");
            type.setText(exercice.getTypeExercice().name());
            setsXreps.setText(exercice.getSets() + "X" + exercice.getReps());
            String imageUrl = exercice.getImage_url();

            image.setImage(ImageLoader.loadImageFromUrl( imageUrl));
        }
    }

    public void onClickDelete(MouseEvent event) {
        try {
            new ServiceExercice().delete((Exercice) this.data);
            List<String> success = new ArrayList<>();
            success.add("Exercice supprimé.");
            AppMsg appMsg = new AppMsg(false, success);
            EventBus.publish("show-app-msg", appMsg);

            EventBus.publish("refresh-view", "/views/exercices/list-exercices.fxml");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickUpdateExercise(MouseEvent event) {
        EventBus.publish("refresh-view", "/views/exercices/update-exercice.fxml", this.exercice);

    }
}
