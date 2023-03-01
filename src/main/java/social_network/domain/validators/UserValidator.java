package social_network.domain.validators;


import social_network.domain.User;

import java.util.Objects;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User user) throws ValidationException {

        if (Objects.equals(user.getUsername(), "")) {
            throw new ValidationException("No Username!");
        }
        if (Objects.equals(user.getPassword(), "")) {
            throw new ValidationException("No Password!");
        }
    }
}
