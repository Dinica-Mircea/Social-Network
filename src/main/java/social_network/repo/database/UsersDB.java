package social_network.repo.database;

import social_network.domain.User;
import social_network.domain.validators.UserValidator;
import social_network.repo.UserRepository;
import social_network.repo.validators.RepoException;
import social_network.repo.validators.UsersValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UsersDB implements UserRepository {
    private final UserValidator userValidator = new UserValidator();
    private final UsersValidator usersValidator = new UsersValidator();
    private final JDBCUtils jdbcUtils = new JDBCUtils();

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        String query = "SELECT * from users";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(username, password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public String toString() {
        return getAll().toString();
    }

    @Override
    public void add(User user) {
        userValidator.validate(user);
        usersValidator.validate(getAll(), user);
        String query = "INSERT INTO users(username,password) VALUES(?, ?)";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(User user) {
        String query = "DELETE FROM users WHERE username = ?";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, user.getUsername());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return getAll().size();
    }

    @Override
    public User get(String username) {
        for (User user : getAll()) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        throw new RepoException("Username doesn't exist!");
    }

    public void mockSetUp() {
        User user1 = new User("u1", "p1");
        User user2 = new User("u2", "p2");
        User user3 = new User("u3", "p3");
        User user4 = new User("u4", "p4");
        User user5 = new User("u5", "p5");
        add(user1);
        add(user2);
        add(user3);
        add(user4);
        add(user5);
    }

}
