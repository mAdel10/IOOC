package com.ioocllcdrdapp.iooc.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.managers.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iooc_text_view)
    TextView splashTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        splashTextView = findViewById(R.id.iooc_text_view);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        splashTextView.startAnimation(animation);

        FirebaseMessaging.getInstance().subscribeToTopic("iooc").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("subscribeToTopic", "onComplete: subscribed");
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if (UserManager.getInstance().isUserLoggedIn()) {
                    if (UserManager.getInstance().isInstructor()){
                        i = new Intent(SplashActivity.this, InstructorMainActivity.class);
                        startActivity(i);
                    }else if (UserManager.getInstance().isStudent()){
                        i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                } else {
                    i = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, Constants.SPLASH_TIME_OUT);
    }
}