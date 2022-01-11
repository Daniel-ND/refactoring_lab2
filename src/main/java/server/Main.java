package server;

public class Main {
    public static void main(String[] args) {
        Catalog catalog;
        int port = 4000;
        Repository repository = new Repository("localhost", 5432, "postgres", "postgres", "daniel");
        catalog = new Catalog(repository);
        Server server = new Server(catalog);
        server.start(port);
    }
}
