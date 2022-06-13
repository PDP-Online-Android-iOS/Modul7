package dev.ogabek.java.service;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import dev.ogabek.java.utils.Logger;

public class FCMService extends FirebaseMessagingService {

    private static final String TAG = FCMService.class.toString();

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Logger.i(TAG, "Refreshed token :: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Logger.i(TAG, "Message :: " + message);
    }

}
