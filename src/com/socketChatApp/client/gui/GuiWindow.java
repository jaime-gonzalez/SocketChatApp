package com.socketChatApp.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public abstract class GuiWindow {

    private String applicationName = "An awesome Chat Application";
    private JFrame chatFrame = new JFrame(applicationName);
    private JButton sendMessageButton;
    private JTextField messageTextField;
    private JTextArea chatArea;
    private JTextField nameTextField;
    private JFrame userNameFrame;
    private String username;
    public GuiWindow() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                requestUsername();
            }
        });
    }

    protected void requestUsername() {
        chatFrame.setVisible(false);
        userNameFrame = new JFrame(applicationName);
        userNameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nameTextField = new JTextField(9);
        JLabel chooseNameLabel = new JLabel("Inform your name:");
        JButton accessServerButton = new JButton("Join the chat");
        accessServerButton.addActionListener(new AccessServerButtonListener());
        JPanel usernamePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 20);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        usernamePanel.add(chooseNameLabel, preLeft);
        usernamePanel.add(nameTextField, preRight);
        userNameFrame.add(BorderLayout.CENTER, usernamePanel);
        userNameFrame.add(BorderLayout.SOUTH, accessServerButton);
        userNameFrame.setSize(375, 200);
        userNameFrame.setVisible(true);
    }

    class AccessServerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            username = nameTextField.getText();
            if (username.length() < 1) {
                JOptionPane.showMessageDialog(null, "Inform an username.", "Attention"
                        , JOptionPane.WARNING_MESSAGE);
            } else {
                if (connect()) {
                    userNameFrame.setVisible(false);
                    showChatWindow();
                } else {
                    JOptionPane.showMessageDialog(null, "Error conecting to server.", "Error"
                            , JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void showChatWindow() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.GRAY);
        southPanel.setLayout(new GridBagLayout());

        messageTextField = new JTextField(30);
        messageTextField.addActionListener(new SendMessageListener());

        sendMessageButton = new JButton("Send");
        sendMessageButton.addActionListener(new SendMessageListener());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Serif", Font.PLAIN, 15));
        chatArea.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageTextField, left);
        southPanel.add(sendMessageButton, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        chatFrame.add(mainPanel);
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setSize(470, 300);
        chatFrame.setVisible(true);
        messageTextField.requestFocusInWindow();
    }

    class SendMessageListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageTextField.getText().length() < 1) {
                // não faz nada
            } else {
                String msg = "<" + username + ">:  " + messageTextField.getText() + "\n";
                sendMessage(msg);

                messageTextField.setText("");
            }

            messageTextField.requestFocusInWindow();
        }
    }

    protected String getUsername() {
        return this.username;
    }

    public void addMessage(String message) {
        if (chatArea != null) {
            chatArea.append(message);
        }
    }

    protected abstract boolean connect();

    protected abstract void sendMessage(String message);

}