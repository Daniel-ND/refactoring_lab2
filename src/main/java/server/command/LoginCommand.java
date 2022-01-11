package server.command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import server.Catalog;
import server.model.User;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;
import java.util.function.Function;

@AllArgsConstructor
public class LoginCommand implements Command {

    private final Reader reader;
    private final Writer writer;
    private final Catalog catalog;
    private final Function<User, User> callback;

    @SneakyThrows
    @Override
    public void execute(User user) {
        if (isAuthenticated(user)) {
            writer.write("Already logged in.");
            writer.flush();
            return;
        }
        BufferedReader br = new BufferedReader(reader);
        writer.write("login:\n");
        writer.flush();
        String login = br.readLine();
        writer.write(":password\n");
        writer.flush();
        String password = br.readLine();
        user = catalog.authenticate(login, password);
        if (user != null) {
            writer.write("Successfully logged in!\n");
        } else {
            writer.write("Login failed.\n");
        }
        writer.flush();
        callback.apply(user);
    }
}
