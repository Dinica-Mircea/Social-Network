package social_network;

import social_network.userInterface.UserInterface;


//8 CRUD operations available:
//1.add user
//2.add friend
//3.delete user
//4.delete friend
//5.change user's password
//6.change user's username
//7.delete all user's friends
//8.change date of friendship
public class Main {
    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        userInterface.run();

    }

}