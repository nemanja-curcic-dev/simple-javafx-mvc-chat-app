package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.util.Pair;
import model.Client;
import model.Prompt;
import org.json.JSONObject;

import java.net.URL;
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

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Prompt prompt = new Prompt();
        prompt.promptUser();

        client = new Client(chatArea, sendMessage, whosOnline,prompt.getAddress(),prompt.getPort());
        client.setDaemon(true);
        client.start();
    }

    public void disconnect(ActionEvent event)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("disconnect", true);

        client.sendMessage(jsonObject);
    }
}
