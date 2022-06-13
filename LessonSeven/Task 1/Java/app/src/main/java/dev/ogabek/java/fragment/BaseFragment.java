package dev.ogabek.java.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.Fragment;

import dev.ogabek.java.R;
import dev.ogabek.java.activity.SignInActivity;

public class BaseFragment extends Fragment {

    static AppCompatDialog progressDialog;

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }

    public static void showLoading(Activity activity) {
        if (activity == null) return;

        if (progressDialog != null && progressDialog.isShowing()) {

        } else {
            progressDialog = new AppCompatDialog(activity, R.style.CustomDialog);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.dialog_loading);
            if (!activity.isFinishing()) progressDialog.show();
        }
    }

    protected void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {;
            progressDialog.dismiss();
        }
    }

    public void callSignInActivity(Activity activity) {
        Intent intent = new Intent(getContext(), SignInActivity.class);
        startActivity(intent);
        activity.finish();
    }

}
