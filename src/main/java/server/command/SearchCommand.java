package server.command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import server.model.Composition;
import server.model.User;
import server.Catalog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SearchCommand implements Command {

    private final Reader reader;
    private final Writer writer;
    private final Catalog catalog;

    @Override
    @SneakyThrows
    public void execute(User user) {
        if (!isAuthenticated(user)) {
            writer.write("Only authenticated user can execute this command.\n");
            writer.flush();
            return;
        }
        BufferedReader br = new BufferedReader(reader);
        writer.write("Input the part of the name to find composition in the catalog:\n");
        writer.flush();
        String searchStr = br.readLine();
        List<String> composition = catalog.all(user.getId()).stream().map(Composition::toString).filter(c -> c.contains(searchStr)).collect(Collectors.toList());
        if (composition.size() == 0) {
            writer.write("No one item was found by this criteria.\n");
        } else {
            composition.forEach(c -> {
                try {
                    writer.write(String.format("%s\n", c));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
