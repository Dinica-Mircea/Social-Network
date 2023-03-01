package social_network.repo;

import social_network.domain.User;

import java.time.LocalDateTime;
import java.util.Map;

public interface FriendsRepository {
    void add(User user, User friend);

    void removeAllFriends(User user);

    void updateDateOfFriendship(User user, User friend, LocalDateTime newFriendsFrom);

    void add(User user, User friend, LocalDateTime friendsFrom);

    void remove(User user, User friend);

    Map<User, LocalDateTime> getAll(User user);
}
