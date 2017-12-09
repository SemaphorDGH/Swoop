package semaphor.swoop.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import semaphor.swoop.R;
import semaphor.swoop.database.PostModel;
import semaphor.swoop.database.PostsDatabaseHandler;
import semaphor.swoop.database.UserModel;
import semaphor.swoop.database.UsersDatabaseHandler;
import semaphor.swoop.fragments.BaseFragment;
import semaphor.swoop.fragments.HomeFragment;
import semaphor.swoop.fragments.NewsFragment;
import semaphor.swoop.fragments.PostFragment;
import semaphor.swoop.fragments.ProfileFragment;
import semaphor.swoop.fragments.SearchFragment;
import semaphor.swoop.utils.FragmentHistory;
import semaphor.swoop.utils.Utils;
import semaphor.swoop.views.FragNavController;

public class HomeActivity extends BaseActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {


    @BindView(R.id.content_frame)
    FrameLayout contentFrame;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int[] mTabIconsSelected = {
            R.drawable.tab_home,
            R.drawable.tab_search,
            R.drawable.tab_share,
            R.drawable.tab_news,
            R.drawable.tab_profile};


    @BindArray(R.array.tab_name)
    String[] TABS;

    @BindView(R.id.bottom_tab_layout)
    TabLayout bottomTabLayout;

    private FragNavController mNavController;

    private FragmentHistory fragmentHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int CURRENT_USERID = getIntent().getIntExtra("CURRENT_USERID", 0);

        ButterKnife.bind(this);

        initDatabase();

        initToolbar();

        initTab();

        fragmentHistory = new FragmentHistory();


        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.content_frame)
                .transactionListener(this)
                .rootFragmentListener(this, TABS.length)
                .build();


        switchTab(0);

        bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                fragmentHistory.push(tab.getPosition());

                switchTab(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                mNavController.clearStack();

                switchTab(tab.getPosition());


            }
        });

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

    }

    private void initTab() {
        if (bottomTabLayout != null) {
            for (int i = 0; i < TABS.length; i++) {
                bottomTabLayout.addTab(bottomTabLayout.newTab());
                TabLayout.Tab tab = bottomTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(getTabView(i));
            }
        }
    }


    private View getTabView(int position) {
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.tab_item_bottom, null);
        ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
        icon.setImageDrawable(Utils.setDrawableSelector(HomeActivity.this, mTabIconsSelected[position], mTabIconsSelected[position]));
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }


    private void switchTab(int position) {
        mNavController.switchTab(position);


//        updateToolbarTitle(position);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case android.R.id.home:


                onBackPressed();
                return true;
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {

            if (fragmentHistory.isEmpty()) {
                super.onBackPressed();
            } else {


                if (fragmentHistory.getStackSize() > 1) {

                    int position = fragmentHistory.popPrevious();

                    switchTab(position);

                    updateTabSelection(position);

                } else {

                    switchTab(0);

                    updateTabSelection(0);

                    fragmentHistory.emptyStack();
                }
            }

        }
    }


    private void updateTabSelection(int currentTab){

        for (int i = 0; i <  TABS.length; i++) {
            TabLayout.Tab selectedTab = bottomTabLayout.getTabAt(i);
            if(currentTab != i) {
                selectedTab.getCustomView().setSelected(false);
            }else{
                selectedTab.getCustomView().setSelected(true);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }


    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {


            updateToolbar();

        }
    }

    private void updateToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        getSupportActionBar().setDisplayShowHomeEnabled(!mNavController.isRootFragment());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }


    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {

            updateToolbar();

        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {

            case FragNavController.TAB1:
                return new HomeFragment();
            case FragNavController.TAB2:
                return new SearchFragment();
            case FragNavController.TAB3:
                return new PostFragment();
            case FragNavController.TAB4:
                return new NewsFragment();
            case FragNavController.TAB5:
                return new ProfileFragment();


        }
        throw new IllegalStateException("Need to send an index that we know");
    }


//    private void updateToolbarTitle(int position){
//
//
//        getSupportActionBar().setTitle(TABS[position]);
//
//    }


    public void updateToolbarTitle(String title) {


        getSupportActionBar().setTitle(title);

    }

    public  void initDatabase() {
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
        posts.addPost(new PostModel("Derek", "Pick between Samsung S8, iPhone 8, and Xiao", "Samsung S8;iPhone 8"));
        posts.addPost(new PostModel("Giang", "What should I eat today?", "Fish;Beef;Pork"));

//        // Reading all posts
//        Log.d("Read ", "Reading posts...");
//        List<PostModel> postList = posts.getPostsByUsername("Hung");
//
//        for (PostModel post : postList) {
//            String log = "ID: " + post.getID() + ", Username: " + post.getUsername() + ", Question: " + post.getTextQuestion()
//                    + ", Answer: " + post.getStringTextAnswer() + ", Date: " + post.getDate();
//            Log.d("Post ", log);
//        }
    }
}
