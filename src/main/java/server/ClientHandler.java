package server;

import lombok.SneakyThrows;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Reader reader;
    private final Writer writer;
    private final Catalog catalog;

    @SneakyThrows
    public ClientHandler(Socket socket, Catalog catalog) {
        this.socket = socket;
        this.catalog = catalog;
        writer = new OutputStreamWriter(socket.getOutputStream());
        reader = new InputStreamReader(socket.getInputStream());
    }

    @Override
    public void run() {
        CommandHandler commandHandler = new CommandHandler(reader, writer, catalog);
        commandHandler.handleCommands();
        stop();
    }

    @SneakyThrows
    public void stop() {
        reader.close();
        writer.close();
        socket.close();
    }
}
