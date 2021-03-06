package semaphor.swoop.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import semaphor.swoop.R;
import semaphor.swoop.database.UserModel;
import semaphor.swoop.database.UsersDatabaseHandler;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private UserModel CURRENTUSER = new UserModel();
    private UsersDatabaseHandler CURRENTDB;

    @BindView(R.id.input_email) TextInputEditText _emailText;
    @BindView(R.id.input_password) TextInputEditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) Button _signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUserDatabase();

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //CURRENTDB = new UsersDatabaseHandler(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // create user
        String username = _emailText.getText().toString();
        //CURRENTUSER = CURRENTDB.getUserByUsername(username);
        final String password = _passwordText.getText().toString();

        // TODO: Implement authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        String password = _passwordText.getText().toString();
                        if(true){
                        // CURRENTUSER != null && CURRENTUSER.getPassword().equals(password)) {
                            // On complete call either onLoginSuccess or onLoginFailed
                            onLoginSuccess();
                        }else {
                            // onLoginFailed();
                            progressDialog.dismiss();
                        }
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful sign up logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the HomeActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("CURRENT_USERID", CURRENTUSER.getID() );
        finish();
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "username&password don't match", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void initUserDatabase() {
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

//        // Reading all users
//        Log.d("Read ", "Reading users...");
//        List<UserModel> userList = users.getAllUsers();
//
//        for (UserModel user : userList) {
//            String log = "ID: " + user.getID() + ", Username: " + user.getUsername() + ", Password: " + user.getPassword()
//                    + ", Fan IDs: " + user.getStringFanIDs() + ", Follow IDs: " + user.getStringFollowIDs();
//            Log.d("User ", log);
//        }
    }
}
