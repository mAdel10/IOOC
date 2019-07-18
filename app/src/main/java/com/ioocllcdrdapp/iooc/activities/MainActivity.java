package com.ioocllcdrdapp.iooc.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.fragments.ChatFragment;
import com.ioocllcdrdapp.iooc.fragments.ClassRoomFragment;
import com.ioocllcdrdapp.iooc.fragments.MainFragment;
import com.ioocllcdrdapp.iooc.fragments.MoreFragment;
import com.ioocllcdrdapp.iooc.fragments.ProfileFragment;
import com.ioocllcdrdapp.iooc.views.UIEngine;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener, View.OnClickListener, RequestObserver {

    BottomNavigationView bottomNavigationView;

    public MainActivity() {
        super(R.layout.activity_main, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        initBottomNavigation();

        toolbarTextView.setVisibility(View.VISIBLE);
        toolbarNotificationImageView.setVisibility(View.VISIBLE);
        toolbarSearchImageView.setVisibility(View.VISIBLE);

        toolbarNotificationImageView.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(i);
        });

        toolbarSearchImageView.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(i);
        });

        loadStartFragment();
    }

    private void initBottomNavigation() {
        UIEngine.initialize(MainActivity.this);
        bottomNavigationView = findViewById(R.id.navigation);
        Menu m = bottomNavigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    UIEngine.applyFontToMenuItem(subMenuItem, UIEngine.Fonts.APP_FONT_LIGHT);
                }
            }

            UIEngine.applyFontToMenuItem(mi, UIEngine.Fonts.APP_FONT_LIGHT);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
    }

    private void loadStartFragment() {
        loadFragment(new MainFragment());
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                toolbarTextView.setText(R.string.app_name);
                loadStartFragment();
                return true;
            case R.id.navigation_class_room:
                toolbarTextView.setText(R.string.class_room);
                loadFragment(new ClassRoomFragment());
                return true;
            case R.id.navigation_chat:
                toolbarTextView.setText(R.string.chat);
                loadFragment(new ChatFragment());
                return true;
            case R.id.navigation_profile:
                toolbarTextView.setText(R.string.profile);
                loadFragment(new ProfileFragment());
                return true;
            case R.id.navigation_more:
                toolbarTextView.setText(R.string.more);
                loadFragment(new MoreFragment());
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void handleRequestFinished(Object requestId, Throwable error, Object resultObject) {

    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }
}
