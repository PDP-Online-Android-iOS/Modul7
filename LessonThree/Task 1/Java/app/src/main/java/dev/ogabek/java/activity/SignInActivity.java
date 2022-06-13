package dev.ogabek.java.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dev.ogabek.java.R;

/*
 * In SignInActivity, user can login using email, password
 */

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = SignInActivity.class.toString();

    EditText et_email;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initViews();

    }

    private void initViews() {
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        Button b_signin = findViewById(R.id.b_signin);
        b_signin.setOnClickListener(view -> callMainActivity());

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