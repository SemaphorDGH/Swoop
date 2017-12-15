package semaphor.swoop.fragments.ProfileSubFragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;

import butterknife.ButterKnife;
import semaphor.swoop.R;
import semaphor.swoop.activities.HomeActivity;
import semaphor.swoop.database.PostModel;
import semaphor.swoop.database.PostsDatabaseHandler;
import semaphor.swoop.fragments.BaseFragment;
import semaphor.swoop.fragments.PostListAdapter;

/**
 * Created by derek-w on 12/12/17.
 */

public class Posts extends BaseFragment {

    private List<PostModel> mPosts;
    private ListView lv;
    private PostListAdapter adapter;
    private PostsDatabaseHandler postsDatabaseHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview = inflater.inflate(R.layout.fragment_profile_posts, container, false);
        lv = rootview.findViewById(R.id.listview_mPosts);

        try {
            mPosts = postsDatabaseHandler.getAllPosts();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new PostListAdapter(getActivity(), mPosts);
        lv.setAdapter(adapter);
        setRetainInstance(true);


        return rootview;
    }
}
