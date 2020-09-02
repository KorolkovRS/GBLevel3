package com.korolkovrs.lesson2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ChatClient {
    private int connectionCount = 1;
    private String host = "localhost";
    private int port = Server.PORT;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ChatHistoryService chatHistory;

    private JFrame frame;
    private JTextField inputField;
    private JTextArea charTextArea;
    private JButton submitBtn;
    public AuthWindow authWindow;

    public ChatClient() {
        connection();

        frame = new JFrame();
        frame.setTitle("ChatClient v0.1");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 600, 500);

        JPanel chatPanel = new JPanel(new BorderLayout());
        charTextArea = new JTextArea();
        charTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(charTextArea);
        chatPanel.add(scrollPane);

        frame.add(chatPanel);

        JPanel controlPanel = new JPanel(new BorderLayout());

        inputField = new JTextField();
        inputField.setEditable(false);

        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new SendButtonListener());
        controlPanel.add(submitBtn, BorderLayout.EAST);

        controlPanel.add(inputField);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        sendMessageWithEnter(true);
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    void activate() {
        chatHistory = new ChatHistoryService(charTextArea);
        inputField.setEditable(true);
        readMessage();
    }

    public void connection() {
        if (connectionCount < 6) {
            try {
                socket = new Socket(host, port);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                authWindow = new AuthWindow(this);
            } catch (IOException e) {
                System.out.println("Connection error");
                connectionCount++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                connection();
            }
        } else {
            System.out.println("Connection timeout error");
            System.exit(1);
        }
    }

    public void readMessage() {

        Thread thread = new Thread(new Runnable() {
            private StringBuilder sb = new StringBuilder();

            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        String message = in.readUTF();
                        displayMessageOnGUI(message);

                    } catch (IOException e) {
                        unsubscribe();
                        JOptionPane.showMessageDialog(frame.getContentPane(), "Session closed. Goodbye!");
                        connection();
                        return;
                    }
                }
            }

            private void displayMessageOnGUI(String message) {
                sb.append(charTextArea.getText())
                        .append(message).append("\n");

                System.out.println("sb: " + sb.toString());
                charTextArea.setText(sb.toString());

                sb.setLength(0);
            }
        });
        thread.start();
    }

    private void unsubscribe() {
        chatHistory.saveHistory();
        charTextArea.setText("");
        charTextArea.setEditable(false);
        inputField.setEditable(false);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (inputField.getText().isBlank()) {
                return;
            }
            try {
                out.writeUTF(inputField.getText());
            } catch (IOException ioException) {
                System.out.println("Sending message error");

            }
            inputField.setText("");
        }
    }

    private void sendMessageWithEnter(boolean flag) {
        if (flag) {
            frame.getRootPane().setDefaultButton(submitBtn);
        } else {
            frame.getRootPane().setDefaultButton(null);
        }
    }
}
