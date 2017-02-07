package model;

import javafx.scene.control.TextArea;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by nemanja on 6.2.17..
 */
public class Client extends Thread{

    private TextArea chatArea;
    private TextArea sendMessage;
    private TextArea whosOnline;
    private String address;
    private int port;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private Socket socket;

    public Client(TextArea chatArea, TextArea sendMessage, TextArea whosOnline, String address, int port) {
        this.chatArea = chatArea;
        this.sendMessage = sendMessage;
        this.whosOnline = whosOnline;
        this.address = address;
        this.port = port;
    }


    public void run()
    {
        try {
            socket = new Socket(address, port);

            printWriter = new PrintWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String odogovor = bufferedReader.readLine();

            System.out.println(odogovor);

            JSONObject jsonObject = new JSONObject(odogovor);

            System.out.println(jsonObject.get("ClientId"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                printWriter.close();
                bufferedReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(JSONObject o)
    {

    }
}
