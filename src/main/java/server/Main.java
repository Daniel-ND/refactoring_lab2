package server;

public class Main {
    public static void main(String[] args) {
        Catalog catalog;
        int port = 4000;
        catalog = new Catalog();
        Server server = new Server(catalog);
        server.start(port);
    }
}
