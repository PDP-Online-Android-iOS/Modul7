package dev.ogabek.java.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.ogabek.java.R;
import dev.ogabek.java.adapter.FavoriteAdapter;
import dev.ogabek.java.manager.AuthManager;
import dev.ogabek.java.manager.DatabaseManager;
import dev.ogabek.java.manager.handler.DBPostsHandler;
import dev.ogabek.java.model.Post;

public class FavoriteFragment extends BaseFragment {

    private static String TAG = FavoriteFragment.class.toString();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        loadLikedFeeds();
    }

    private void loadLikedFeeds() {
        showLoading(requireActivity());
        String uid = AuthManager.currentUser().getUid();
        DatabaseManager.loadLikedFeeds(uid, new DBPostsHandler() {
            @Override
            public void onSuccess(ArrayList<Post> posts) {
                dismissLoading();
                refreshAdapter(posts);
            }

            @Override
            public void onError(Exception e) {
                dismissLoading();
            }
        });
    }

    public void likeOrUnlikePost(Post post) {
        String uid = AuthManager.currentUser().getUid();
        DatabaseManager.likeFeedPost(uid, post);

        loadLikedFeeds();

    }

    private void refreshAdapter(ArrayList<Post> items) {
        FavoriteAdapter adapter = new FavoriteAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    public void showDeleteDialog(Post post) {

    }

}