package com.ioocllcdrdapp.iooc.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;

public class MainFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    private FragmentManager fm;
    private HomePagerAdapter pagerAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        context = getActivity().getApplicationContext();
        Context mContext = getActivity();
        fm = ((FragmentActivity) mContext).getSupportFragmentManager();

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        initViews();

        return view;
    }

    private void initViews() {
        pagerAdapter = new HomePagerAdapter(fm);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
