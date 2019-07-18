package com.ioocllcdrdapp.iooc.notifications;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ioocllcdrdapp.iooc.R;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutionException;

public class AppMessagingService extends FirebaseMessagingService {

    private static final String TAG = AppMessagingService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.v(TAG, "MessageReceived");
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String notificationBody;
        String notificationTitle;

        Log.v(TAG, remoteMessage.getData().get("type"));
        Log.v(TAG, remoteMessage.getData().get("id"));

        try {
            boolean isAppRunning = new RunningTaskManager().execute(this).get();
            if (isAppRunning) {
                Log.v(TAG, "Foreground");
                EventBus.getDefault().post("New Message");
                return;
            } else {
                Log.v(TAG, "Background");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (notification != null) {
            try {
                if (notification.getTitle() != null) {
                    notificationTitle = notification.getTitle();
                } else {
                    notificationTitle = getString(R.string.app_name);
                }
                notificationBody = notification.getBody();

                AppNotificationBuilder.pushNotification(
                        notificationTitle,
                        notificationBody,
                        getApplicationContext());

            } catch (NullPointerException exception) {
                Log.e(TAG, exception.getMessage());
            }
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e(TAG, "NEW_TOKEN " + s);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    }
}
