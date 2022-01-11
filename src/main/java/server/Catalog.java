package server;

import server.model.Composition;
import server.model.User;


import java.util.concurrent.CopyOnWriteArrayList;

public class Catalog {
    private final Repository repository;

    public Catalog(Repository repository) {
        this.repository = repository;
    }

    public CopyOnWriteArrayList<Composition> all(int userId) {
        return new CopyOnWriteArrayList<>(repository.findAll(userId));
    }

    public User authenticate(String login, String password) {
        User user = repository.findUserByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User register(String login, String password) {
        boolean isSuccessful = repository.register(login, password);
        if (isSuccessful) {
            return repository.findUserByLogin(login);
        } else {
            return null;
        }
    }

    public boolean add(Composition composition) {
        return repository.save(composition);
    }

    public boolean delete(Composition composition) {
        return repository.delete(composition);
    }
}
