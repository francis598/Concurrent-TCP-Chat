package com.codeforall.online.ConcurrentTCPChat.Commands;

import com.codeforall.online.ConcurrentTCPChat.ClientHandler;
import com.codeforall.online.ConcurrentTCPChat.Server;

/**
 * Handles invalid commands sent by the client
 */
public class InvalidHandler implements CommandHandler {

    @Override
    public void handle(Server server, ClientHandler sender, String message) {
        if (message.startsWith(Messages.COMMAND_IDENTIFIER)) {

        }
    }
}
