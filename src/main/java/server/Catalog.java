package server;

import server.model.Composition;

import java.util.concurrent.CopyOnWriteArrayList;

public class Catalog {
    private final CopyOnWriteArrayList<Composition> compositions;

    public Catalog() {
        compositions = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList<Composition> all() {
        return compositions;
    }

    public boolean add(Composition composition) {
        return compositions.add(composition);
    }

    public boolean delete(Composition composition) {
        return compositions.remove(composition);
    }
}
