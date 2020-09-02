package com.korolkovrs.lesson2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int PORT = 8082;
    private AuthService authService;
    private Set<ClientHandler> clientHandlers;
    ExecutorService executorService = Executors.newCachedThreadPool();

    public Server() {
        this(PORT);
    }

    public Server(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            authService = new DataBaseAuthService();
            System.out.println("Auth is started up");

            clientHandlers = new HashSet<>();

            while (true) {
                System.out.println("Waiting for a connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized boolean isOccupied(AuthService.Record record) {
        for (ClientHandler ch : clientHandlers) {
            if (ch.getRecord().equals(record)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void subscribe(ClientHandler ch) {
        clientHandlers.add(ch);
    }

    public synchronized void unsubscribe(ClientHandler ch) {
        clientHandlers.remove(ch);
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler ch : clientHandlers) {
            ch.sendMessage(message);
        }
    }

    public synchronized void privateMessage(String message, ClientHandler sender) {
        String[] strings = message.split("\\s");
        String name = strings[1];
        message = message.replaceAll(strings[0], "");
        message = message.replaceAll(strings[1], "");


        for (ClientHandler ch : clientHandlers) {
            if (ch.getRecord().getName().equals(name)) {
                ch.sendMessage(String.format("Private message from %s: %s", sender.getRecord().getName(), message.trim()));
                return;
            }
        }

        sender.sendMessage(String.format("Sending error. Incorrect name [%s].", name));
    }

    public void addThread(Runnable thread) {
        executorService.execute(thread);
    }
}