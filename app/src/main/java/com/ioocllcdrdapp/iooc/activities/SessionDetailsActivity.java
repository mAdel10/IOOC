package com.ioocllcdrdapp.iooc.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.models.Session;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.controllers.adapters.MaterialAdapter;
import com.ioocllcdrdapp.iooc.controllers.adapters.MaterialsAdapter;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.views.AppChromeClient;
import com.ioocllcdrdapp.iooc.views.AppWebViewClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SessionDetailsActivity extends BaseActivity {


    @BindView(R.id.sessions_details_videoView)
    WebView sessionsVideoView;
    @BindView(R.id.sessions_details_live_btn)
    Button sessionsLiveBtn;
    @BindView(R.id.sessions_details_quiz_btn)
    Button sessionsQuizBtn;

    private Group group;
    private Session session;

    private RecyclerView recyclerView;
    MaterialAdapter materialAdapter;
    private List<String> material;

    public SessionDetailsActivity() {
        super(R.layout.activity_session_details, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);
        session = (Session) getIntent().getSerializableExtra(Constants.INTENT_SESSION);
        group = (Group) getIntent().getSerializableExtra(Constants.INTENT_GROUP);
        material = session.getMaterials();
        if (session.getVideoURL() != null){
            sessionsVideoView.getSettings().setJavaScriptEnabled(true);
            AppChromeClient appChromeClient = new AppChromeClient(this, this);
            sessionsVideoView.setWebViewClient(new AppWebViewClient());
            sessionsVideoView.setWebChromeClient(appChromeClient);

            sessionsVideoView.loadUrl(session.getVideoURL());
        }

        recyclerView = findViewById(R.id.sessions_details_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        materialAdapter = new MaterialAdapter(material, this);
        recyclerView.setAdapter(materialAdapter);

    }

    @OnClick({R.id.sessions_details_live_btn, R.id.sessions_details_quiz_btn})
    public void onViewClicked(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.sessions_details_live_btn:
                Uri live = Uri.parse(group.getLiveURL());
                i = new Intent(Intent.ACTION_VIEW,live);
                startActivity(i);
                break;
            case R.id.sessions_details_quiz_btn:
                Uri quiz = Uri.parse(session.getQuizURL());
                i = new Intent(Intent.ACTION_VIEW,quiz);
                startActivity(i);
                break;
        }
    }
}
