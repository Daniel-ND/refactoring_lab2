package server.command;

import server.model.User;

public interface Command {
    void execute(User user);

    default boolean isAuthenticated(User user) {
        return user != null;
    }
}
