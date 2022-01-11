package server.command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.Writer;

@AllArgsConstructor
public class UnknownCommand implements Command {

    private Writer writer;

    @SneakyThrows
    @Override
    public void execute() {
        writer.write("Unknown command.\n");
    }
}
