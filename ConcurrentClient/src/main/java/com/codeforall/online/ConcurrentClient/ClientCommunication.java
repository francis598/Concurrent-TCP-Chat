package com.codeforall.online.ConcurrentClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Implements Runnable to handle message reception in a separate thread.
 */

public class ClientCommunication implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String message;
    private String username;

    public ClientCommunication(Socket socket, String username) throws IOException {
        this.socket = socket;
        this.username = username;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendUsername() {
        out.println(username);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Keyboard is ready. Press enter to send a new message \n");

        Thread receiveThread = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (NoSuchElementException e) {
                System.out.println("No line found: The server might have closed the connection.");
            } catch (IOException e) {
                System.err.println("Error reading from server: " + e.getMessage());
            } finally {
                try {
                    socket.close();  // Fechar o socket aqui se houver erro
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        });

        receiveThread.start();

        try {
            while (true) {
                String message = scanner.nextLine();
                if ("exit".equalsIgnoreCase(message)) {
                    out.println(username + " has left the chat."); // Notificar o servidor
                    break;
                }
                out.println(username + ": " + message);
                System.out.println("Message sent: " + message);
            }
        } catch (Exception e) {
            System.err.println("Error during communication: " + e.getMessage());
        } finally {
            try {
                scanner.close(); // Fechar o scanner
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
