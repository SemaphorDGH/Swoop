package semaphor.swoop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnguyen on 10/21/2017.
 */

public class UsersDatabaseHandler extends SQLiteOpenHelper {

    // All static variables
    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "USERS_DATABASE_MANAGER";

    // User table name
    private static final String TABLE_USERS = "users";

    // User table columns names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FAN_ID = "fanID";
    private static final String COLUMN_FOLLOW_ID = "followID";

    // Constructor
    public UsersDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" + COLUMN_ID + " INT PRIMARY KEY,"
                + COLUMN_USERNAME + " TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_FAN_ID + " TEXT," + COLUMN_FOLLOW_ID + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_USERS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // Create tables again
        onCreate(db);
    }

    // CRUD operations
    // Adding new user
    public void addUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, user.getID()); // id
        values.put(COLUMN_USERNAME, user.getUsername()); // username
        values.put(COLUMN_PASSWORD, user.getPassword()); // password
        values.put(COLUMN_FAN_ID, user.getStringFanIDs()); // fan ids
        values.put(COLUMN_FOLLOW_ID, user.getStringFollowIDs()); // follow ids

        // Inserting row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single user by id
    public UserModel getUserByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_FAN_ID, COLUMN_FOLLOW_ID },
                COLUMN_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        UserModel user = new UserModel(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return user;
    }

    // Getting single user by username
    public UserModel getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_FAN_ID, COLUMN_FOLLOW_ID },
                COLUMN_USERNAME + "=?", new String[] { username }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        UserModel user = new UserModel(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return user;
    }

    // Getting all users
    public List<UserModel> getAllUsers() {
        List<UserModel> userList = new ArrayList<>();
        // Select all queries
        String selectAllQueries = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllQueries, null);

        // Adding to the list all users
        if (cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return userList;
    }

    // Getting users count
    public int getUsersCount() {
        // Select all users
        String selectAllUsers = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAllUsers, null);
        cursor.close();

        return cursor.getCount();
    }

    // Updating single user
    public int updateUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername()); // username
        values.put(COLUMN_PASSWORD, user.getPassword()); // password
        values.put(COLUMN_FAN_ID, user.getStringFanIDs()); // fan ids
        values.put(COLUMN_FOLLOW_ID, user.getStringFollowIDs()); // follow ids

        // Updating row
        return db.update(TABLE_USERS, values, COLUMN_ID + "=?", new String[]{ String.valueOf(user.getID())});
    }

    // Deleting single user
    public void deleteUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_ID + "=?", new String[]{ String.valueOf(user.getID()) });
        db.close();
    }

    // Deleting all users
    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteAllQueries = "DELETE FROM " + TABLE_USERS;
        db.execSQL(deleteAllQueries);
        db.close();
    }
}
