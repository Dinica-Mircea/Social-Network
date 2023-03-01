package social_network.repo.database;

import org.junit.jupiter.api.Test;
import social_network.repo.FriendsRepository;
import social_network.repo.UserRepository;
import social_network.repo.memory.Friends;
import social_network.repo.memory.Users;

import static org.junit.jupiter.api.Assertions.*;

class FriendsDBTest {

    private final UserRepository users = new UsersDB();
    private final FriendsRepository friends = new FriendsDB(users);

    @Test
    void add() {
        users.mockSetUp();
        friends.add(users.get("u1"), users.get("u2"));
        friends.add(users.get("u1"), users.get("u3"));
        friends.add(users.get("u1"), users.get("u4"));
        friends.add(users.get("u2"), users.get("u3"));
        friends.add(users.get("u4"), users.get("u5"));
        assertEquals(friends.getAll(users.get("u1")).size(), 3);
        assertEquals(friends.getAll(users.get("u2")).size(), 2);
        assertEquals(friends.getAll(users.get("u4")).size(), 2);
    }

    @Test
    void removeAllFriends() {
        friends.remove(users.get("u1"),users.get("u3"));
        friends.remove(users.get("u1"),users.get("u4"));
        assertEquals(friends.getAll(users.get("u1")).size(), 1);
        friends.remove(users.get("u1"),users.get("u2"));
        friends.remove(users.get("u2"),users.get("u3"));
        assertEquals(friends.getAll(users.get("u2")).size(), 0);
        friends.remove(users.get("u4"),users.get("u5"));
        assertEquals(friends.getAll(users.get("u1")).size(), 0);
        users.remove(users.get("u1"));
        users.remove(users.get("u2"));
        users.remove(users.get("u3"));
        users.remove(users.get("u4"));
        users.remove(users.get("u5"));
    }

}