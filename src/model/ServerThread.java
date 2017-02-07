package model;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by nemanja on 7.2.17..
 */
public class ServerThread extends Thread{

    private LinkedList<Socket> sockets;
    private LinkedList<PrintWriter> outputStreams;
    private LinkedList<String> currentUsers;
    private int clientId;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private boolean running = true;
    private ArrayList<String> chatAreaOutput;

    public ServerThread(LinkedList<Socket> sockets, LinkedList<PrintWriter> outputStreams, LinkedList<String> currentUsers, ArrayList<String> chatAreaOutput) {
        this.sockets = sockets;
        this.outputStreams = outputStreams;
        this.currentUsers = currentUsers;
        this.clientId = sockets.size() - 1;
        this.chatAreaOutput = chatAreaOutput;
    }

    public void run()
    {
        try {
            printWriter = new PrintWriter(sockets.get(clientId).getOutputStream(),true);
            bufferedReader = new BufferedReader(new InputStreamReader(sockets.get(clientId).getInputStream()));

            outputStreams.add(printWriter); //save current output stream to outputStreams list
            currentUsers.add("Client " + clientId); //save current username to users list

            System.out.println("Client " + clientId + " is connected.");

            serveClients();

            System.out.println("Client " + clientId + " disconnected...");

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                printWriter.close();
                bufferedReader.close();
                sockets.get(clientId).close();
                //sockets.remove(clientId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void serveClients()
    {
        chatAreaOutput.add("Client " + clientId + " is now connected");
        String messageInit = "";

        for(String s : chatAreaOutput)
        {
            messageInit += s + "|";
        }

        broadcast(messageInit);

        while (running)
        {
            try {
                System.out.println("on start again..");
                String input = bufferedReader.readLine();

                if(input == null)
                {
                    running = false;
                }
                else {
                    JSONObject jsonObject = new JSONObject(input);

                    if(jsonObject.has("disconnect"))
                    {
                        printWriter.println("closeSocket");
                        running = false;
                    }
                    else if(jsonObject.has("changeName")) {
                        String newName = jsonObject.getString("changeName");

                        chatAreaOutput.add(currentUsers.get(clientId) + " changed his name to " + newName);
                        currentUsers.set(clientId, newName);

                        String messageChat = "";

                        for (String s : chatAreaOutput)
                        {
                            messageChat += s + "|";
                        }

                        broadcast(messageChat);
                    }
                    else if(jsonObject.has("sendMessage"))
                    {
                        String name = jsonObject.getString("name");
                        String message = jsonObject.getString("sendMessage");

                        String output = "";

                        chatAreaOutput.add(name + ":" + message + "|");

                        System.out.println(name+":" + message);

                        for(String s : chatAreaOutput)
                        {
                            output += s;
                        }

                        broadcast(output);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(String s)
    {
        for(PrintWriter p : outputStreams)
        {
            p.println(s);
        }
    }
}
