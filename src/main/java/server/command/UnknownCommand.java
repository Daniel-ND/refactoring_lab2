package server.command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import server.model.User;

import java.io.Writer;

@AllArgsConstructor
public class UnknownCommand implements Command {

    private Writer writer;

    @SneakyThrows
    @Override
    public void execute(User user) {
        writer.write("Unknown command.\n");
    }
}
