package semaphor.swoop.database;

/**
 * Created by hungnguyen on 10/21/2017.
 */

public class UserModel {
    private int id;
    private String username, password, followID, fanID;

    // Empty constructor
    public UserModel() {

    }

    // Constructor with id
    public UserModel(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Constructor without id
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Constructor with full data model
    public UserModel(int id, String username, String password, String fanID, String followID) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fanID = fanID;
        this.followID = followID;
    }

    // Constructor with full data model without id
    public UserModel(String username, String password, String fanID, String followID) {
        this.username = username;
        this.password = password;
        this.fanID = fanID;
        this.followID = followID;
    }

    // Get id
    public int getID() {
        return this.id;
    }

    // Set id
    public void setID(int id) {
        this.id = id;
    }

    // Get username
    public String getUsername() {
        return this.username;
    }

    // Set username ?

    // Get password
    public String getPassword() {
        return this.password;
    }

    // Get password
    public void setPassword(String password) {
        this.password = password;
    }

    // Get fan ids
    public String getFanID() {
        return this.fanID;
    }

    // Set fan ids
    public void setFanID(String fanID) {
        this.fanID = fanID;
    }

    // Get follow ids
    public String getFollowID() {
        return this.followID;
    }

    // Set follow ids
    public void setFollowID(String followID) {
        this.followID = followID;
    }
}
