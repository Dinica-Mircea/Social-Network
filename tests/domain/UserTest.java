package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user1 = new User("1", "2");

    @Test
    void getUsername() {
        assertEquals(user1.getUsername(), "1");
    }

    @Test
    void setUsername() {
        user1.setUsername("mircea");
        assertEquals(user1.getUsername(), "mircea");
    }

    @Test
    void getPassword() {
        assertEquals(user1.getPassword(), "2");
    }

    @Test
    void setPassword() {
        user1.setPassword("psw");
        assertEquals(user1.getPassword(), "psw");
    }
}