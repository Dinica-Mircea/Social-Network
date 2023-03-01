package social_network.repo.database;

import social_network.domain.User;
import social_network.repo.FriendsRepository;
import social_network.repo.UserRepository;

import java.sql.*;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FriendsDB implements FriendsRepository {
    JDBCUtils jdbcUtils = new JDBCUtils();
    private final UserRepository users;

    public FriendsDB(UserRepository users) {
        this.users = users;
    }


    @Override
    public void add(User user, User friend) {
        add(user, friend, LocalDateTime.now());
    }

    @Override
    public void removeAllFriends(User user) {
        for (User friend : getAll(user).keySet()) {
            remove(user, friend);
        }
    }

    @Override
    public void updateDateOfFriendship(User user, User friend, LocalDateTime newFriendsFrom) {
        remove(user, friend);
        add(user, friend, newFriendsFrom);
    }

    @Override
    public void add(User user, User friend, LocalDateTime friendsFrom) {
        String query = "INSERT INTO friendships(userusername,friendusername,friendsfrom) VALUES(?, ?, ?)";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, friend.getUsername());
            ZonedDateTime zdt = friendsFrom.atZone(ZoneId.systemDefault());
            java.sql.Date date = new java.sql.Date(Date.from(zdt.toInstant()).getTime());
            statement.setDate(3, date);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(User user, User friend) {
        String query = "DELETE FROM friendships WHERE userusername = ? AND friendusername = ?";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, friend.getUsername());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<User, LocalDateTime> getAll(User user) {
        Map<User, LocalDateTime> friends = new HashMap<>();

        String query = "SELECT * from friendships";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                String userUsername = resultSet.getString("userusername");
                String friendUsername = resultSet.getString("friendusername");
                //Date friendsFrom=resultSet.getDate("friendsfrom");
                LocalDate friendsFrom = Instant.ofEpochMilli(resultSet.getDate("friendsfrom").getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                if (Objects.equals(user.getUsername(), userUsername))
                    friends.put(users.get(friendUsername), friendsFrom.atStartOfDay());
                else if (Objects.equals(user.getUsername(), friendUsername))
                    friends.put(users.get(userUsername), friendsFrom.atStartOfDay());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }
}
