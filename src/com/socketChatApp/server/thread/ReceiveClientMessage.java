package com.socketChatApp.server.thread;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.OutputStream;
import java.net.Socket;

import com.socketChatApp.server.Server;

public class ReceiveClientMessage implements Runnable{

    private Socket socket;
    private Server server;

    public ReceiveClientMessage(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Awaiting for client message...");

            try {
                DataInput dis = new DataInputStream(this.socket.getInputStream());
                String msgReceived = dis.readUTF();
                this.server.sendMessageClients(msgReceived);
            } catch (EOFException e) {
                System.out.println("Client Disconnected");
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            OutputStream os = this.socket.getOutputStream();
            DataOutput dos = new DataOutputStream(os);
            dos.writeUTF(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
