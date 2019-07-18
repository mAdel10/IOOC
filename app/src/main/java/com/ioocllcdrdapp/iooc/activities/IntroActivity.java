package com.ioocllcdrdapp.iooc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends AppCompatActivity {

    @BindView(R.id.intro_signIn_TextView)
    TextView signInTextView;
    @BindView(R.id.into_sign_up_button)
    Button introSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.intro_signIn_TextView, R.id.into_sign_up_button})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.intro_signIn_TextView:
                intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                break;
            case R.id.into_sign_up_button:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
