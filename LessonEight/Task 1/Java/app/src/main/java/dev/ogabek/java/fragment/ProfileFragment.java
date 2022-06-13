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

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.util.ArrayList;

import dev.ogabek.java.R;
import dev.ogabek.java.adapter.ProfileAdapter;
import dev.ogabek.java.manager.AuthManager;
import dev.ogabek.java.manager.DatabaseManager;
import dev.ogabek.java.manager.StorageManager;
import dev.ogabek.java.manager.handler.DBPostsHandler;
import dev.ogabek.java.manager.handler.DBUserHandler;
import dev.ogabek.java.manager.handler.DBUsersHandler;
import dev.ogabek.java.manager.handler.StorageHandler;
import dev.ogabek.java.model.Post;
import dev.ogabek.java.model.User;
import dev.ogabek.java.utils.Logger;

public class ProfileFragment extends BaseFragment {

    private static final String TAG = ProfileFragment.class.toString();
    RecyclerView rv_profile;
    ShapeableImageView iv_profile;
    TextView tv_fullName;
    TextView tv_email, tv_posts, tv_followers, tv_following;
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

        tv_fullName = view.findViewById(R.id.tv_fullname);
        tv_email = view.findViewById(R.id.tv_email);

        tv_posts = view.findViewById(R.id.tv_posts);
        tv_followers = view.findViewById(R.id.tv_followers);
        tv_following = view.findViewById(R.id.tv_following);

        iv_profile = view.findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(it -> pickFishBunPhoto());

        ImageView logOut = view.findViewById(R.id.iv_logout);

        logOut.setOnClickListener(it -> {
            AuthManager.signOut();
            callSignInActivity(requireActivity());
        });


        loadUserInfo();

        loadMyPosts();

        loadMyFollowers();
        loadMyFollowings();

        return view;
    }

    void loadUserInfo() {
        DatabaseManager.loadUser(AuthManager.currentUser().getUid(), new DBUserHandler() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    showUserInfo(user);
                }
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception exception) {
                Logger.e(TAG, exception.getMessage());
            }
        });
    }

    void showUserInfo(User user) {
        tv_fullName.setText(user.getFullname());
        tv_email.setText(user.getEmail());
        Glide.with(this).load(user.getUserImg())
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(iv_profile);
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
                    uploadUserPhoto();
                }
            });

    private void uploadUserPhoto() {
        if (pickedPhoto == null) return;
        StorageManager.uploadUserPhoto(pickedPhoto, new StorageHandler() {
            @Override
            public void onSuccess(String imageUrl) {
                DatabaseManager.updateUserImage(imageUrl);
                iv_profile.setImageURI(pickedPhoto);
            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }

    private void loadMyFollowers(){
        String uid = AuthManager.currentUser().getUid();
        DatabaseManager.loadFollowers(uid, new DBUsersHandler() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                tv_followers.setText(String.valueOf(users.size()));
            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }

    private void loadMyFollowings() {
        String uid = AuthManager.currentUser().getUid();
        DatabaseManager.loadFollowing(uid, new DBUsersHandler() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                tv_following.setText(String.valueOf(users.size()));
            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }

    private void refreshAdapter(ArrayList<Post> items) {
        ProfileAdapter adapter = new ProfileAdapter(this, items);
        rv_profile.setAdapter(adapter);
    }

    private void loadMyPosts() {
        String uid = AuthManager.currentUser().getUid();
        DatabaseManager.loadPosts(uid, new DBPostsHandler() {
            @Override
            public void onSuccess(ArrayList<Post> posts) {
                tv_posts.setText(String.valueOf(posts.size()));
                refreshAdapter(posts);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

}