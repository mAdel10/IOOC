package com.ioocllcdrdapp.iooc.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.activities.EditProfileActivity;
import com.ioocllcdrdapp.iooc.activities.IntroActivity;
import com.ioocllcdrdapp.iooc.backend.models.User;
import com.ioocllcdrdapp.iooc.managers.TokenManager;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.squareup.picasso.Picasso;

public class MoreFragment extends Fragment implements View.OnClickListener {

    private ImageView profileImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView logoutTextView;
    private Button editProfileBtn;
    private Context context;
    private User user;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        context = getActivity().getApplicationContext();

        init(view);
        if (UserManager.getInstance().isUserLoggedIn()) {
            user = UserManager.getInstance().getCurrentUser();
            fillData(user);
            Picasso.with(getActivity().getApplicationContext())
                    .load(user.getImage())
                    .placeholder(R.drawable.img_vector_placeholder)
                    .into(profileImageView);

            emailTextView.setText(user.getEmail());
            phoneTextView.setText(user.getPhone());


        }

        return view;
    }

    private void init(View view) {
        profileImageView = view.findViewById(R.id.more_profile_imageView);
        nameTextView = view.findViewById(R.id.more_profile_name_textView);
        emailTextView = view.findViewById(R.id.more_profile_email_textView);
        phoneTextView = view.findViewById(R.id.more_profile_phone_textView);

        editProfileBtn = view.findViewById(R.id.more_edit_profile_button);
        editProfileBtn.setOnClickListener(this);

        logoutTextView = view.findViewById(R.id.more_logout_textView);
        logoutTextView.setOnClickListener(this);
    }


    private void fillData(User user) {

        String name = user.getFirstName() + " " + user.getLastName();
        nameTextView.setText(name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_logout_textView:
                UserManager.getInstance().logout();
                TokenManager.getInstance().delete();

                Intent i = new Intent(context, IntroActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                getActivity().finish();
                break;
            case R.id.more_edit_profile_button:
                Intent intent = new Intent(getActivity() , EditProfileActivity.class);
                startActivity(intent);

        }
    }
}
