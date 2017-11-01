package semaphor.swoop.database;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import semaphor.swoop.R;

/**
 * Created by hungnguyen on 10/21/2017.
 */

public class SqliteTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        UsersDatabaseHandler dbHandler = new UsersDatabaseHandler(this);
        // Deleting all users
        Log.d("Delete ", "Deleting all ...");
        dbHandler.deleteAllUsers();
        /*
        * CRUD operations
        * */
        // Inserting users
        Log.d("Insert ", "Inserting ...");
        dbHandler.addUser(new UserModel("Hung", "123", "1", "2"));
        dbHandler.addUser(new UserModel("Derek", "456", "2", "0"));
        dbHandler.addUser(new UserModel("Giang", "789", "0", "1"));

        // Reading all users
        Log.d("Read ", "Reading ...");
        List<UserModel> users = dbHandler.getAllUsers();

        for (UserModel user : users) {
            String log = "ID: " + user.getID() + ", Username: " + user.getUsername() + ", Password: " + user.getPassword()
                    + ", Fan IDs: " + user.getFanID() + ", Follow IDs: " + user.getFollowID();
            Log.d("User ", log);
        }
    }
}
