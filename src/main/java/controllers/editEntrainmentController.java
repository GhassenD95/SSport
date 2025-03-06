package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.AppMsg;
import models.module1.Equipe;
import models.module2.Entrainment;
import models.module6.InstallationSportive;
import services.events.EventBus;
import services.jdbc.module1.ServiceEquipe;
import services.jdbc.module2.ServiceEntrainment;
import services.jdbc.module6.ServiceInstallationSportive;
import services.utilities.NavigationService;
import services.validators.EntrainmentValidator;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class editEntrainmentController extends BaseController implements INavigation {

    @FXML
    private DatePicker dateDepart;

    @FXML
    private DatePicker dateFin;

    @FXML
    private ComboBox<Equipe> equipe;

    @FXML
    private TextField hours_depart;

    @FXML
    private TextField hours_fin;

    @FXML
    private ComboBox<InstallationSportive> lieux;

    @FXML
    private TextField minutes_depart;

    @FXML
    private TextField minutes_fin;

    @FXML
    private TextField nom;

    @FXML
    private TextArea notes;

    @FXML
    private VBox title;

    private Entrainment entrainment;

    @Override
    public void setData(Object object) {
        super.setData(object);
        if (data instanceof Entrainment) {
            entrainment = (Entrainment) data;

            // Populate fields with the Entrainment data
            nom.setText(entrainment.getNom());
            notes.setText(entrainment.getDescription());

            // Set date and time
            if (entrainment.getDateDebut() != null) {
                dateDepart.setValue(entrainment.getDateDebut().toLocalDate());
                hours_depart.setText(String.format("%02d", entrainment.getDateDebut().getHour()));
                minutes_depart.setText(String.format("%02d", entrainment.getDateDebut().getMinute()));
            }

            if (entrainment.getDateFin() != null) {
                dateFin.setValue(entrainment.getDateFin().toLocalDate());
                hours_fin.setText(String.format("%02d", entrainment.getDateFin().getHour()));
                minutes_fin.setText(String.format("%02d", entrainment.getDateFin().getMinute()));
            }

            // Set the selected equipe and lieux
            equipe.getSelectionModel().select(entrainment.getEquipe());
            lieux.getSelectionModel().select(entrainment.getInstallationSportive());
        }
    }

    public void initialize() {
        // Fill comboboxes
        fillEquipeComboBox();
        fillLieuxComboBox();

        // Configure time fields
        configureTimeFields(hours_depart, "HH", 23);
        configureTimeFields(minutes_depart, "MM", 59);
        configureTimeFields(hours_fin, "HH", 23);
        configureTimeFields(minutes_fin, "MM", 59);

        // Load components (same as in AddEntrainmentController)
        this.navigationService = new NavigationService(title);
        navigationService.loadComponent("/views/components/title-banner.fxml", title, "Modifier entrainement");
    }

    private void configureTimeFields(TextField textField, String placeholder, int maxValue) {
        textField.setText(placeholder); // Set default placeholder

        Pattern digitPattern = Pattern.compile("\\d{0,2}"); // Allow max 2 digits

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (!digitPattern.matcher(newText).matches()) return null; // Restrict to max 2 digits

            if (!newText.isEmpty()) {
                try {
                    int value = Integer.parseInt(newText);
                    if (value > maxValue) return null; // Restrict max value
                } catch (NumberFormatException e) {
                    return null; // Reject invalid number
                }
            }
            return change;
        };

        textField.setTextFormatter(new TextFormatter<>(filter));

        // When clicked, clear placeholder
        textField.setOnMouseClicked(event -> {
            if (textField.getText().equals(placeholder)) {
                textField.clear();
            }
        });

        // If empty or invalid, reset to placeholder
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // When focus is lost
                String currentText = textField.getText().trim();
                if (currentText.isEmpty() || !currentText.matches("\\d{1,2}")) {
                    textField.setText(placeholder); // Reset to placeholder if empty or invalid
                } else {
                    try {
                        int value = Integer.parseInt(currentText);
                        if (value > maxValue) {
                            textField.setText(placeholder); // Reset to placeholder if value exceeds max
                        }
                    } catch (NumberFormatException e) {
                        textField.setText(placeholder); // Reset to placeholder if invalid number
                    }
                }
            }
        });
    }

    public void onClickCancel(MouseEvent event) {
        EventBus.publish("refresh-view", "/views/entrainment/entrainment.fxml");
    }

    public void onClickSaveEntrainment(MouseEvent event) {
        // Fetch selected team and installation
        Equipe selectedEquipe = equipe.getSelectionModel().getSelectedItem();
        InstallationSportive selectedLieux = lieux.getSelectionModel().getSelectedItem();

        // No need to create a new entrainment since this is for editing
        // Update the existing entrainment object
        entrainment.setNom(nom.getText());
        entrainment.setDescription(notes.getText());

        // Parse and set the start and end date + time
        LocalDateTime startDateTime = getDateTime(dateDepart, hours_depart, minutes_depart);
        LocalDateTime endDateTime = getDateTime(dateFin, hours_fin, minutes_fin);

        entrainment.setDateDebut(startDateTime);
        entrainment.setDateFin(endDateTime);
        entrainment.setEquipe(selectedEquipe);
        entrainment.setInstallationSportive(selectedLieux);

        // Validate the Entrainment object
        EntrainmentValidator validator = new EntrainmentValidator();
        List<String> errors = validator.validate(entrainment, selectedLieux);
        AppMsg appMsg;

        // Check if the object is valid
        if (validator.isValid()) {
            try {
                // Directly edit the existing entrainment in the database
                new ServiceEntrainment().edit(entrainment);
                appMsg = new AppMsg(false, List.of("Entrainment mise à jour."));
                EventBus.publish("show-app-msg", appMsg);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            appMsg = new AppMsg(true, errors);
            EventBus.publish("show-app-msg", appMsg);
        }
    }

    private void fillEquipeComboBox() {
        equipe.getItems().add(null); // Add a null value to represent the empty option

        try {
            List<Equipe> equipes = new ServiceEquipe().getAll();
            for (Equipe equipeObj : equipes) {
                equipe.getItems().add(equipeObj);
            }

            equipe.setCellFactory(param -> new ListCell<Equipe>() {
                @Override
                protected void updateItem(Equipe equipeObj, boolean empty) {
                    super.updateItem(equipeObj, empty);
                    setText(empty || equipeObj == null ? "" : equipeObj.getNom());
                }
            });

            equipe.setButtonCell(new ListCell<Equipe>() {
                @Override
                protected void updateItem(Equipe equipeObj, boolean empty) {
                    super.updateItem(equipeObj, empty);
                    setText(empty || equipeObj == null ? "" : equipeObj.getNom());
                }
            });

            equipe.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillLieuxComboBox() {
        lieux.getItems().add(null); // Add a null value to represent the empty option

        try {
            List<InstallationSportive> installations = new ServiceInstallationSportive().getAll();
            for (InstallationSportive installationObj : installations) {
                lieux.getItems().add(installationObj);
            }

            lieux.setCellFactory(param -> new ListCell<InstallationSportive>() {
                @Override
                protected void updateItem(InstallationSportive installationObj, boolean empty) {
                    super.updateItem(installationObj, empty);
                    setText(empty || installationObj == null ? "" : installationObj.getNom());
                }
            });

            lieux.setButtonCell(new ListCell<InstallationSportive>() {
                @Override
                protected void updateItem(InstallationSportive installationObj, boolean empty) {
                    super.updateItem(installationObj, empty);
                    setText(empty || installationObj == null ? "" : installationObj.getNom());
                }
            });

            lieux.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime getDateTime(DatePicker datePicker, TextField hoursField, TextField minutesField) {
        if (datePicker.getValue() == null
                || hoursField.getText().equals("HH")
                || minutesField.getText().equals("MM")
                || hoursField.getText().isEmpty()
                || minutesField.getText().isEmpty()) {
            return null;
        }

        try {
            LocalDateTime dateTime = datePicker.getValue().atStartOfDay();
            int hours = Integer.parseInt(hoursField.getText().trim());
            int minutes = Integer.parseInt(minutesField.getText().trim());
            return dateTime.withHour(hours).withMinute(minutes);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
