package services;

import common.INavigation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class NavigationService {

    // lblasa lfil layout win yenjecti el views
    //nesthakouha fil navigation
    private final StackPane mainContent;

    //constructor
    public NavigationService(StackPane mainContent) {
        this.mainContent = mainContent;
    }

    //params
    //string view path
    /*data passed to the next controller/view*/
    public void navigateTo(String fxml, Object data ) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        try {
            Parent root = fxmlLoader.load();
            Object controller = fxmlLoader.getController();
            if (controller instanceof INavigation) {
                ((INavigation) controller).setNavigationService(this);
                ((INavigation) controller).setData(data);
            }

            mainContent.getChildren().setAll(root);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void navigateTo(String fxml){
        navigateTo(fxml,null);
    }
}
