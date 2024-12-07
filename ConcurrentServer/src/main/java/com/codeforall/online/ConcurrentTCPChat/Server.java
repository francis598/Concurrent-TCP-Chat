package com.codeforall.online.ConcurrentTCPChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * The ChatServer is responsible for listens for incoming client connections
 * Manages client communication.
 */

public class Server {
    public static void main(String[] args) {
        Set<ClientHandler> clientsList = new HashSet<>();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());


                ClientHandler clientHandler = new ClientHandler(clientSocket, clientsList);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }
}

