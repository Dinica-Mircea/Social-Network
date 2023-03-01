package social_network.domain;

import java.util.Date;

public class Friendship {
    private String userUsername, friendUsername;
    private Date friendsFrom;

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public Date getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(Date friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    public Friendship(String userUsername, String friendUsername, Date friendsFrom) {
        this.userUsername = userUsername;
        this.friendUsername = friendUsername;
        this.friendsFrom = friendsFrom;
    }
}
