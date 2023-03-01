package repo;


import org.junit.jupiter.api.Test;
import social_network.domain.User;
import social_network.repo.memory.Users;
import social_network.repo.validators.RepoException;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsersTest {
    private final Users users = new Users();

    void setUpUsers() {
        users.mockSetUp();
    }
    void deleteMockSetUp() {
        users.remove(users.get("u1"));
        users.remove(users.get("u2"));
        users.remove(users.get("u3"));
        users.remove(users.get("u4"));
        users.remove(users.get("u5"));
    }

    @Test
    void getUsers() {
        setUpUsers();
        List<User> usersCopy = users.getAll();
        assertEquals(usersCopy.size(), 5);
        deleteMockSetUp();
    }

    @Test
    void add() {
        setUpUsers();
        User user6 = new User("u6", "p6");
        User user6same = new User("u6", "p6222");
        users.add(user6);
        assertEquals(users.size(), 6);
        RepoException thrown = assertThrows(RepoException.class, () -> users.add(user6same));
        assertEquals(thrown.getMessage(), "Username already exists!");
        users.updatePassword(user6, "newPassword");
        assertEquals(user6.getPassword(), "newPassword");
        users.updateUsername(user6, "newUsername");
        assertEquals(user6.getUsername(), "newUsername");
        deleteMockSetUp();

    }

    @Test
    void remove() {
        setUpUsers();
        users.remove(users.get("u5"));
        assertEquals(users.size(), 4);
        users.add(new User("u5","p5"));
        deleteMockSetUp();
    }
}