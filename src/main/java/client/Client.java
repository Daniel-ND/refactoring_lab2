package client;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
    @SneakyThrows
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ConsoleApp client = new ConsoleApp("127.0.0.1", 4000);
        while (client.isRunning) {
            String str = reader.readLine().trim();
            client.sendMessage(str);
        }
        client.stopConnection();
    }
}
