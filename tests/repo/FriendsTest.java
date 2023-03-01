package repo;

import domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FriendsTest {

    private final Users users = new Users();
    private final Friends friends = new Friends(users);

    void mockSetUp() {
        users.mockSetUp();
        friends.add(users.get("u1"), users.get("u2"));
        friends.add(users.get("u1"), users.get("u3"));
        friends.add(users.get("u1"), users.get("u4"));
        friends.add(users.get("u2"), users.get("u3"));
        friends.add(users.get("u4"), users.get("u5"));
    }

    @Test
    void add() {
        mockSetUp();
        assertEquals(friends.getAll(users.get("u1")).size(), 3);
        assertEquals(friends.getAll(users.get("u2")).size(), 2);
        assertEquals(friends.getAll(users.get("u4")).size(), 2);
        LocalDateTime friendsFrom = LocalDateTime.now();
        friends.updateDateOfFriendship(users.get("u1"), users.get("u2"), friendsFrom);
        User u1 = users.get("u1");
        User u2 = users.get("u2");
        assertEquals(friends.getAll(u1).get(u2), friendsFrom);
    }

    @Test
    void remove() {
        mockSetUp();
        friends.remove(users.get("u1"), users.get("u3"));
        assertEquals(friends.getAll(users.get("u1")).size(), 2);
    }

    @Test
    void getAll() {
        mockSetUp();
        assertEquals(friends.getAll(users.get("u1")).size(), 3);
    }
}