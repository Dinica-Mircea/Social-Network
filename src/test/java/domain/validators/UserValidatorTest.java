package domain.validators;


import org.junit.jupiter.api.Test;
import social_network.domain.User;
import social_network.domain.validators.UserValidator;
import social_network.domain.validators.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    UserValidator userValidator = new UserValidator();

    @Test
    void validate() {
        User userNoUsername = new User("", "sadsad");
        User userNoPassword = new User("sadsad", "");
        User userNoNothing = new User("", "");
        ValidationException thrown1 = assertThrows(ValidationException.class, () -> userValidator.validate(userNoUsername), "unlucky");
        assertEquals(thrown1.getMessage(), "No Username!");
        ValidationException thrown2 = assertThrows(ValidationException.class, () -> userValidator.validate(userNoPassword), "unlucky");
        assertEquals(thrown2.getMessage(), "No Password!");
        ValidationException thrown3 = assertThrows(ValidationException.class, () -> userValidator.validate(userNoNothing), "unlucky");
        assertEquals(thrown3.getMessage(), "No Username!");
    }
}