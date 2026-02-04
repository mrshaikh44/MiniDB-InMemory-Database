package db.core;

import db.exceptions.InvalidCommandException;
import db.exceptions.InvalidTTLException;

public class CommandParser {

    public static Command parse(String input) {

        if (input == null || input.trim().isEmpty()) {
            throw new InvalidCommandException("Empty command");
        }

        String[] tokens = input.trim().split("\\s+");
        String cmd = tokens[0].toUpperCase();

        try {
            switch (cmd) {

                case "PUT":
                    if (tokens.length < 3 || tokens.length > 4) {
                        throw new InvalidCommandException("PUT <key> <value> [ttl]");
                    }

                    Integer key = Integer.parseInt(tokens[1]);
                    String value = tokens[2];
                    Long ttl = null;

                    if (tokens.length == 4) {
                        ttl = Long.parseLong(tokens[3]);
                        if (ttl <= 0) {
                            throw new InvalidTTLException("TTL must be > 0");
                        }
                    }
                    return new Command(CommandType.PUT, key, value, ttl);

                case "GET":
                    if (tokens.length != 2)
                        throw new InvalidCommandException("GET <key>");
                    return new Command(
                            CommandType.GET,
                            Integer.parseInt(tokens[1]),
                            null,
                            null
                    );

                case "DELETE":
                    if (tokens.length != 2)
                        throw new InvalidCommandException("DELETE <key>");
                    return new Command(
                            CommandType.DELETE,
                            Integer.parseInt(tokens[1]),
                            null,
                            null
                    );

                case "STOP":
                    return new Command(CommandType.STOP, null, null, null);

                case "START":
                    return new Command(CommandType.START, null, null, null);

                case "EXIT":
                    return new Command(CommandType.EXIT, null, null, null);

                default:
                    throw new InvalidCommandException("Unknown command: " + cmd);
            }

        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Key / TTL must be numeric");
        }
    }
}

