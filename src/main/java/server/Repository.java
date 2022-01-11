package server;

import lombok.SneakyThrows;
import server.model.Composition;
import server.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Repository {
    private final Connection connection;

    @SneakyThrows
    public Repository(String address, int port, String database, String user, String password) {
        this.connection = DriverManager
                .getConnection(String.format("jdbc:postgresql://%s:%d/%s", address, port, database), user, password);
        this.initTables();
    }

    private void initTables() {
        try (Statement stmt = connection.createStatement()) {
            String createUsersTable = new StringBuilder()
                    .append("create table if not exists users")
                    .append("(user_id serial primary key,")
                    .append("login varchar(30) not null unique,")
                    .append("password varchar(30) not null)")
                    .toString();
            stmt.execute(createUsersTable);

            String createCompositionsTable = new StringBuilder()
                    .append("create table if not exists compositions")
                    .append("(composition_id serial primary key,")
                    .append("author varchar(30),")
                    .append("name varchar(30),")
                    .append("user_id int not null references users on delete cascade,")
                    .append("unique(author, name, user_id))")
                    .toString();
            stmt.execute(createCompositionsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean save(Composition composition) {
        String query = new StringBuilder()
                .append("insert into compositions(author, name, user_id)")
                .append("values(?, ?, ?)").toString();
        boolean isSuccessful = false;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, composition.getAuthor());
            stmt.setString(2, composition.getName());
            stmt.setInt(3, composition.getUser().getId());
            stmt.execute();
            isSuccessful = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }

    public boolean delete(Composition composition) {
        String query = "delete from compositions where author = ? and name = ? and user_id = ?";
        boolean isSuccessful = false;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, composition.getAuthor());
            stmt.setString(2, composition.getName());
            stmt.setInt(3, composition.getUser().getId());
            stmt.execute();
            isSuccessful = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }

    @SneakyThrows
    public User findUser(int id) {
        String query = "select * from users where user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("login"),
                            resultSet.getString("password")
                    );
                } else {
                    throw new IllegalStateException("User doesn't exists!");
                }
            }
        }
    }

    @SneakyThrows
    public User findUserByLogin(String login) {
        String query = "select * from users where login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, login);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("user_id"),
                            login,
                            resultSet.getString("password")
                    );
                }
            }
        }
        return null;
    }

    public boolean register(String login, String password) {
        String query = "insert into users(login, password) VALUES (?, ?)";
        boolean isSuccessful = false;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.execute();
            isSuccessful = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }

    public Collection<Composition> findAll(int userId) {
        List<Composition> compositions = new ArrayList<>();
        String query = "select * from compositions where user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            User user = findUser(userId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    resultSet.getInt("user_id");
                    Composition composition = new Composition(
                            resultSet.getString("author"),
                            resultSet.getString("name"),
                            user
                    );
                    compositions.add(composition);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compositions;
    }

    public void shutdown() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Connection shutdown was failed.");
        }
    }
}
