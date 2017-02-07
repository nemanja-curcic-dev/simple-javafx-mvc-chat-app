package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Client;
import model.Prompt;
import org.json.JSONObject;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by nemanja on 6.2.17..
 */
public class Controller implements Initializable {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextArea sendMessage;

    @FXML
    private TextArea whosOnline;

    @FXML
    private Pane pane;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Prompt prompt = new Prompt();
        prompt.promptUser();

        client = new Client(chatArea, sendMessage, whosOnline,prompt.getAddress(),prompt.getPort());
        client.setDaemon(true);
        client.start();

        Platform.runLater(()->{
            Stage stage = (Stage) pane.getScene().getWindow();

            stage.setOnCloseRequest(e->this.disconnect());
        });
    }

    public void sendMessage()
    {
        String message = sendMessage.getText();
        client.sendMessage(message);
    }

    public void disconnect()
    {
        JSONObject jsonDisconnect = new JSONObject();
        jsonDisconnect.put("disconnect", true);

        client.disconnect(jsonDisconnect);
    }

    public void changeName(ActionEvent event)
    {
        String name;

        TextInputDialog dialog = new TextInputDialog("some name");
        dialog.setTitle("Input your name");
        dialog.setHeaderText(null);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            name = result.get();
        }
        else{
            name = "default";
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("changeName", name);

        client.sendName(jsonObject);
    }
}
