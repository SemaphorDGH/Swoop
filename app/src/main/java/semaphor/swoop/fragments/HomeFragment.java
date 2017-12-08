package semaphor.swoop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import semaphor.swoop.R;
import semaphor.swoop.activities.HomeActivity;

import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import semaphor.swoop.database.PostModel;


public class HomeFragment extends BaseFragment {
    private List<PostModel> posts;
    private PostListAdapter adapter;
    private ListView lv;

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
        setHasOptionsMenu(true);
        posts = new ArrayList<>();
        posts.add(new PostModel("Derek","????","1;2;3"));
        posts.add(new PostModel("Hung","????","1;2;3"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

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
