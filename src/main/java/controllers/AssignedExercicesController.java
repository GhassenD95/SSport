package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.module2.Exercice;
import services.utilities.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class AssignedExercicesController extends BaseController implements INavigation {

    @FXML
    private HBox exercicesImgContainer;

    @Override
    public void setData(Object data) {
        super.setData(data);
        if(data instanceof List){
            List<Exercice> exercices = (List<Exercice>) data;
            System.out.println(exercices);
            exercicesImgContainer.getChildren().clear();
            for(Exercice exercice : exercices){
                ImageView exerciceImg = new ImageView();
                exerciceImg.setFitHeight(50);
                exerciceImg.setFitWidth(50);
                exerciceImg.setImage(ImageLoader.loadImage("exercices", exercice.getImage_url()));
                exercicesImgContainer.getChildren().add(exerciceImg);
            }
        }


    }
}
