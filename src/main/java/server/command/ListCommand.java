package server.command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import server.model.Composition;
import server.model.User;
import server.Catalog;

import java.io.Writer;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ListCommand implements Command {

    private final Writer writer;
    private final Catalog catalog;

    @SneakyThrows
    @Override
    public void execute(User user) {
        if (!isAuthenticated(user)) {
            writer.write("Only authenticated user can execute this command.\n");
            writer.flush();
            return;
        }
        writer.write("All compositions in catalog:\n");
        String compositions = catalog.all(user.getId()).stream()
                .map(Composition::toString)
                .collect(Collectors.joining("\n"));
        writer.write(compositions);
        writer.write("\n");
        writer.flush();
    }
}
