package service;

import domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTest {
    private final Service service = new Service();

    @Test
    void numberOfCommunities() {
        service.mockSetUp();
        User user99=new User("99","99");
        service.addUser(user99);
        assertEquals(service.numberOfCommunities(), 2);
    }

    @Test
    void addFriend() {
        service.mockSetUp();
        User user99=new User("99","99");
        service.addUser(user99);
        service.addFriend(service.getUsers().get("u1"),user99);
        assertEquals(service.friendsOfUser(service.getUsers().get("u1")).size(),4);
    }

    @Test
    void removeFriend() {
        service.mockSetUp();
        service.removeFriend(service.getUsers().get("u1"),service.getUsers().get("u2"));
        assertEquals(service.friendsOfUser(service.getUsers().get("u1")).size(),2);
    }

    @Test
    void friendsOfUser() {
        service.mockSetUp();
        assertEquals(service.friendsOfUser(service.getUsers().get("u1")).size(),3);
    }

    @Test
    void users() {
        service.mockSetUp();
        assertEquals(service.getUsers().size(), 5);
    }

    @Test
    void addUser() {
        service.mockSetUp();
        User user99=new User("99","99");
        service.addUser(user99);
        assertEquals(service.getUsers().size(),6);
    }

    @Test
    void mostSociableCommunity() {
        service.mockSetUp();
        User user99=new User("99","99");
        service.addUser(user99);
        assertEquals(service.numberOfCommunities(), 2);
        service.addFriend(service.getUsers().get("u1"),user99);
        List<User> mostSociableCommunity=service.mostSociableCommunity();
        assertEquals(mostSociableCommunity.size(),6);
    }
}