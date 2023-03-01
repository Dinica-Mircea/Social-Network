package repo.file;

import org.junit.jupiter.api.Test;
import repo.Friends;
import repo.Users;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FriendsFileRepositoryTest {
    FriendsFileRepository friendsFileRepository = new FriendsFileRepository("TestData.txt");
    Friends friends = friendsFileRepository.getFriends();
    Users users = friendsFileRepository.getUsers();

    @Test
    void creation() {
        assertEquals(users.getAll().size(), 5);
        assertEquals(friends.getAll(users.get("Mircea")).size(), 3);
        assertEquals(friends.getAll(users.get("Andrei")).size(), 3);
        assertEquals(friends.getAll(users.get("Ianis")).size(), 2);
        assertEquals(friends.getAll(users.get("Andrei")).size(), 3);
    }

    @Test
    void writeToFile() {
        friends.add(users.get("Alex"), users.get("Mircea"));
        friendsFileRepository.writeToFile();
        friends.remove(users.get("Alex"), users.get("Mircea"));
        friendsFileRepository.writeToFile();
        //users.add(new User("Vlad", "askudh"));
        //friendsFileRepository.writeToFile();
        friends.add(users.get("Vlad"), users.get("Mircea"));
        friendsFileRepository.writeToFile();
        users.remove(users.get("Vlad"));
    }
}