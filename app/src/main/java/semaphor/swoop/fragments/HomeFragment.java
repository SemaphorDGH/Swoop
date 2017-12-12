package semaphor.swoop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import semaphor.swoop.R;
import semaphor.swoop.activities.HomeActivity;
import semaphor.swoop.database.PostModel;
import semaphor.swoop.database.PostsDatabaseHandler;


public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    private List<PostModel> posts;
    private PostListAdapter adapter;
    private ListView lv;
    private PostsDatabaseHandler postsDatabaseHandler;

    int fragCount;

    public static HomeFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    public void generateListContent(int CURRENT_USERID){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // register for Android-Bootstrap by Bearded-Hen
        TypefaceProvider.registerDefaultIconSets();
        setHasOptionsMenu(true);

        postsDatabaseHandler = new PostsDatabaseHandler(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        Log.d(TAG, "onCreateView is called");
        // Reading all posts
        Log.d("Read ", "Reading posts...");
        List<PostModel> postList = null;
        try {
            postList = postsDatabaseHandler.getAllPosts();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        posts = new ArrayList<>();

        for (PostModel post : postList) {
            String log = "ID: " + post.getID() + ", Username: " + post.getUsername() + ", Question: " + post.getTextQuestion()
                    + ", Answer: " + post.getStringTextAnswer() + ", Date: " + post.getDate();
            Log.d("Post ", log);
            posts.add(post);
        }

        adapter = new PostListAdapter(getActivity(), posts);
        lv = rootView.findViewById(R.id.listview);
        lv.setAdapter(adapter);
        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
            }
        });


        ( (HomeActivity)getActivity()).updateToolbarTitle((fragCount == 0) ? "Home" : "Sub Home "+fragCount);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
