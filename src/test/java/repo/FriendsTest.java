package repo;


import org.junit.jupiter.api.Test;
import social_network.domain.User;
import social_network.repo.FriendsRepository;
import social_network.repo.UserRepository;
import social_network.repo.memory.Friends;
import social_network.repo.memory.Users;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FriendsTest {

    private final UserRepository users = new Users();
    private final FriendsRepository friends = new Friends(users);

    void mockSetUp() {
        users.mockSetUp();
        friends.add(users.get("u1"), users.get("u2"));
        friends.add(users.get("u1"), users.get("u3"));
        friends.add(users.get("u1"), users.get("u4"));
        friends.add(users.get("u2"), users.get("u3"));
        friends.add(users.get("u4"), users.get("u5"));
    }

    void deleteMockSetUp(){
        friends.remove(users.get("u1"), users.get("u2"));
        friends.remove(users.get("u1"), users.get("u3"));
        friends.remove(users.get("u1"), users.get("u4"));
        friends.remove(users.get("u2"), users.get("u3"));
        friends.remove(users.get("u4"), users.get("u5"));
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
        deleteMockSetUp();
    }

    @Test
    void remove() {
        mockSetUp();
        friends.remove(users.get("u1"), users.get("u3"));
        assertEquals(friends.getAll(users.get("u1")).size(), 2);
        deleteMockSetUp();
    }

    @Test
    void getAll() {
        mockSetUp();
        assertEquals(friends.getAll(users.get("u1")).size(), 3);
        deleteMockSetUp();
    }
}