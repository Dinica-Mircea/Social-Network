package social_network.userInterface;


import social_network.domain.User;
import social_network.repo.UserRepository;
import social_network.service.Service;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserInterface {
    private final Service service;
    private final Scanner in;

    public UserInterface() {
        service = new Service();
        in = new Scanner(System.in);
    }

    public UserInterface(String filename) {
        in = new Scanner(System.in);
        service = new Service(filename);
    }

    public String menu() {
        System.out.println("1-Add User");
        System.out.println("2-Add Friend");
        System.out.println("3-Show all users");
        System.out.println("4-Show number of communities");
        System.out.println("5-Show most sociable community");
        System.out.println("6-Remove User");
        System.out.println("7-Remove Friend");
        System.out.println("8-Show all user's friends");
        System.out.println("99-Exit");
        System.out.println("Type command: ");
        return in.nextLine();
    }

    public void addUser() {
        System.out.println("Username: ");
        String username = in.nextLine();
        System.out.println("Password: ");
        String password = in.nextLine();
        User newUser = new User(username, password);
        service.addUser(newUser);

    }

    public void addFriend() {
        System.out.println("User Username: ");
        String usernameUser = in.nextLine();
        User user = service.getUser(usernameUser);
        System.out.println("Friend Username: ");
        String friendUsername = in.nextLine();
        User friend = service.getUser(friendUsername);
        service.addFriend(user, friend);

    }

    public void showAllUsers() {
        UserRepository users = service.getUsers();
        System.out.println(users.toString());
    }

    public void showNumberOfCommunities() {
        int output = service.numberOfCommunities();
        System.out.println("There are " + output + " communities");
    }

    public void showMostSociableCommunity() {
        List<User> users = service.mostSociableCommunity();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    public void removeUser() {
        System.out.println("Username: ");
        String username = in.nextLine();
        User user = service.getUser(username);
        service.removeUser(user);
    }

    public void removeFriend() {
        System.out.println("User Username: ");
        String userUsername = in.nextLine();
        User user = service.getUser(userUsername);
        System.out.println("Friend Username: ");
        String friendUsername = in.nextLine();
        User friend = service.getUser(friendUsername);
        service.removeFriend(user, friend);
    }

    public void showAllUsersFriends() {
        System.out.println("User Username: ");
        String userUsername = in.nextLine();
        User user = service.getUser(userUsername);
        System.out.println(service.friendsOfUser(user).toString());
    }

    public void run() {
        boolean stop = false;
        while (!stop) {
            String command = this.menu();
            try {
                if (Objects.equals(command, "99")) {
                    stop = true;
                } else if (Objects.equals(command, "1")) {
                    addUser();
                } else if (Objects.equals(command, "2")) {
                    addFriend();
                } else if (Objects.equals(command, "3")) {
                    showAllUsers();
                } else if (Objects.equals(command, "4")) {
                    showNumberOfCommunities();
                } else if (Objects.equals(command, "5")) {
                    showMostSociableCommunity();
                } else if (Objects.equals(command, "6")) {
                    removeUser();
                } else if (Objects.equals(command, "7")) {
                    removeFriend();
                } else if (Objects.equals(command, "8")) {
                    showAllUsersFriends();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
}
