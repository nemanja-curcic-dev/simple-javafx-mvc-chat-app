package model;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.Optional;

/**
 * Created by nemanja on 7.2.17..
 */
public class Prompt {

    private String address;
    private int port;

    public void promptUser()
    {
        Dialog<Pair<String, String>> prompt = new Dialog<>();
        prompt.setTitle("Enter host address and port");

        ButtonType loginButtonType = new ButtonType("Enter", ButtonBar.ButtonData.OK_DONE);
        prompt.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        VBox vBox = new VBox();

        TextField address = new TextField();
        address.setPromptText("Host address");

        TextField port = new TextField();
        port.setPromptText("Port number");

        vBox.getChildren().addAll(new Label("Host address"), address, new Label("Port number"), port);

        prompt.getDialogPane().setContent(vBox);

        prompt.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(address.getText(), port.getText());
            }
            return null;
        });


        Optional<Pair<String, String>> result = prompt.showAndWait();

        result.ifPresent(hostPort->{
            this.address = hostPort.getKey();
            this.port = Integer.parseInt(hostPort.getValue());
        });
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
