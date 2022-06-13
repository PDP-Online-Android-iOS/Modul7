package dev.ogabek.java.fragment;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import dev.ogabek.java.activity.SignInActivity;

public class BaseFragment extends Fragment {

    void callSignInActivity(Activity activity) {
        Intent intent = new Intent(getContext(), SignInActivity.class);
        startActivity(intent);
        activity.finish();
    }

}
