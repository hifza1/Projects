package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatApp extends Application {

    private TextArea chatArea;
    private TextField inputField;
    private PrintWriter out;

    @Override
    public void start(Stage stage) {

        chatArea = new TextArea();
        chatArea.setEditable(false);

        inputField = new TextField();
        inputField.setPromptText("Type message...");

        Button sendButton = new Button("Send");

        sendButton.setOnAction(e -> sendMessage());
        inputField.setOnAction(e -> sendMessage());

        HBox bottom = new HBox(10, inputField, sendButton);

        BorderPane root = new BorderPane();
        root.setCenter(chatArea);
        root.setBottom(bottom);

        Scene scene = new Scene(root, 400, 400);

        stage.setTitle("Chat Client");
        stage.setScene(scene);
        stage.show();

        connectToServer();
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 59001);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                out = new PrintWriter(socket.getOutputStream(), true);

                String message;

                while ((message = in.readLine()) != null) {

                    String finalMessage = message;

                    Platform.runLater(() ->
                        chatArea.appendText(finalMessage + "\n")
                    );
                }

            } catch (Exception e) {
                Platform.runLater(() ->
                        chatArea.appendText("Connection failed\n")
                );
            }
        }).start();
    }

    private void sendMessage() {

        String message = inputField.getText();

        if (!message.isEmpty()) {
            out.println(message);
            inputField.clear();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}