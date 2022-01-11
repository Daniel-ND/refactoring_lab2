package server;

import server.command.*;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;

public class CommandHandler {
    private final Reader reader;
    private final Writer writer;
    private final Catalog catalog;
    private boolean isRunning = true;
    private final HashMap<String, Command> availableCommands = new HashMap<>();
    private Command unknownCommand;

    public CommandHandler(Reader reader, Writer writer, Catalog catalog) {
        this.reader = reader;
        this.writer = writer;
        this.catalog = catalog;
        this.registerDefaultCommands();
    }

    @SneakyThrows
    public void handleCommands() {
        BufferedReader reader = new BufferedReader(this.reader);
        new HelpCommand(writer).execute();
        while (isRunning) {
            writer.write("Input the command:\n");
            writer.flush();
            String cmd = reader.readLine().toLowerCase();
            Command command = getCmdFromStr(cmd);
            command.execute();
            writer.write("----\n");
            writer.flush();
        }
    }

    private void registerDefaultCommands() {
        unknownCommand = new UnknownCommand(writer);
        registerCommandType("list", new ListCommand(writer, catalog));
        registerCommandType("search", new SearchCommand(reader, writer, catalog));
        registerCommandType("add", new AddCommand(reader, writer, catalog));
        registerCommandType("exit", new QuitCommand(writer, () -> isRunning = false));
        registerCommandType("help", new HelpCommand(writer));
        registerCommandType("del", new DelCommand(reader, writer, catalog));
    }

    public void registerCommandType(String name, Command command) {
        availableCommands.put(name, command);
    }

    private Command getCmdFromStr(String cmd) {
        Command command = availableCommands.get(cmd);
        if (command == null) {
            command = unknownCommand;
        }
        return command;
    }
}