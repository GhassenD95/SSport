package services.websockets;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.net.URI;
import java.net.URISyntaxException;

public class NotificationClient extends WebSocketClient {

    public NotificationClient() throws URISyntaxException {
        super(new URI("ws://localhost:8888"));
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket server");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received notification: " + message);
        // Display the notification in the JavaFX application
        Platform.runLater(() -> showNotification(message));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from WebSocket server: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error:");
        ex.printStackTrace();
    }

    private void showNotification(String message) {
        // Display the notification in the JavaFX UI
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to establish connection
    public void connect() {
        super.connect();
    }

    // Method to send a message to the server
    public void sendNotification(String message) {
        if (isOpen()) {
            send(message);
        } else {
            System.err.println("Cannot send message - connection is not open");
        }
    }
}