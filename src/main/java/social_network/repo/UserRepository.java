package social_network.repo;


import social_network.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    void add(User user);

    void remove(User user);

    int size();

    User get(String username);

    void mockSetUp();


}

