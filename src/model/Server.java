package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by nemanja on 6.2.17..
 */
public class Server {

    private static LinkedList<PrintWriter> outputStreams = new LinkedList<>();
    private static LinkedList<Socket> sockets = new LinkedList<>();
    private static LinkedList<String> currentUsers = new LinkedList<>();
    private static ArrayList<String> chatAreaOutput = new ArrayList<>();

    public static void main(String[] args){

        try {
            ServerSocket serverSocket = new ServerSocket(9000);

            System.out.println("Server starded...");

            while (true)
            {
                sockets.add(serverSocket.accept());
                new ServerThread(sockets, outputStreams, currentUsers, chatAreaOutput).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
