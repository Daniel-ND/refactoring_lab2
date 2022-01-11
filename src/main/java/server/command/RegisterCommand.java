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
public class RegisterCommand implements Command {

    private final Reader reader;
    private final Writer writer;
    private final Catalog catalog;
    private final Function<User, User> callback;

    @SneakyThrows
    @Override
    public void execute(User user) {
        if (isAuthenticated(user)) {
            writer.write("You are already logged in.");
            writer.flush();
            return;
        }
        BufferedReader br = new BufferedReader(reader);
        writer.write("login:\n");
        writer.flush();
        String login = br.readLine();
        writer.write("password:\n");
        writer.flush();
        String password = br.readLine();

        User new_user = catalog.register(login, password);
        if (new_user != null) {
            writer.write("Successfully registered and logged in!\n");
        } else {
            writer.write("Registration failed");
        }
        writer.flush();
        callback.apply(new_user);
    }
}
