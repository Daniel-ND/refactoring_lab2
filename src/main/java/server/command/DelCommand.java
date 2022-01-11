package server.command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import server.model.Composition;
import server.model.User;
import server.Catalog;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;

@AllArgsConstructor
public class DelCommand implements Command {

    private final Reader reader;
    private final Writer writer;
    private final Catalog catalog;

    @Override
    @SneakyThrows
    public void execute(User user) {
        if (!isAuthenticated(user)) {
            writer.write("Only authenticated user can execute this server.command.\n");
            writer.flush();
            return;
        }
        BufferedReader br = new BufferedReader(reader);
        writer.write("Input author's name:\n");
        writer.flush();
        String author = br.readLine();
        writer.write("Input the composition's name:\n");
        writer.flush();
        String name = br.readLine();

        Composition composition = new Composition(author, name, user);
        if (catalog.delete(composition)) {
            writer.write("Composition was deleted successfully\n");
        } else {
            writer.write("Occurred an error.\n");
        }
        writer.flush();
    }
}