package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ioocllcdrdapp.iooc.fragments.CourseByInstructorIdFragment;
import com.ioocllcdrdapp.iooc.fragments.GroupByInstructorIdFragment;

public class InstructorPageAdapter  extends FragmentPagerAdapter {
    public InstructorPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new CourseByInstructorIdFragment();
                return fragment;

            case 1:
                fragment = new GroupByInstructorIdFragment();
                return fragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Courses";

            case 1:
                return "Groups";

            default:
                return null;
        }
    }
}
