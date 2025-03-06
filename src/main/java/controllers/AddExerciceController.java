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

public class AddExerciceController extends BaseController implements INavigation {
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

    private AppMsg appMsg;
    private File selectedImageFile;
    private String imageUrl;

    private static final String IMGBB_API_URL = "https://api.imgbb.com/1/upload";
    private static final String IMGBB_API_KEY = ConfigLoader.get("api.key.imgbb"); // Replace with your ImgBB API key

    @FXML
    public void initialize() {
        // Populate the typeExercice ComboBox with enum values
        typeExercice.getItems().setAll(TypeExercice.values());

        // Set up the upload button action
        uploadButton.setOnAction(event -> handleUploadImage());

        // Set up the add button action (assuming you have a button with fx:id="addButton")
        addButton.setOnAction(event -> handleAddExercise());
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
                        errors.add("failed to upload image.");
                        appMsg = new AppMsg(true, errors);
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
    private void handleAddExercise() {
        // Validate input fields
        if (nom.getText().isEmpty() || typeExercice.getValue() == null || dureeMinutes.getText().isEmpty() ||
                sets.getText().isEmpty() || reps.getText().isEmpty() || imageUrl == null) {
            List<String> errors = new ArrayList<>();
            errors.add("remplir tous les champs.");
            appMsg = new AppMsg(true, errors);
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

            // Create the exercise object
            Exercice exercice = new Exercice();
            exercice.setNom(name);
            exercice.setTypeExercice(type);
            exercice.setDureeMinutes(duration);
            exercice.setSets(setsCount);
            exercice.setReps(repsCount);
            exercice.setImage_url(imageUrl);

            // Add the exercise to the database
            ServiceExercice serviceExercice = new ServiceExercice();
            serviceExercice.add(exercice);

            // Show success message
            List<String> success = new ArrayList<>();
            success.add("Exercice ajoutée.");
            appMsg = new AppMsg(false, success);
            EventBus.publish("show-app-msg", appMsg);

            // Clear the form
            clearForm();
        } catch (NumberFormatException e) {
            List<String> errors = new ArrayList<>();
            errors.add("invalid number.");
            appMsg = new AppMsg(true, errors);
            EventBus.publish("show-app-msg", appMsg);
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add("ajout echouée.");
            appMsg = new AppMsg(true, errors);
            EventBus.publish("show-app-msg", appMsg);        }
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
