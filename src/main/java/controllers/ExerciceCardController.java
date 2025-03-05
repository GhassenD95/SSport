package controllers;

import common.INavigation;
import enums.TypeExercice;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.module2.Exercice;
import services.utilities.ImageLoader;

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
}
