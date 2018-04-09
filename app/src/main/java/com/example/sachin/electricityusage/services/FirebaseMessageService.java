package com.example.sachin.electricityusage.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.util.Log;

import com.example.sachin.electricityusage.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Dreamy on 23-04-2017.
 */

public class FirebaseMessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        /*Log.d("server", "From: " + remoteMessage.getFrom());
        Log.d("messagenoti", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        showNotification(remoteMessage.getNotification().getBody());*/

    }

    void showNotification(String message) {
        Notification n = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(message)
                .setSmallIcon(R.drawable.icon)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
}
