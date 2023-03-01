package service;


import org.junit.jupiter.api.Test;
import social_network.domain.User;
import social_network.service.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTest {
    private final Service service = new Service();
    @Test
    void numberOfCommunities() {
        int previous=service.numberOfCommunities();
        service.mockSetUp();
        assertEquals(service.numberOfCommunities(), previous+1);
        service.deleteMockSetUp();
    }

    @Test
    void addFriend() {
        service.mockSetUp();
        User user99=new User("99","99");
        service.addUser(user99);
        service.addFriend(service.getUsers().get("u1"),user99);
        assertEquals(service.friendsOfUser(service.getUsers().get("u1")).size(),4);
        service.removeFriend(service.getUsers().get("u1"),user99);
        service.removeUser(user99);
        service.deleteMockSetUp();
    }

    @Test
    void removeFriend() {
        service.mockSetUp();
        service.removeFriend(service.getUsers().get("u1"),service.getUsers().get("u2"));
        assertEquals(service.friendsOfUser(service.getUsers().get("u1")).size(),2);
        service.deleteMockSetUp();
    }

    @Test
    void friendsOfUser() {
        service.mockSetUp();
        assertEquals(service.friendsOfUser(service.getUsers().get("u1")).size(),3);
        service.deleteMockSetUp();
    }


    @Test
    void addUser() {
        service.mockSetUp();
        User user99=new User("99","99");
        int previous=service.getUsers().size();
        service.addUser(user99);
        assertEquals(service.getUsers().size(),previous+1);
        service.removeUser(user99);
        service.deleteMockSetUp();
    }

    @Test
    void mostSociableCommunity() {
        service.mockSetUp();
        int previous=service.numberOfCommunities();
        User user99=new User("99","99");
        service.addUser(user99);
        assertEquals(service.numberOfCommunities(), previous+1);
        service.addFriend(service.getUsers().get("u1"),user99);
        List<User> mostSociableCommunity=service.mostSociableCommunity();
        assertEquals(mostSociableCommunity.size(),6);
        service.deleteMockSetUp();
        service.removeUser(user99);
    }
}