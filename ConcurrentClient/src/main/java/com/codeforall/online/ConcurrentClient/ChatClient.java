package com.codeforall.online.ConcurrentClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * The ChatClient class represents a client.
 * It connects to the chat server
 * Allows users to send with the keyboard input
 * Allows to receive messages.
 */

public class ChatClient {
    private Socket socket;
    private static String username;
    private ClientCommunication clientCommunication;
    private PrintWriter printWriter;
    private static final int PORT = 12345;
    private static final String ADDRESS = "localhost";

    public ChatClient(String username) {
        this.username = username;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        try {
            Socket socket = new Socket(ADDRESS, PORT);
            System.out.println("Connected to server.");

            ClientCommunication clientCommunication = new ClientCommunication(socket, username);
            Thread communicationThread = new Thread(clientCommunication);
            communicationThread.start();

            clientCommunication.sendUsername();


        } catch (IOException e) {
            e.printStackTrace();
                }
            }
        }

