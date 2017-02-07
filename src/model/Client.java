package model;

import javafx.scene.control.TextArea;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

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
    private String chatAreaOutput = "";
    private boolean runnning = true;
    private String name;

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

            printWriter = new PrintWriter(socket.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String text = bufferedReader.readLine();

            StringTokenizer stringTokenizer = new StringTokenizer(text,"|",false);
            String output = "";

            while (stringTokenizer.hasMoreTokens())
            {
                output += stringTokenizer.nextToken() + "\n";
            }

            chatArea.setText(output);

            while(runnning)
            {
                String text1 = bufferedReader.readLine();

                if(text.equals("closeSocket"))
                {
                    closeStreams();
                    runnning = false;
                }
                else{

                    if(text1 != null)
                    {
                        StringTokenizer stringTokenizer1 = new StringTokenizer(text1,"|",false);
                        String output1 = "";

                        while (stringTokenizer1.hasMoreTokens())
                        {
                            output1 = stringTokenizer1.nextToken() + "\n";
                        }

                        chatArea.appendText(output1);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*finally {
            try {
                printWriter.close();
                bufferedReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void sendMessage(String s)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("sendMessage", s);

        System.out.println(jsonObject);
        printWriter.println(jsonObject);
    }

    public void sendName(JSONObject o)
    {
        name = o.getString("changeName");
        printWriter.println(o);
    }

    public void disconnect(JSONObject o)
    {
        printWriter.println(o);
    }

    public void closeStreams()
    {
        printWriter.close();
        try {
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
