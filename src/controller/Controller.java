package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import model.Client;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Client client = new Client(chatArea, sendMessage, whosOnline);
        client.start();

    }
}
