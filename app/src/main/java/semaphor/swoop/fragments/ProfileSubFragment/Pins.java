package semaphor.swoop.fragments.ProfileSubFragment;

import android.os.Bundle;
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

public class Pins extends BaseFragment {

    private List<PostModel> mPins;
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

        View rootview = inflater.inflate(R.layout.fragment_profile_pins, container, false);
        lv = rootview.findViewById(R.id.listview_mpins);

        try {
            mPins = postsDatabaseHandler.getAllPosts();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter = new PostListAdapter(getActivity(), mPins);
        lv.setAdapter(adapter);
        setRetainInstance(true);


        return rootview;
    }
}
