package server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class Composition {
    private String author;
    private String name;
    private User user;

    public String toString() {
        return author + " - " + name;
    }
}
