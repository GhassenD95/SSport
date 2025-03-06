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
import services.websockets.NotificationClient;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddEntrainmentController extends BaseController implements INavigation {
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

    private NotificationClient notificationClient;

    public void initialize() {
        // Initialize WebSocket client
        initializeNotificationClient();

        //fill comboboxes
        //equipes
        fillEquipeComboBox();
        //fill combobox Installation
        fillLieuxComboBox();
        //load components
        this.navigationService = new NavigationService(title);
        navigationService.loadComponent("/views/components/title-banner.fxml", title, "Ajouter entrainment");
        //
        configureTimeFields(hours_depart, "HH", 23);
        configureTimeFields(minutes_depart, "MM", 59);
        configureTimeFields(hours_fin, "HH", 23);
        configureTimeFields(minutes_fin, "MM", 59);
    }

    private void initializeNotificationClient() {
        try {
            notificationClient = new NotificationClient();
            notificationClient.connect();
        } catch (URISyntaxException e) {
            System.err.println("Failed to initialize WebSocket client:");
            e.printStackTrace();
        }
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


    public void onClickAddEntrainment(MouseEvent event) {
        Equipe selectedEquipe = null;
        InstallationSportive selectedLieux = null;
        // Fetch selected team and installation
        selectedEquipe = equipe.getSelectionModel().getSelectedItem();
        selectedLieux = lieux.getSelectionModel().getSelectedItem();

        // Create the new Entrainment object
        Entrainment newEntrainment = new Entrainment();
        newEntrainment.setNom(nom.getText());
        newEntrainment.setDescription(notes.getText());

        // Parse and set the start and end date + time
        LocalDateTime startDateTime = getDateTime(dateDepart, hours_depart, minutes_depart);
        LocalDateTime endDateTime = getDateTime(dateFin, hours_fin, minutes_fin);

        newEntrainment.setDateDebut(startDateTime);
        newEntrainment.setDateFin(endDateTime);
        newEntrainment.setEquipe(selectedEquipe);
        newEntrainment.setInstallationSportive(selectedLieux);

        // Validate the Entrainment object
        EntrainmentValidator validator = new EntrainmentValidator();
        List<String> errors = validator.validate(newEntrainment, selectedLieux);
        AppMsg appMsg;
        // Check if the object is valid
        if (validator.isValid()) {
            try {
                ServiceEntrainment service = new ServiceEntrainment();
                service.add(newEntrainment);

                // Send notification about the new training
                sendNewEntrainmentNotification(newEntrainment);

                List<String> msg_success = new ArrayList<>();
                msg_success.add("Entrainment ajoutée.");
                appMsg = new AppMsg(false, msg_success);
                EventBus.publish("show-app-msg", appMsg);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            appMsg = new AppMsg(true, errors);
            EventBus.publish("show-app-msg", appMsg);
        }
    }

    // Method to send notification when a new entrainment is created
    private void sendNewEntrainmentNotification(Entrainment entrainment) {
        if (notificationClient != null && notificationClient.isOpen()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String formattedStart = entrainment.getDateDebut().format(formatter);

            String message = String.format(
                    "Nouvel entrainment créé: %s pour l'équipe %s, prévu le %s",
                    entrainment.getNom(),
                    entrainment.getEquipe() != null ? entrainment.getEquipe().getNom() : "Non spécifiée",
                    formattedStart
            );

            notificationClient.sendNotification(message);
        }
    }

    // Method to send notification when athletes are added to an entrainment
    public void notifyAthletesAddedToEntrainment(Entrainment entrainment, int athleteCount) {
        if (notificationClient != null && notificationClient.isOpen()) {
            String message = String.format(
                    "%d athlète(s) ajouté(s) à l'entrainment '%s' de l'équipe %s",
                    athleteCount,
                    entrainment.getNom(),
                    entrainment.getEquipe() != null ? entrainment.getEquipe().getNom() : "Non spécifiée"
            );

            notificationClient.sendNotification(message);
        }
    }

    private void fillEquipeComboBox() {
        equipe.getItems().add(null); // Add a null value to represent the empty option

        try {
            List<Equipe> equipes = new ServiceEquipe().getAll();
            for (Equipe equipeObj : equipes) {
                equipe.getItems().add(equipeObj); // Add the whole object or just the name
            }

            // Set a converter for the ComboBox
            equipe.setCellFactory(param -> new ListCell<Equipe>() {
                @Override
                protected void updateItem(Equipe equipeObj, boolean empty) {
                    super.updateItem(equipeObj, empty);
                    setText(empty || equipeObj == null ? "" : equipeObj.getNom()); // Handle null (empty) option
                }
            });

            equipe.setButtonCell(new ListCell<Equipe>() {
                @Override
                protected void updateItem(Equipe equipeObj, boolean empty) {
                    super.updateItem(equipeObj, empty);
                    setText(empty || equipeObj == null ? "" : equipeObj.getNom()); // Set the name for the button as well
                }
            });

            // Select the empty option by default
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
                lieux.getItems().add(installationObj); // Add the whole object or just the name
            }

            // Set a converter for the ComboBox
            lieux.setCellFactory(param -> new ListCell<InstallationSportive>() {
                @Override
                protected void updateItem(InstallationSportive installationObj, boolean empty) {
                    super.updateItem(installationObj, empty);
                    setText(empty || installationObj == null ? "" : installationObj.getNom()); // Handle null (empty) option
                }
            });

            lieux.setButtonCell(new ListCell<InstallationSportive>() {
                @Override
                protected void updateItem(InstallationSportive installationObj, boolean empty) {
                    super.updateItem(installationObj, empty);
                    setText(empty || installationObj == null ? "" : installationObj.getNom()); // Set the name for the button as well
                }
            });

            // Select the empty option by default
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
            return null; // Return null if any field is invalid or empty
        }

        try {
            // Get the date from the DatePicker
            LocalDateTime dateTime = datePicker.getValue().atStartOfDay();

            // Get the time from the TextFields (with validation to ensure valid input)
            int hours = Integer.parseInt(hoursField.getText().trim());
            int minutes = Integer.parseInt(minutesField.getText().trim());

            // Create and return the LocalDateTime with the provided time
            return dateTime.withHour(hours).withMinute(minutes);

        } catch (NumberFormatException e) {
            // Handle invalid number input (non-numeric or placeholder like "HH")
            System.err.println("Invalid input for hours or minutes: " + e.getMessage());
            return null; // Return null in case of error
        }
    }

    // Method to clean up resources
    public void cleanup() {
        if (notificationClient != null) {
            notificationClient.close();
        }
    }
}