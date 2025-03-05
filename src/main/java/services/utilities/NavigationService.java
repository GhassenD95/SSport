package services.utilities;

import common.INavigation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NavigationService {

    // lblasa lfil layout win yenjecti el views
    //nesthakouha fil navigation
    private final Pane mainContent;

    //constructor
    public NavigationService(Pane mainContent) {
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

    public void loadComponent(String fxml, Pane container, Object data){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent component = loader.load();

            // Pass data to the controller if it implements INavigation
            Object controller = loader.getController();
            if (controller instanceof INavigation) {
                ((INavigation) controller).setNavigationService(this);
                ((INavigation) controller).setData(data);
            }

            // Inject the component into the specified container
            container.getChildren().add(component);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load component: " + fxml, e);
        }

    }

    public void loadComponent(String fxml, Pane container) {
        loadComponent(fxml, container, null);
    }
}
