package social_network.repo.validators;



import social_network.domain.User;

import java.util.List;
import java.util.Objects;

public class UsersValidator implements RepoValidator<User> {

    @Override
    public void validate(List<User> users, User user) throws RepoException {
        for (User _user : users) {
            if (Objects.equals(_user.getUsername(), user.getUsername())) {
                throw new RepoException("Username already exists!");
            }
        }
    }
}
