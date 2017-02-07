package model;

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

    public ServerThread(ArrayList<Socket> sockets, ArrayList<PrintWriter> outputStreams, ArrayList<String> currentUsers) {
        this.sockets = sockets;
        this.outputStreams = outputStreams;
        this.currentUsers = currentUsers;
    }

    public void run()
    {

    }
}
