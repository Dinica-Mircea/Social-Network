package repo;

import domain.User;
import org.junit.jupiter.api.Test;
import repo.validators.RepoException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsersTest {
    private final Users users = new Users();

    void setUpUsers() {
        users.mockSetUp();
    }

    @Test
    void getUsers() {
        setUpUsers();
        List<User> usersCopy = users.getAll();
        assertEquals(usersCopy.size(), 5);
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

    }

    @Test
    void remove() {
        setUpUsers();
        users.remove(users.get("u5"));
        assertEquals(users.size(), 4);
    }
}