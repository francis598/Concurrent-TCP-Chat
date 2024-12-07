package com.codeforall.online.ConcurrentTCPChat.Commands;

import com.codeforall.online.ConcurrentTCPChat.ClientHandler;
import com.codeforall.online.ConcurrentTCPChat.Server;

public interface CommandHandler {
    void handle(Server server, ClientHandler sender, String message);

}
