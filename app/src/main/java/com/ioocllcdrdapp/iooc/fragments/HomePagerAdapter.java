package com.ioocllcdrdapp.iooc.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new CoursesFragment();
                return fragment;

            case 1:
                fragment = new InstructorsFragment();
                return fragment;

            case 2:
                fragment = new MOOCsFragment();
                return fragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Courses";

            case 1:
                return "Instructors";

            case 2:
                return "MOOCs";

            default:
                return null;
        }
    }
}