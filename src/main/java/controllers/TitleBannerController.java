package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.module2.Entrainment;
import services.utilities.DateConverter;
import services.utilities.ImageLoader;

public class TitleBannerController extends BaseController implements INavigation {

    @FXML
    private Label title;
    @Override
    public void setData(Object data) {
        super.setData(data);
        title.setText(data.toString());
    }


}
