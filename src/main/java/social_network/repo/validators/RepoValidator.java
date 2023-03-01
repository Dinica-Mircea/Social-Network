package social_network.repo.validators;

import java.util.List;

public interface RepoValidator<T> {
    void validate(List<T> entity, T entity2) throws RepoException;
}
