package com.korolkovrs.lesson2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthWindow {
    public final int WIDTH = 500;
    public final int HEIGHT = 100;

    JFrame frame;

    private String login;
    private String password;

    ChatClient chatClient;

    public AuthWindow(ChatClient chatClient) {
        this.chatClient = chatClient;
        frame = new JFrame();
        frame.setTitle("Authentication");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        frame.setSize(500, 100);

        JPanel panel = new JPanel(new FlowLayout());
        JLabel jLogin = new JLabel("Login");
        JTextField ltf = new JTextField(10);
        JLabel jPassword = new JLabel("Password");
        JButton button = new JButton("Enter");
        JPasswordField passwordField = new JPasswordField(10);

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = ltf.getText();
                password = String.valueOf(passwordField.getPassword());

                if (!login.isBlank() && !password.isBlank()) {
                    Pattern pattern = Pattern.compile("[^\\w]");
                    Matcher matcher = pattern.matcher(login + password);
                    if (matcher.find()) {
                        JOptionPane.showMessageDialog(frame.getContentPane(), "The username and password must contain" +
                                "only letters and numbers");
                        System.out.println((login + password).substring(matcher.start(), matcher.end()));
                    } else {
                        try {
                            chatClient.getOut().writeUTF(login + " " + password);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                JOptionPane.showMessageDialog(frame.getContentPane(), "Empty login or password");
                ltf.setText("");
                passwordField.setText("");
            }
        });

        panel.add(jLogin);
        panel.add(ltf);
        panel.add(jPassword);
        panel.add(passwordField);
        panel.add(button);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        Thread messageReader = new Thread(() -> {
            String message = null;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    message = chatClient.getIn().readUTF();

                    if (message.startsWith("/authok")) {
                        String[] data = message.split(" ");
                        JOptionPane.showMessageDialog(frame.getContentPane(), "Hello, " + data[1] + "!");
                        chatClient.activate();
                        frame.removeAll();
                        frame.dispose();
                        Thread.currentThread().interrupt();
                    } else if (message.equals("/occupied")) {
                        JOptionPane.showMessageDialog(frame.getContentPane(), "User is occupied");
                    } else if (message.equals("/notfound")) {
                        JOptionPane.showMessageDialog(frame.getContentPane(), "Invalid username or password");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        messageReader.start();
    }
}
