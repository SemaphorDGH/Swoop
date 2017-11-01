package semaphor.swoop.database;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Random;

import semaphor.swoop.R;

/**
 * Created by hungnguyen on 10/21/2017.
 */

public class SqliteTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsersDatabaseHandler users = new UsersDatabaseHandler(this);
        // Deleting all users
        Log.d("Delete ", "Deleting all users...");
        users.deleteAllUsers();
        /*
        * CRUD operations
        * */
        // Inserting users
        Log.d("Insert ", "Inserting users ...");
        Random r = new Random();
        users.addUser(new UserModel(r.nextInt(), "Hung", "123", "1", "2"));
        users.addUser(new UserModel(r.nextInt(), "Derek", "456", "2", "0"));
        users.addUser(new UserModel(r.nextInt(), "Giang", "789", "0", "1"));

        // Reading all users
        Log.d("Read ", "Reading users...");
        List<UserModel> userList = users.getAllUsers();

        for (UserModel user : userList) {
            String log = "ID: " + user.getID() + ", Username: " + user.getUsername() + ", Password: " + user.getPassword()
                    + ", Fan IDs: " + user.getStringFanIDs() + ", Follow IDs: " + user.getStringFollowIDs();
            Log.d("User ", log);
        }

        PostsDatabaseHandler posts = new PostsDatabaseHandler(this);
        // Deleting all posts
        Log.d("Delete ", "Deleting all posts...");
        posts.deleteAllPosts();
        /*
        * CRUD operations
        * */
        // Inserting posts
        Log.d("Insert ", "Inserting posts ...");
        posts.addPost(new PostModel("Hung", "Is Hitler a good man?", "Good;Bad"));
        posts.addPost(new PostModel("Derek", "Pick between Samsung Galaxy S8 and iPhone 8", "Samsung Galaxy S8;iPhone 8"));
        posts.addPost(new PostModel("Giang", "What should I eat today?", "Fish;Beef;Pork;Crab"));

        // Reading all posts
        Log.d("Read ", "Reading posts...");
        List<PostModel> postList = posts.getPostsByUsername("Hung");

        for (PostModel post : postList) {
            String log = "ID: " + post.getID() + ", Username: " + post.getUsername() + ", Question: " + post.getTextQuestion()
                    + ", Answer: " + post.getStringTextAnswer() + ", Date: " + post.getDate();
            Log.d("Post ", log);
        }


    }
}
