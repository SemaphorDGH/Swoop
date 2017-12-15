package semaphor.swoop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindArray;
import butterknife.BindView;
import semaphor.swoop.R;
import semaphor.swoop.activities.HomeActivity;

import butterknife.ButterKnife;
import semaphor.swoop.fragments.ProfileSubFragment.*;
import semaphor.swoop.views.FragNavController;


public class ProfileFragment extends BaseFragment{
    private static final String TAG = "ProfileFragment";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private TabLayout tabLayout;
    private Fragment infoFragment,postsFragment,pinsFragment;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoFragment = new Info();
        postsFragment = new Posts();
        pinsFragment = new Pins();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mSectionsPageAdapter = new SectionsPageAdapter(getChildFragmentManager());
        mViewPager = view.findViewById(R.id.profile_subfragment);
        setupViewPager(mViewPager);
        tabLayout = view.findViewById(R.id.profile_tabs);
        tabLayout.setupWithViewPager(mViewPager);


        ( (HomeActivity)getActivity()).updateToolbarTitle("Profile");

        return view;
    }
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());
        adapter.addFragment(infoFragment, "tab_info");
        adapter.addFragment(postsFragment, "tab_posts");
        adapter.addFragment(pinsFragment, "tab_pins");
        viewPager.setAdapter(adapter);
    }



}
