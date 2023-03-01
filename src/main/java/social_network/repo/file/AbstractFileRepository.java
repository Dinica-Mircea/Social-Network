package social_network.repo.file;

import social_network.domain.User;
import social_network.repo.memory.Friends;
import social_network.repo.memory.Users;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public abstract class AbstractFileRepository {
    String fileName;
    Users users;
    Friends friends;

    public AbstractFileRepository(String fileName) {
        this.fileName = fileName;
        users = new Users();
        friends = new Friends(users);
        loadData();
    }

    protected abstract void loadData();

    public abstract User extractUser(List<String> attributes);

    protected abstract String createUserAsString(User user);

    protected abstract String createFriendAsString(Map.Entry<User, LocalDateTime> friend);

    protected void clearFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
    }

    public abstract void writeToFile();


}

