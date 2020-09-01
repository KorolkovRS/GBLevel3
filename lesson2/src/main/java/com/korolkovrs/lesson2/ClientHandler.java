package com.korolkovrs.lesson2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class ClientHandler {
    private AuthService.Record record;
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        doAuth();
                        readMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        closeConnection();
                    }
                }
            })
                    .start();

        } catch (IOException e) {
            throw new RuntimeException("Client handler was not created");
        }
    }

    public AuthService.Record getRecord() {
        return record;
    }

    public void doAuth() throws IOException {
        System.out.println("Waiting for auth...");

        while (true) {
            String message = in.readUTF();
            String[] loginAndPassword = message.split(" ");
            record = this.server.getAuthService().findRecord(loginAndPassword[0], loginAndPassword[1]);

            if (record != null) {
                if (!server.isOccupied(record)) {
                    sendMessage("/authok " + record.getName());
                    server.broadcastMessage("Logged-in " + record.getName());
                    server.subscribe(this);
                    return;
                } else {
                    sendMessage("/occupied");
                }
            } else {
                sendMessage("/notfound");
            }
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() throws IOException {
        while (true) {
            String message = in.readUTF();
            System.out.println(String.format("Incoming message from %s: %s", record.getName(), message));
            if (message.equals("/end")) {
                return;
            } else if (message.startsWith("/w")) {
                server.privateMessage(message, this);
                continue;
            } else if (message.startsWith("/changeNick")) {
                String newNick = message.substring("/changeNick".length()).trim();
                server.getAuthService().changeNickname(this.record.getId(), newNick);

                sendMessage(String.format("Nickname changed to %s. Changes will take effect after restarting",
                        newNick));
                continue;
            }
            server.broadcastMessage(String.format("%s: %s", record.getName(), message));
        }
    }

    public void closeConnection() {
        server.unsubscribe(this);
        server.broadcastMessage(record.getName() + " left chat");
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
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientHandler that = (ClientHandler) o;
        return record.equals(that.record) &&
                server.equals(that.server) &&
                socket.equals(that.socket) &&
                in.equals(that.in) &&
                out.equals(that.out);
    }

    @Override
    public int hashCode() {
        return Objects.hash(record, server, socket, in, out);
    }
}
