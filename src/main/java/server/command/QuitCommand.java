package server.command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import server.model.User;

import java.io.Writer;

@AllArgsConstructor
public class QuitCommand implements Command {

    private Writer writer;
    private Runnable performExit;

    @SneakyThrows
    @Override
    public void execute(User user) {
        writer.write("Exiting the program.\n");
        performExit.run();
    }
}