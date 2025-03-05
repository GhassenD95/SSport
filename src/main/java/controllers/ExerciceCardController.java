package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.module2.Exercice;
import services.events.EventBus;
import services.jdbc.module2.ServiceExercice;
import services.utilities.ImageLoader;

import java.sql.SQLException;

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

    @Override
    public void setData(Object data) {
        super.setData(data);
        if(data instanceof Exercice){
            Exercice ex = (Exercice)data;
            name.setText(ex.getNom());
            duree.setText(String.valueOf(ex.getDureeMinutes()) + " min");
            type.setText(ex.getTypeExercice().name());
            setsXreps.setText(ex.getSets() + "X" + ex.getReps());
            String imageUrl = ex.getImage_url();

            image.setImage(ImageLoader.loadImage("exercices", imageUrl));
        }
    }

    public void onClickDelete(MouseEvent event) {
        try {
            new ServiceExercice().delete((Exercice) this.data);
            EventBus.publish("refresh-view", "/views/exercices/list-exercices.fxml");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
