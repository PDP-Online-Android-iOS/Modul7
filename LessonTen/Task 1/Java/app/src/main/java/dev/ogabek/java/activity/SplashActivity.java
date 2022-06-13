package dev.ogabek.java.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.messaging.FirebaseMessaging;

import dev.ogabek.java.R;
import dev.ogabek.java.manager.AuthManager;
import dev.ogabek.java.utils.Logger;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initViews();

    }

    private void initViews() {
        countDownTimer();
        loadFCMToken();
    }

    private void countDownTimer() {
        new CountDownTimer(2000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (AuthManager.isSignedIn()) {
                    callMainActivity(getApplicationContext());
                } else {
                    callSignInActivity();
                }
            }
        }.start();
    }

    void callSignInActivity() {
        Log.d(TAG, "onCreate: " + TAG);
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener( task -> {
            if (!task.isSuccessful()) {
                Logger.e(TAG, "Fetching FCM registration token failed");
                return;
            }
            // Get new FCM registration token
            // Save it in locally to use later
            String token = task.getResult();
            Logger.d(TAG, "token :: " + token);
        });
    }

}