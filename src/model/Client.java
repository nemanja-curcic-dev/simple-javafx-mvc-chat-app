package model;

import javafx.scene.control.TextArea;

/**
 * Created by nemanja on 6.2.17..
 */
public class Client extends Thread{

    private TextArea chatArea;
    private TextArea sendMessage;
    private TextArea whosOnline;

    public Client(TextArea chatArea, TextArea sendMessage, TextArea whosOnline) {
        this.chatArea = chatArea;
        this.sendMessage = sendMessage;
        this.whosOnline = whosOnline;
    }


    public void run()
    {

    }
}
