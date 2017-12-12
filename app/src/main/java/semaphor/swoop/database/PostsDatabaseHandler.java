package semaphor.swoop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by hungnguyen on 10/21/2017.
 */

public class PostsDatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "PostsDatabaseHandler";
    // All static variables
    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "POSTS_DATABASE_MANAGER";

    // Post table name
    private static final String TABLE_POSTS = "posts";

    // Post table columns names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_TEXT_QUESTION = "textQuestion";
    private static final String COLUMN_TEXT_ANSWER = "textAnswer";
    private static final String COLUMN_DATE = "date";

    // Constructor
    public PostsDatabaseHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_POSTS = "CREATE TABLE " + TABLE_POSTS + "(" + COLUMN_ID + " INT PRIMARY KEY," +
                COLUMN_USERNAME + " TEXT," + COLUMN_TEXT_QUESTION + " TEXT," + COLUMN_TEXT_ANSWER + " TEXT," +
                COLUMN_DATE + " TEXT," + "FOREIGN KEY" + "(" + COLUMN_USERNAME + ") " + "REFERENCES " + "posts(id)" + ")";
        db.execSQL(CREATE_TABLE_POSTS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        // Create tables again
        onCreate(db);
    }

    // CRUD operations
    // Adding new user
    public void addPost(PostModel post) {
        SQLiteDatabase db = this.getWritableDatabase();

        Random r = new Random();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, Math.abs(r.nextInt())); // id
        values.put(COLUMN_USERNAME, post.getUsername()); // username
        values.put(COLUMN_TEXT_QUESTION, post.getTextQuestion()); // text question
        values.put(COLUMN_TEXT_ANSWER, post.getStringTextAnswer()); // text answer
        values.put(COLUMN_DATE, post.getStringDate()); // date

        // Inserting row
        db.insert(TABLE_POSTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single user
    public PostModel getPostByID(int id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_POSTS, new String[] { COLUMN_ID, COLUMN_USERNAME, COLUMN_TEXT_QUESTION, COLUMN_TEXT_ANSWER, COLUMN_DATE },
                COLUMN_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        PostModel post = new PostModel(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return post;
    }

    // Getting all posts
    public List<PostModel> getAllPosts() throws ParseException {
        List<PostModel> postList = new ArrayList<>();
        // Select all queries
        String selectAllQueries = "SELECT * FROM " + TABLE_POSTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQueries, null);

        // Adding to the list all posts
        if (cursor.moveToFirst()) {
            do {
                PostModel post = new PostModel(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                postList.add(post);
            } while (cursor.moveToNext());
        }
        cursor.close();

        sortPostsByDate(postList);

        return postList;
    }
    
    public List<PostModel> getPostsByUsername(String username) throws ParseException {
        List<PostModel> postList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POSTS, new String[] { COLUMN_ID, COLUMN_USERNAME, COLUMN_TEXT_QUESTION, COLUMN_TEXT_ANSWER, COLUMN_DATE },
                COLUMN_USERNAME + "=?", new String[] { username }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                PostModel post = new PostModel(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                postList.add(post);
            } while (cursor.moveToNext());
        }
        cursor.close();

        sortPostsByDate(postList);

        return postList;
    }

    public void sortPostsByDate(List<PostModel> posts) {
        Collections.sort(posts, new Comparator<PostModel>() {
            @Override
            public int compare(PostModel p1, PostModel p2) {
                Log.d(TAG, String.valueOf(p2.getDate().compareTo(p1.getDate())));
                return p2.getDate().compareTo(p1.getDate());
            }
        });
    }

    // Getting posts count
    public int getPostsCount() {
        // Select all posts
        String selectAllPosts = "SELECT * FROM " + TABLE_POSTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllPosts, null);
        cursor.close();

        return cursor.getCount();
    }

    // Updating single user
    public int updatePost(PostModel post) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, post.getUsername()); // username
        values.put(COLUMN_TEXT_QUESTION, post.getTextQuestion()); // text question
        values.put(COLUMN_TEXT_ANSWER, post.getStringTextAnswer()); // text answer
        values.put(COLUMN_DATE, post.getStringDate()); // date

        // Updating row
        return db.update(TABLE_POSTS, values, COLUMN_ID + "=?", new String[]{ String.valueOf(post.getID())});
    }

    // Deleting single user
    public void deletePost(PostModel post) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POSTS, COLUMN_ID + "=?", new String[]{ String.valueOf(post.getID()) });
        db.close();
    }

    // Deleting all posts
    public void deleteAllPosts() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteAllQueries = "DELETE FROM " + TABLE_POSTS;
        db.execSQL(deleteAllQueries);
        db.close();
    }
}
