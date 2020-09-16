package com.socketChatApp.client;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.socketChatApp.client.gui.GuiWindow;
import com.socketChatApp.client.thread.ReceiveServerMessage;

public class Client extends GuiWindow {

    private Socket socket;

    public static void main(String[] args) {
        new Client();
    }

    @Override
    protected boolean connect() {
        System.out.println("Connecting to the server...");
        try {
            this.socket = new Socket("127.0.0.1", 3333);

            ReceiveServerMessage receiveServerMessage = new ReceiveServerMessage(this.socket, this);
            new Thread(receiveServerMessage).start();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void sendMessage(String message) {
        System.out.println("Send a socket message to the server - " + message);

        try {
            OutputStream os = this.socket.getOutputStream();
            DataOutput dos = new DataOutputStream(os);
            dos.writeUTF(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
