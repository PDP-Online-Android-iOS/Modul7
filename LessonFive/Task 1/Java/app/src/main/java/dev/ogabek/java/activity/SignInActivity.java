package dev.ogabek.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dev.ogabek.java.R;
import dev.ogabek.java.manager.AuthManager;
import dev.ogabek.java.manager.handler.AuthHandler;

/*
 * In SignInActivity, user can login using email, password
 */

public class SignInActivity extends BaseActivity {

    private static final String TAG = SignInActivity.class.toString();

    EditText et_email;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initViews();

    }

    private void firebaseSignIn(String email, String password) {
        showLoading(this);
        AuthManager.signIn(email, password, new AuthHandler() {
            @Override
            public void onSuccess(String uid) {
                dismissLoading();
                Toast.makeText(getApplicationContext(), getString(R.string.str_signin_success), Toast.LENGTH_SHORT).show();
                callMainActivity(getApplicationContext());
            }

            @Override
            public void onError(Exception exception) {
                dismissLoading();
                Toast.makeText(getApplicationContext(), getString(R.string.str_signin_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        Button b_signin = findViewById(R.id.b_signin);
        b_signin.setOnClickListener(view -> {
            String email = et_email.getText().toString();
            String password = et_password.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                firebaseSignIn(email, password);
            }
        });

        TextView tv_signup = findViewById(R.id.tv_signup);
        tv_signup.setOnClickListener(view -> callSignUpActivity());

    }

    private void callSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void callMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}