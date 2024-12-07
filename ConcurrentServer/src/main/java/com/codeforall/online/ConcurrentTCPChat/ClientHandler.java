package com.codeforall.online.ConcurrentTCPChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

/**
 * The ClientHandler class is responsible for managing the communication
*/

    public class ClientHandler implements Runnable {
        private Socket socket;
        private Set<ClientHandler> clientsList;
        private PrintWriter output;
        private BufferedReader input;
        private String username;

        public ClientHandler(Socket socket, Set<ClientHandler> clientList) throws IOException {
            this.socket = socket;
            this.clientsList = clientList;
            this.output = new PrintWriter(socket.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = input.readLine();
            broadcast( username + " has joined the chat. \n");
        }

    /**
     * Responsible for the communication loop with the client.
     * Reads messages from the client and sends them to all other clients.
     */

        @Override
        public void run() {

            try {
                synchronized (clientsList) {
                    clientsList.add(this);
                }
                String message;
                while ((message = input.readLine()) != null) {
                    broadcast(message);
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.err.println("Error in client communication: " + e.getMessage());
            } finally {
                closeResources();
                broadcast(username + " has left the chat. ");
            }
        }

        private void broadcast(String message) {
            synchronized (clientsList) {
                for (ClientHandler client : clientsList) {
                    if (client != this) {
                        client.output.println(message);
                    }
                }
            }
        }

        private void closeResources() {
            try {
                synchronized (clientsList) {
                    clientsList.remove(this);
                }
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }

        public void send(String message) {
            output.println(message);
        }
    }
