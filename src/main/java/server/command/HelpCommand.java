package server.command;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.Writer;

@AllArgsConstructor
public class HelpCommand implements Command {

    private final Writer writer;

    @SneakyThrows
    @Override
    public void execute() {
        writer.write("Usage:\n");
        writer.write("\tType one of the commands:\n");
        writer.write("\t\tlist - to display all items of catalog\n");
        writer.write("\t\tsearch - to find items in catalog\n");
        writer.write("\t\tadd - to add new item\n");
        writer.write("\t\tdel - to remove some item from catalog\n");
        writer.write("\t\thelp - to display all commands.\n");
        writer.write("\t\tquit - to exit\n\n");
        writer.flush();
    }
}

