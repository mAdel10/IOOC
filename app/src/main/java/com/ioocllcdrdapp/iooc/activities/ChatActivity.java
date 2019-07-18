package com.ioocllcdrdapp.iooc.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Chat;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.ChatByGroupIdOperation;
import com.ioocllcdrdapp.iooc.backend.operations.SendChatOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.MessageAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

public class ChatActivity extends BaseActivity implements RequestObserver {

    @BindView(R.id.app_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.chat_send_imageButton)
    ImageButton sendMessageImageButton;
    @BindView(R.id.chat_typing_editText)
    EditText typingEditText;
    @BindView(R.id.no_results_found_textView)
    TextView noResultsFoundTextView;

    MessageAdapter messageAdapter;
    List<Chat> chats;
    private String groupId;

    private final static int REQUEST_SEND_CHAT = 1;
    private final static int REQUEST_GET_CHAT = 2;

    public ChatActivity() {
        super(R.layout.activity_chat, true);
    }

    private void initRV() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
    }


    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);
        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());

        groupId = getIntent().getStringExtra(Constants.INTENT_ID);
        String groupName = getIntent().getStringExtra(Constants.INTENT_KEY);
        toolbarTextView.setText(groupName);
        initRV();

        getChatByGroupId(groupId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void getChatByGroupId(String id) {
        ChatByGroupIdOperation operation = new ChatByGroupIdOperation(id, REQUEST_GET_CHAT, false, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void sendMessage(Chat chat) {
        SendChatOperation operation = new SendChatOperation(chat, REQUEST_SEND_CHAT, false, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleRequestFinished(Object requestId, Throwable error, Object resultObject) {
        if (error != null) {
            if (error instanceof CTHttpError) {
                int code = ((CTHttpError) error).getStatusCode();
                String errorMsg = ((CTHttpError) error).getErrorMsg();
                if (code == -1 || Utilities.isNullString(errorMsg)) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), getString(R.string.request_server_error), this);
                } else if (code != 401) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), errorMsg, this);
                }
            }
        } else if (resultObject != null) {
            switch ((int) requestId) {
                case REQUEST_GET_CHAT:
                    chats = (List<Chat>) resultObject;
                    if (!chats.isEmpty()) {
                        messageAdapter = new MessageAdapter(chats, this);
                        recyclerView.setAdapter(messageAdapter);
                    } else {
                        noResultsFoundTextView.setVisibility(View.VISIBLE);
                    }
                    break;
                case REQUEST_SEND_CHAT:
                    Utilities.restartActivity(this);
                    break;
            }
        }

        enableSendButton();
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }

    @OnClick({R.id.chat_send_imageButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chat_send_imageButton:
                if (!typingEditText.getText().toString().trim().isEmpty()) {
                    sendMessage(typingEditText.getText().toString().trim());
                }
                break;
        }
    }

    private void sendMessage(String comment) {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(c);

        typingEditText.setText("");
        noResultsFoundTextView.setVisibility(GONE);
        disableSendButton();

        String myId = UserManager.getInstance().getCurrentUser().getId();

        if (chats != null && !chats.isEmpty()) {
            messageAdapter.addItem(new Chat("-id", comment, myId, date));
        } else {
            chats = new ArrayList<>();
            chats.add(new Chat("-id", comment, myId, date));
            messageAdapter = new MessageAdapter(chats, this);
            recyclerView.setAdapter(messageAdapter);
        }

        Chat chat = new Chat(comment, date, groupId);
        sendMessage(chat);
    }

    private void enableSendButton() {
        sendMessageImageButton.setEnabled(true);
        sendMessageImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_send_message_accent));
    }

    private void disableSendButton() {
        sendMessageImageButton.setEnabled(false);
        sendMessageImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_send_loading));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if(event != null && event.equals("New Message") && this.getWindow().getDecorView().getRootView().isShown()){
            Utilities.restartActivity(this);
        }
    }
}
