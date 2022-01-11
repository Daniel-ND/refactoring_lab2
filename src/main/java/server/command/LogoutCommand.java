package server.command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import server.model.User;

import java.io.Writer;

@AllArgsConstructor
public class LogoutCommand implements Command {
    private final Writer writer;
    private final Runnable callback;

    @SneakyThrows
    @Override
    public void execute(User user) {
        if (!isAuthenticated(user)) {
            writer.write("You must be logged in.\n");
            return;
        }
        writer.write("Logged out.\n");
        writer.flush();
        callback.run();
    }
}
