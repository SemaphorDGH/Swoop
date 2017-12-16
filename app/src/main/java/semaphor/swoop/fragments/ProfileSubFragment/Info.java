package semaphor.swoop.fragments.ProfileSubFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import semaphor.swoop.R;
import semaphor.swoop.activities.HomeActivity;
import semaphor.swoop.fragments.BaseFragment;
import semaphor.swoop.fragments.PostListAdapter;


public class Info extends BaseFragment{
    private static final String TAG = "Info";

    private List<InfoModel> infoList;
    private ListView lv;
    private InfoListAdapter adapter;

    public static Info newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        Info fragment = new Info();
        fragment.setArguments(args);
        return fragment;
    }


    public Info() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoList = new ArrayList<>();
        infoList.add(new InfoModel("Name:"));
        infoList.add(new InfoModel("Birthday:"));
        infoList.add(new InfoModel("Following:"));
        infoList.add(new InfoModel("Fans:"));
        infoList.add(new InfoModel("Account:"));
        infoList.add(new InfoModel("Settings:"));
        infoList.add(new InfoModel("Privacy:"));
        infoList.add(new InfoModel("Others:"));
        infoList.add(new InfoModel("Credits:"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_profile_info, container, false);

        lv = view.findViewById(R.id.listview_infos);
       // Log.d("????", String.valueOf(lv == null));
        adapter = new InfoListAdapter(getActivity(), infoList);
        lv.setAdapter(adapter);

        setRetainInstance(true);
        return view;
    }



}
