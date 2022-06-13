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

    AppCompatDialog progressDialog;

    void callSignInActivity(Activity activity) {
        Intent intent = new Intent(getContext(), SignInActivity.class);
        startActivity(intent);
        activity.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }

    private void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    void showDialog(Activity activity) {
        if (progressDialog == null) return;

        if (!progressDialog.isShowing()) {
            progressDialog = new AppCompatDialog(activity, R.style.CustomDialog);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.dialog_loading);
            if (!activity.isFinishing()) progressDialog.show();
        }

    }

}
