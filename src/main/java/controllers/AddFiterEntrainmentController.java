package controllers;

import common.INavigation;
import enums.Role;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import models.module1.Utilisateur;
import models.module6.InstallationSportive;
import services.events.EventBus;
import services.jdbc.module1.ServiceUtilisateur;
import services.jdbc.module6.ServiceInstallationSportive;
import services.utilities.Session;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddFiterEntrainmentController extends BaseController implements INavigation {

    @FXML
    private ComboBox<Utilisateur> coaches;
    @FXML
    private ComboBox<InstallationSportive> installations;
    @FXML
    private DatePicker debut;
    @FXML
    private DatePicker fin;
    @FXML
    private HBox addbtn;

    public void initialize() {

        if(Session.getInstance().getUtilisateur().getRole() != Role.ADMIN && Session.getInstance().getUtilisateur().getRole() != Role.COACH) {
            addbtn.setVisible(false);
        }

        fillCoachesComboBox();
        fillInstallationsComboBox();

        coaches.valueProperty().addListener((obs, oldVal, newVal) -> filterEntrainments());
        installations.valueProperty().addListener((obs, oldVal, newVal) -> filterEntrainments());
        debut.valueProperty().addListener((obs, oldVal, newVal) -> filterEntrainments());
        fin.valueProperty().addListener((obs, oldVal, newVal) -> filterEntrainments());


    }


    public void onClick(MouseEvent event) {
        EventBus.publish("refresh-view", "/views/entrainment/add-entrainment.fxml");
    }



    private void fillInstallationsComboBox() {
        installations.getItems().add(null); // Add a null value to represent the empty option

        try {
            List<InstallationSportive> installationList = new ServiceInstallationSportive().getAll();
            for (InstallationSportive installation : installationList) {
                installations.getItems().add(installation); // Add the whole object or just the name
            }

            // Set a converter for the ComboBox
            installations.setCellFactory(param -> new ListCell<InstallationSportive>() {
                @Override
                protected void updateItem(InstallationSportive installation, boolean empty) {
                    super.updateItem(installation, empty);
                    setText(empty || installation == null ? "" : installation.getNom()); // Handle null (empty) option
                }
            });

            installations.setButtonCell(new ListCell<InstallationSportive>() {
                @Override
                protected void updateItem(InstallationSportive installation, boolean empty) {
                    super.updateItem(installation, empty);
                    setText(empty || installation == null ? "" : installation.getNom()); // Set the name for the button as well
                }
            });

            // Select the empty option by default
            installations.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void fillCoachesComboBox() {
        coaches.getItems().add(null); // Add a null value to represent the empty option

        try {
            List<Utilisateur> coachList = new ServiceUtilisateur()
                    .getAll()
                    .stream()
                    .filter((utilisateur) -> utilisateur.getRole() == Role.COACH || utilisateur.getRole() == Role.ADMIN)  // Filter by COACH enum
                    .collect(Collectors.toList());

            for (Utilisateur coach : coachList) {
                coaches.getItems().add(coach); // Add the whole object or just the name
            }

            // Set a converter for the ComboBox
            coaches.setCellFactory(param -> new ListCell<Utilisateur>() {
                @Override
                protected void updateItem(Utilisateur coach, boolean empty) {
                    super.updateItem(coach, empty);
                    setText(empty || coach == null ? "" : coach.getNom()); // Handle null (empty) option
                }
            });

            coaches.setButtonCell(new ListCell<Utilisateur>() {
                @Override
                protected void updateItem(Utilisateur coach, boolean empty) {
                    super.updateItem(coach, empty);
                    setText(empty || coach == null ? "" : coach.getNom()); // Set the name for the button as well
                }
            });

            // Select the empty option by default
            coaches.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void filterEntrainments() {
        // Create a Map to hold the filter criteria
        Map<String, Object> filters = new HashMap<>();
        filters.put("coach", coaches.getValue());
        filters.put("installation", installations.getValue());
        filters.put("startDate", debut.getValue());
        filters.put("endDate", fin.getValue());

        // Publish the Map via EventBus
        EventBus.publish("filter-entrainment", filters);
    }

    public void resetFilters(MouseEvent event) {
        EventBus.publish("refresh-view", "/views/entrainment/entrainment.fxml");
    }
}
