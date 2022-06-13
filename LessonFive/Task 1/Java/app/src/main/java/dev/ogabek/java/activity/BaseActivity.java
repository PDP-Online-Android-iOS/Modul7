package dev.ogabek.java.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import dev.ogabek.java.R;

/*
 * Base Activity is parent for all Activities
 */

public class BaseActivity extends AppCompatActivity {

    Context context;
    AppCompatDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }

    void showLoading(Activity activity) {
        if (activity == null) return;

        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new AppCompatDialog(activity, R.style.CustomDialog);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.dialog_loading);
            if (!activity.isFinishing()) progressDialog.show();
        }
    }

    protected void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    void callMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void callSignInActivity(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        startActivity(intent);
        finish();
    }

}
