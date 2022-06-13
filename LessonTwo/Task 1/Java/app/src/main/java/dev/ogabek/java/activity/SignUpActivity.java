package dev.ogabek.java.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dev.ogabek.java.R;

/**
 * In SignUpActivity, user can signup using fullname , email,password
 */

public class SignUpActivity extends AppCompatActivity {

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
    }

    private void initViews() {
        et_fullname=findViewById(R.id.et_fullname);
        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        et_cpassword=findViewById(R.id.et_cpassword);
        tv_signin=findViewById(R.id.tv_signin);
        btn_signup=findViewById(R.id.btn_signup);

        tv_signin.setOnClickListener(view -> finish());
        btn_signup.setOnClickListener(view -> finish());
    }

}