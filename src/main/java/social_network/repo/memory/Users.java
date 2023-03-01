package social_network.repo.memory;



import social_network.domain.User;
import social_network.domain.validators.UserValidator;
import social_network.repo.UserRepository;
import social_network.repo.validators.RepoException;
import social_network.repo.validators.UsersValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Users implements UserRepository {
    private final UserValidator userValidator;
    private final UsersValidator usersValidator;
    protected final List<User> users;

    public Users() {
        users = new ArrayList<>();
        userValidator = new UserValidator();
        usersValidator = new UsersValidator();
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void add(User user) {
        userValidator.validate(user);
        usersValidator.validate(users, user);
        users.add(user);
    }

    @Override
    public void remove(User user) {
        users.remove(user);
    }

    @Override
    public int size() {
        return users.size();
    }

    public void updatePassword(User user, String newPassword) {
        userValidator.validate(new User("test", newPassword));
        user.setPassword(newPassword);
    }

    public void updateUsername(User user, String newUsername) {
        userValidator.validate(new User(newUsername, "test"));
        usersValidator.validate(users, new User(newUsername, "test"));
        user.setUsername(newUsername);
    }

    @Override
    public User get(String username) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        throw new RepoException("Username doesn't exist!");
    }

    @Override
    public void mockSetUp() {
        User user1 = new User("u1", "p1");
        User user2 = new User("u2", "p2");
        User user3 = new User("u3", "p3");
        User user4 = new User("u4", "p4");
        User user5 = new User("u5", "p5");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (User user : users) {
            if (user != null) {
                output.append(user);
                output.append("\n");
            }
        }
        return output.toString();
    }
}
