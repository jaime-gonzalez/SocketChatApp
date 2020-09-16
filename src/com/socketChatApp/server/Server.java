package com.socketChatApp.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.socketChatApp.server.thread.ReceiveClientMessage;

public class Server {

    private List<ReceiveClientMessage> clients = new ArrayList<>();

    public static void main(String[] args) {
        Server server = new Server();
        server.awaitForConnections();
    }

    public void awaitForConnections() {
        try (ServerSocket server = new ServerSocket(3333)) {

            while (true) {
                System.out.println("Awaiting for Connections...");
                Socket socket = server.accept();

                ReceiveClientMessage receiveClientMessage = new ReceiveClientMessage(socket, this);
                new Thread(receiveClientMessage).start();

                this.clients.add(receiveClientMessage);
                System.out.println("New client connected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessageClients(String message) {
        for (ReceiveClientMessage client : this.clients) {
            client.sendMessage(message);
        }
    }

}
