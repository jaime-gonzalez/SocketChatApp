package com.socketChatApp.client.thread;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

import com.socketChatApp.client.gui.GuiWindow;

public class ReceiveServerMessage implements Runnable {

    private Socket socket;
    private GuiWindow window;

    public ReceiveServerMessage(Socket socket, GuiWindow window) {
        this.socket = socket;
        this.window = window;
    }

    @Override
    public void run() {
        while (true) {
            try {
                InputStream is = this.socket.getInputStream();
                DataInput dis = new DataInputStream(is);
                String msgReceived = dis.readUTF();

                window.addMessage(msgReceived);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
