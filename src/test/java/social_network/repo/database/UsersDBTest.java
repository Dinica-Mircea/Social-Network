package social_network.repo.database;

import org.junit.jupiter.api.Test;
import social_network.domain.User;

import static org.junit.jupiter.api.Assertions.*;

class UsersDBTest {
    UsersDB usersDB= new UsersDB();
    User user1 = new User("u1", "p1");
    User user2 = new User("u2", "p2");
    User user3 = new User("u3", "p3");
    User user4 = new User("u4", "p4");
    User user5 = new User("u5", "p5");
    int size=usersDB.getAll().size();
    @Test
    void getAll() {
        assertEquals(usersDB.getAll().size(),size);
    }

    @Test
    void add() {

        usersDB.add(user1);
        usersDB.add(user2);
        usersDB.add(user3);
        usersDB.add(user4);
        usersDB.add(user5);
        assertEquals(usersDB.size(),size+5);
        usersDB.remove(user1);
        usersDB.remove(user2);
        usersDB.remove(user3);
        usersDB.remove(user4);
        usersDB.remove(user5);
    }

    @Test
    void remove() {
        usersDB.remove(user1);
        usersDB.remove(user2);
        usersDB.remove(user3);
        usersDB.remove(user4);
        usersDB.remove(user5);
        assertEquals(usersDB.size(),size);
    }

    @Test
    void size() {
        assertEquals(usersDB.size(),size);
    }
}