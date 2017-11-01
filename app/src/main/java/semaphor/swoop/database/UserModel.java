package semaphor.swoop.database;

/**
 * Created by hungnguyen on 10/21/2017.
 */

public class UserModel {
    private int id;
    private String username, password;
    private String[] followIDs, fanIDs;

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
    public UserModel(int id, String username, String password, String fanIDs, String followIDs) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fanIDs = fanIDs.split(";");
        this.followIDs = followIDs.split(";");
    }

    // Constructor with full data model without id
    public UserModel(String username, String password, String fanIDs, String followIDs) {
        this.username = username;
        this.password = password;
        this.fanIDs = fanIDs.split(";");
        this.followIDs = followIDs.split(";");
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
    public String[] getArrayFanIDs() {
        return this.fanIDs;
    }

    public String getStringFanIDs() {
        String fanIDs = "";
        int n = this.fanIDs.length;
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                fanIDs += this.fanIDs[i];
            } else {
                fanIDs += this.fanIDs[i] + ";";
            }
        }
        return fanIDs;
    }

    // Set fan ids
    public void setFanIDs(String fanIDs) {
        this.fanIDs = fanIDs.split(";");
    }

    // Get follow ids
    public String[] getArrayFollowIDs() {
        return this.followIDs;
    }

    public String getStringFollowIDs() {
        String followIDs = "";
        int n = this.fanIDs.length;
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                followIDs += this.followIDs[i];
            } else {
                followIDs += this.followIDs[i] + ";";
            }
        }
        return followIDs;
    }

    // Set follow ids
    public void setFollowIDs(String followIDs) {
        this.followIDs = followIDs.split(";");
    }
}
