package controllers;

import common.INavigation;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import okhttp3.*;
import org.json.JSONObject;
import services.utilities.ConfigLoader;

import java.io.IOException;
public class ChatbotController {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    // Cohere API URL and API Key
    private static final String API_URL = "https://api.cohere.ai/v1/generate";
    private static final String API_KEY = ConfigLoader.get("api.key.chatbot"); // Replace with your Cohere API key

    @FXML
    private void sendMessage(MouseEvent event) {
        String userInput = input.getText().trim();
        if (userInput.isEmpty()) {
            output.appendText("Please enter a message.\n");
            return;
        }

        // Disable the button and clear the input field
        input.setDisable(true);
        output.appendText("You: " + userInput + "\n");

        // Run the API call in a background thread
        new Thread(() -> {
            try {
                String response = getChatbotResponse(userInput);
                // Update the UI on the JavaFX application thread
                javafx.application.Platform.runLater(() -> {
                    output.appendText("Bot: " + response + "\n");
                    input.clear();
                    input.setDisable(false);
                });
            } catch (IOException e) {
                javafx.application.Platform.runLater(() -> {
                    output.appendText("Error: Unable to fetch response. Please check your API key and internet connection.\n");
                    input.setDisable(false);
                });
            }
        }).start();
    }

    private String getChatbotResponse(String message) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Prepare the JSON request body
        String jsonRequest = "{"
                + "\"prompt\":\"" + message + "\","
                + "\"max_tokens\":200," // Adjust the number of tokens as needed
                + "\"temperature\":0.7" // Adjust the creativity level (0.0 to 1.0)
                + "}";

        RequestBody body = RequestBody.create(jsonRequest, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cohere-Version", "2022-12-06") // Use the latest API version
                .post(body)
                .build();

        // Execute the request and parse the response
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code: " + response.code() + ", " + response.body().string());
            }

            // Parse the JSON response
            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getJSONArray("generations")
                    .getJSONObject(0)
                    .getString("text"); // Extract the generated text
        }
    }
}
