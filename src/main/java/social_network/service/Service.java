package social_network.service;

import social_network.domain.User;
import social_network.repo.FriendsRepository;
import social_network.repo.database.FriendsDB;
import social_network.repo.database.UsersDB;
import social_network.repo.UserRepository;
import social_network.repo.file.FriendsFileRepository;
import java.time.LocalDateTime;
import java.util.*;

public class Service {
    private final UserRepository users;
    private final FriendsRepository friends;
    private final FriendsFileRepository friendsFileRepository;
    ArrayList<ArrayList<User>> components
            = new ArrayList<>();

    public Service() {
        users = new UsersDB();
        friends = new FriendsDB(users);
        friendsFileRepository = null;

    }

    public Service(String filename) {
        friendsFileRepository = new FriendsFileRepository(filename);
        users = friendsFileRepository.getUsers();
        friends = friendsFileRepository.getFriends();
    }


    public void addUser(User user) {
        users.add(user);
        updateFile();
    }

    private void updateFile() {
        if (friendsFileRepository != null) {
            friendsFileRepository.writeToFile();
        }
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void removeUser(User user) {
        friends.removeAllFriends(user);
        users.remove(user);
        updateFile();
    }

    public void addFriend(User user, User friend) {
        friends.add(user, friend);
        updateFile();
    }

    public void removeFriend(User user, User friend) {
        friends.remove(user, friend);
        updateFile();
    }


    public Map<User, LocalDateTime> friendsOfUser(User user) {
        return friends.getAll(user);
    }

    public UserRepository getUsers() {
        return users;
    }

    public void mockSetUp() {
        users.mockSetUp();
        friends.add(users.get("u1"), users.get("u2"));
        friends.add(users.get("u1"), users.get("u3"));
        friends.add(users.get("u1"), users.get("u4"));
        friends.add(users.get("u2"), users.get("u3"));
        friends.add(users.get("u4"), users.get("u5"));
    }

    public void deleteMockSetUp(){
        removeUser(users.get("u1"));
        removeUser(users.get("u2"));
        removeUser(users.get("u3"));
        removeUser(users.get("u4"));
        removeUser(users.get("u5"));
    }

    public int numberOfCommunities() {
        DFS();
        return components.size();
    }

    public List<User> mostSociableCommunity() {
        List<User> mostSociableCommunity = new ArrayList<>();
        DFS();
        for (List<User> component : components) {
            if (component.size() > mostSociableCommunity.size()) {
                mostSociableCommunity = component;
            }
        }
        return mostSociableCommunity;
    }

    private void DFSUtil(User user, HashMap<User, Boolean> visited,
                         ArrayList<User> al, List<User> allUsers) {
        visited.put(user, true);
        al.add(user);
        Map<User, LocalDateTime> allFriends = friends.getAll(user);
        if (allFriends != null) {
            for (User u : allFriends.keySet()) {
                for (User u2 : allUsers) {
                    {
                        if (Objects.equals(u2.getUsername(), u.getUsername())) {
                            if (!visited.get(u2)) {
                                DFSUtil(u2, visited, al, allUsers);
                            }
                        }
                    }
                }

            }
        }
    }

    private void DFS() {
        components.clear();
        HashMap<User, Boolean> visited = new HashMap<>();
        List<User> allUsers = users.getAll();
        for (User user : allUsers)
            visited.put(user, false);

        for (User user : allUsers) {
            ArrayList<User> al = new ArrayList<>();
            if (!visited.get(user)) {
                DFSUtil(user, visited, al, allUsers);
                components.add(al);
            }
        }
    }
}
