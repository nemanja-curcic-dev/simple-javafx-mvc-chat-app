package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by nemanja on 6.2.17..
 */
public class Server {

    private static ArrayList<PrintWriter> outputStreams = new ArrayList<>();
    private static ArrayList<Socket> sockets = new ArrayList<>();
    private static ArrayList<String> currentUsers = new ArrayList<>();

    public static void main(String[] args){

        try {
            ServerSocket serverSocket = new ServerSocket(9000);

            System.out.println("Server starded...");

            while (true)
            {
                sockets.add(serverSocket.accept());
                new ServerThread(sockets, outputStreams, currentUsers).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
