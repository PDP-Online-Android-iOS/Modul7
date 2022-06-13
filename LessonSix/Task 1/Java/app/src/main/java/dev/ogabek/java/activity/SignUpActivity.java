package dev.ogabek.java.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dev.ogabek.java.R;
import dev.ogabek.java.manager.AuthManager;
import dev.ogabek.java.manager.DatabaseManager;
import dev.ogabek.java.manager.handler.AuthHandler;
import dev.ogabek.java.manager.handler.DBUserHandler;
import dev.ogabek.java.model.User;
import dev.ogabek.java.utils.Logger;

/**
 * In SignUpActivity, user can signup using fullname , email,password
 */

public class SignUpActivity extends BaseActivity {

    private static final String TAG = SignUpActivity.class.toString();
     EditText et_email;
     EditText et_password;
     EditText et_cpassword;
     EditText et_fullname;
     TextView tv_signin;
     Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();

    }

    private void initViews() {
        et_fullname=findViewById(R.id.et_fullname);
        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        et_cpassword=findViewById(R.id.et_cpassword);
        tv_signin=findViewById(R.id.tv_signin);
        btn_signup=findViewById(R.id.btn_signup);

        tv_signin.setOnClickListener(view -> finish());
        btn_signup.setOnClickListener(view -> {
            String fullName = et_fullname.getText().toString().trim();
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            String cPassword = et_cpassword.getText().toString().trim();

            if (!fullName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !cPassword.isEmpty() && password.equals(cPassword)) {
                User user = new User(fullName, email, password, "");
                firebaseSignUp(user);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.str_empty_info), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void firebaseSignUp(User user) {
        showLoading(this);
        AuthManager.signUp(user.getEmail(), user.getPassword(), new AuthHandler() {
            @Override
            public void onSuccess(String uid) {
                user.setUid(uid);
                storeUserToDB(user);
                Toast.makeText(getApplicationContext(), getString(R.string.str_signup_success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception exception) {
                dismissLoading();
                Toast.makeText(getApplicationContext(), getString(R.string.str_signup_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeUserToDB(User user) {
        DatabaseManager.storeUser(user, new DBUserHandler() {
            @Override
            public void onSuccess(User user) {
                dismissLoading();
                callMainActivity(getApplicationContext());
            }

            @Override
            public void onSuccess() {
                dismissLoading();
                callMainActivity(getApplicationContext());
            }

            @Override
            public void onError(Exception exception) {
                Logger.e(TAG, exception.getMessage());
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}