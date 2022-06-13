package dev.ogabek.java.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.util.ArrayList;

import dev.ogabek.java.R;
import dev.ogabek.java.adapter.ProfileAdapter;
import dev.ogabek.java.manager.AuthManager;
import dev.ogabek.java.model.Post;

public class ProfileFragment extends BaseFragment {

    private static final String TAG = ProfileFragment.class.toString();
    RecyclerView rv_profile;
    ShapeableImageView iv_profile;
    TextView tv_fullName;
    TextView tv_email;

    Uri pickedPhoto;
    ArrayList<Uri> allPhotos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initViews(inflater.inflate(R.layout.fragment_profile, container, false));
    }

    private View initViews(View view) {

        rv_profile = view.findViewById(R.id.rv_profile);
        rv_profile.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        tv_fullName = view.findViewById(R.id.tv_fullName);
        tv_email = view.findViewById(R.id.tv_email);

        iv_profile = view.findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(it -> pickFishBunPhoto());

        ImageView logOut = view.findViewById(R.id.iv_logOut);

        logOut.setOnClickListener(it -> {
            AuthManager.signOut();
            callSignInActivity(requireActivity());
        });

        refreshAdapter(loadPosts());

        return view;
    }

    /**
     * Pick Photo using FishBun Library
     */

    private void pickFishBunPhoto() {
        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setMaxCount(1)
                .setMinCount(1)
                .setSelectedImages(allPhotos)
                .startAlbumWithActivityResultCallback(photoLauncher);
    }


    private ActivityResultLauncher photoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), it -> {
                if (it.getResultCode() == Activity.RESULT_OK) {
                    allPhotos = (it.getData().getParcelableArrayListExtra(FishBun.INTENT_PATH) != null) ? it.getData().getParcelableArrayListExtra(FishBun.INTENT_PATH) : new ArrayList<>();
                    pickedPhoto = allPhotos.get(0);
                    iv_profile.setImageURI(pickedPhoto);
                }
            });

    private void refreshAdapter(ArrayList<Post> items) {
        ProfileAdapter adapter = new ProfileAdapter(this, items);
        rv_profile.setAdapter(adapter);
    }

    private ArrayList<Post> loadPosts() {
        ArrayList<Post> items = new ArrayList<>();
        items.add(new Post("https://images.unsplash.com/photo-1616588589676-62b3bd4ff6d2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1932&q=80"));
        items.add(new Post("https://images.unsplash.com/photo-1618193139062-2c5bf4f935b7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fGdhbWluZ3xlbnwwfDF8MHx8&auto=format&fit=crop&w=800&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1617744216376-2a5283a1671d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1160&q=80"));
        items.add(new Post("https://images.unsplash.com/photo-1616588589676-62b3bd4ff6d2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1932&q=80"));
        items.add(new Post("https://images.unsplash.com/photo-1618193139062-2c5bf4f935b7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fGdhbWluZ3xlbnwwfDF8MHx8&auto=format&fit=crop&w=800&q=60"));
        items.add(new Post("https://images.unsplash.com/photo-1617744216376-2a5283a1671d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1160&q=80"));
        return items;
    }

}