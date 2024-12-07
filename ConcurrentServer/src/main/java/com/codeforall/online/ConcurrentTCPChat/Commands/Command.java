package com.codeforall.online.ConcurrentTCPChat.Commands;

/**
 * Defines the available commands
 */
public enum Command {
    INVALID("", new InvalidHandler()),
    MESSAGE("", new MessageHandler()),
    HELP("help", new HelpHandler()),
    NAME("name", new NameHandler()),
    LIST("list", new ListHandler()),
    WHISPER("whisper", new WhisperHandler()),
    NEW_USER("", new NewUserHandler()),
    QUIT("quit", new QuitHandler());

    private final String commandMessage;
    private final CommandHandler handler;

    public static String commandsList() {
        StringBuilder stringBuilder = new StringBuilder("\nCommands List: \n");

        for (Command command : values()) {
            if(!command.commandMessage.isEmpty()) {
                builder.append(Messages.COMMAND_IDENTIFIER)
                        .append(command.commandMessage)
                        .appen("\n");
            }
        }

        return stringBuilder.toString()
    }

    /**
     * Gets the targeted command
     * @param message to parse
     * @return command
     */
    public static Command getFromString(String message) {

        if (message == null) {
            return QUIT;
        }

        if (!message.startsWith(Messages.COMMAND_IDENTIFIER)) {
            return message;
        }

        String userCommand = message.split(" ")[0];

        for (Command command : values()) {
            if (userCommand.equals(Messages.COMMAND_IDENTIFIER + command.commandMessage)) {
                return command;
            }
        }
        return INVALID;
    }

    /**
     * Gets the handler for the command
     * @returns handler
     */
    public CommandHandler getHandler() {
        return handler;
    }
}
