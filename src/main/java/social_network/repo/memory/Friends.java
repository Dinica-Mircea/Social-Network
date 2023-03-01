package social_network.repo.memory;


import social_network.domain.User;
import social_network.repo.FriendsRepository;
import social_network.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Friends implements FriendsRepository {
    private final Map<User, Map<User, LocalDateTime>> usersFriends;

    public Friends(UserRepository users) {
        this.usersFriends = new HashMap<>();
        for (User user : users.getAll()) {
            Map<User, LocalDateTime> friends = new HashMap<>();
            usersFriends.put(user, friends);
        }
    }

    public Friends(Map<User, Map<User, LocalDateTime>> usersFriends) {
        this.usersFriends = usersFriends;
    }

    public void add(User user, User friend) {
        LocalDateTime friendsFrom = LocalDateTime.now();
        add(user, friend, friendsFrom);

    }

    @Override
    public void removeAllFriends(User user) {
        for (User friend : getAll(user).keySet()) {
            remove(user, friend);
        }
    }

    @Override
    public void updateDateOfFriendship(User user, User friend, LocalDateTime newFriendsFrom) {
        getAll(user).put(friend, newFriendsFrom);
    }

    @Override
    public void add(User user, User friend, LocalDateTime friendsFrom) {
        if (!usersFriends.containsKey(user)) {
            Map<User, LocalDateTime> friends = new HashMap<>();
            usersFriends.put(user, friends);
        }
        usersFriends.get(user).put(friend, friendsFrom);
        if (!usersFriends.containsKey(friend)) {
            Map<User, LocalDateTime> friendsOfFriend = new HashMap<>();
            usersFriends.put(friend, friendsOfFriend);
        }
        usersFriends.get(friend).put(user, friendsFrom);
    }

    @Override
    public void remove(User user, User friend) {
        usersFriends.get(user).remove(friend);
        usersFriends.get(friend).remove(user);
    }

    @Override
    public Map<User, LocalDateTime> getAll(User user) {
        return usersFriends.get(user);
    }


}
