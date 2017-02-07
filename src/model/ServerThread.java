package model;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by nemanja on 7.2.17..
 */
public class ServerThread extends Thread{

    private ArrayList<Socket> sockets;
    private ArrayList<PrintWriter> outputStreams;
    private ArrayList<String> currentUsers;
    private int clientId;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private boolean running = true;

    public ServerThread(ArrayList<Socket> sockets, ArrayList<PrintWriter> outputStreams, ArrayList<String> currentUsers) {
        this.sockets = sockets;
        this.outputStreams = outputStreams;
        this.currentUsers = currentUsers;
        this.clientId = sockets.size() - 1;
    }

    public void run()
    {
        System.out.println("Client " + clientId + " is connected.");

        try {
            printWriter = new PrintWriter(sockets.get(clientId).getOutputStream(),true);
            bufferedReader = new BufferedReader(new InputStreamReader(sockets.get(clientId).getInputStream()));

            outputStreams.add(printWriter);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("ClientId", clientId);

            printWriter.println(jsonObject);

            System.out.println("Client " + clientId + " disconnected...");

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                printWriter.close();
                bufferedReader.close();
                sockets.get(clientId).close();
                sockets.remove(clientId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void serveClients()
    {
        while (running)
        {
            try {
                String input = bufferedReader.readLine();

                JSONObject jsonObject = new JSONObject(input);

                if(jsonObject.has("disconnect"))
                {
                    running = false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(JSONObject o)
    {
        for(PrintWriter p : outputStreams)
        {
            p.println(o);
        }
    }
}
