package social_network.repo.file;


import social_network.domain.User;
import social_network.repo.memory.Friends;
import social_network.repo.memory.Users;
import social_network.repo.validators.RepoException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FriendsFileRepository extends AbstractFileRepository {

    public FriendsFileRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> attr = Arrays.asList(line.split(";"));
                List<String> firstUserAttr = Arrays.asList(attr.get(0), attr.get(1));
                User firstUser = extractUser(firstUserAttr);
                try {
                    users.add(firstUser);
                } catch (Exception ignored) {
                }
                for (int i = 2; i < attr.size(); i = i + 3) {
                    User friend = extractUser(Arrays.asList(attr.get(i), attr.get(i + 1)));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime friendsFrom = LocalDateTime.parse(attr.get(i + 2), formatter);
                    try {
                        users.add(friend);
                    } catch (RepoException ignored) {
                    }
                    friends.add(users.get(firstUser.getUsername()), users.get(friend.getUsername()), friendsFrom);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User extractUser(List<String> attributes) {
        return new User(attributes.get(0), attributes.get(1));
    }

    @Override
    protected String createUserAsString(User user) {
        return user.getUsername() + ";" + user.getPassword() + ";";
    }

    @Override
    protected String createFriendAsString(Map.Entry<User, LocalDateTime> friend) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return friend.getKey().getUsername() + ";" + friend.getKey().getPassword() + ";" + friend.getValue().format(formatter) + ";";
    }

    @Override
    public void writeToFile() {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName, true))) {
            for (User user : users.getAll()) {
                clearFile();
                bW.write(createUserAsString(user));
                if (friends.getAll(user) != null) {
                    for (Map.Entry<User, LocalDateTime> friend : friends.getAll(user).entrySet()) {
                        bW.write(createFriendAsString(friend));
                    }
                    bW.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Users getUsers() {
        return users;
    }

    public Friends getFriends() {
        return friends;
    }

}
