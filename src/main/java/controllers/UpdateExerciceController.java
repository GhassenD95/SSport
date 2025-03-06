package controllers;

import common.INavigation;
import enums.TypeExercice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import models.AppMsg;
import models.module2.Exercice;
import okhttp3.*;
import org.json.JSONObject;
import services.events.EventBus;
import services.jdbc.module2.ServiceExercice;
import services.utilities.ConfigLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateExerciceController extends BaseController implements INavigation {

    @FXML
    private Button addButton;
    @FXML
    private TextField nom;

    @FXML
    private ComboBox<TypeExercice> typeExercice;

    @FXML
    private TextField dureeMinutes;

    @FXML
    private TextField sets;

    @FXML
    private TextField reps;

    @FXML
    private Button uploadButton;

    @FXML
    private ImageView imagePreview;

    @FXML
    private ProgressIndicator uploadProgress;

    private Exercice exercice; // The Exercice object to edit
    private String imageUrl; // For storing the image URL (if changed)
    private File selectedImageFile; // For storing the selected image file

    private static final String IMGBB_API_URL = "https://api.imgbb.com/1/upload";
    private static final String IMGBB_API_KEY = ConfigLoader.get("api.key.imgbb");

    @Override
    public void setData(Object data) {
        super.setData(data);
        if (data instanceof Exercice) {
            this.exercice = (Exercice) data;
            // Populate the fields with the Exercice data
            nom.setText(exercice.getNom());
            typeExercice.getSelectionModel().select(exercice.getTypeExercice());
            dureeMinutes.setText(String.valueOf(exercice.getDureeMinutes()));
            sets.setText(String.valueOf(exercice.getSets()));
            reps.setText(String.valueOf(exercice.getReps()));

            // If the exercise has an image URL, display it
            if (exercice.getImage_url() != null) {
                imageUrl = exercice.getImage_url();
                imagePreview.setImage(new Image(imageUrl));
            }
        }
    }

    @FXML
    public void initialize() {
        // Populate the typeExercice ComboBox with enum values
        typeExercice.getItems().setAll(TypeExercice.values());

        // Set up the upload button action
        uploadButton.setOnAction(event -> handleUploadImage());

        // Set up the add button action to update the exercise
        addButton.setOnAction(event -> handleUpdateExercise());
    }

    @FXML
    private void handleUploadImage() {
        // Open a file chooser to select an image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());

        if (selectedImageFile != null) {
            uploadProgress.setVisible(true);
            new Thread(() -> {
                try {
                    imageUrl = uploadImageToImgBB(selectedImageFile);
                    javafx.application.Platform.runLater(() -> {
                        imagePreview.setImage(new Image(imageUrl));
                        uploadProgress.setVisible(false);
                    });
                } catch (IOException e) {
                    javafx.application.Platform.runLater(() -> {
                        List<String> errors = new ArrayList<>();
                        errors.add("Failed to upload image.");
                        AppMsg appMsg = new AppMsg(true, errors);
                        EventBus.publish("show-app-msg", appMsg);
                        uploadProgress.setVisible(false);
                    });
                }
            }).start();
        }
    }

    private String uploadImageToImgBB(File imageFile) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.getName(),
                        RequestBody.create(imageFile, MediaType.parse("image/*")))
                .addFormDataPart("key", IMGBB_API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(IMGBB_API_URL)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response.code() + ", " + response.body().string());
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getJSONObject("data").getString("url");
        }
    }

    @FXML
    private void handleUpdateExercise() {
        // Validate input fields
        if (nom.getText().isEmpty() || typeExercice.getValue() == null || dureeMinutes.getText().isEmpty() ||
                sets.getText().isEmpty() || reps.getText().isEmpty()) {
            List<String> errors = new ArrayList<>();
            errors.add("Please fill in all fields.");
            AppMsg appMsg = new AppMsg(true, errors);
            EventBus.publish("show-app-msg", appMsg);
            return;
        }

        try {
            // Parse input values
            String name = nom.getText();
            TypeExercice type = typeExercice.getValue();
            int duration = Integer.parseInt(dureeMinutes.getText());
            int setsCount = Integer.parseInt(sets.getText());
            int repsCount = Integer.parseInt(reps.getText());

            // Update the exercice object
            exercice.setNom(name);
            exercice.setTypeExercice(type);
            exercice.setDureeMinutes(duration);
            exercice.setSets(setsCount);
            exercice.setReps(repsCount);

            // If the image URL is updated, set it
            if (imageUrl != null) {
                exercice.setImage_url(imageUrl);
            }

            // Update the exercise in the database
            ServiceExercice serviceExercice = new ServiceExercice();
            serviceExercice.edit(exercice);

            // Show success message
            List<String> success = new ArrayList<>();
            success.add("Exercice updated.");
            AppMsg appMsg = new AppMsg(false, success);
            EventBus.publish("show-app-msg", appMsg);

            // Clear the form
            clearForm();
        } catch (NumberFormatException e) {
            List<String> errors = new ArrayList<>();
            errors.add("Invalid number format.");
            AppMsg appMsg = new AppMsg(true, errors);
            EventBus.publish("show-app-msg", appMsg);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add("Failed to update exercice.");
            AppMsg appMsg = new AppMsg(true, errors);
            EventBus.publish("show-app-msg", appMsg);
        }
    }

    private void clearForm() {
        nom.clear();
        typeExercice.getSelectionModel().clearSelection();
        dureeMinutes.clear();
        sets.clear();
        reps.clear();
        imagePreview.setImage(null);
        imageUrl = null;
        selectedImageFile = null;
    }

    public void onClickCancel(MouseEvent event) {
        EventBus.publish("refresh-view", "/views/exercices/list-exercices.fxml");
    }
}
