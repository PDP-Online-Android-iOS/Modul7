package dev.ogabek.java.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.ogabek.java.R;
import dev.ogabek.java.adapter.HomeAdapter;
import dev.ogabek.java.manager.AuthManager;
import dev.ogabek.java.manager.DatabaseManager;
import dev.ogabek.java.manager.handler.DBPostHandler;
import dev.ogabek.java.manager.handler.DBPostsHandler;
import dev.ogabek.java.model.Post;
import dev.ogabek.java.utils.DialogListener;
import dev.ogabek.java.utils.Logger;
import dev.ogabek.java.utils.Utils;

public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.toString();

    RecyclerView recyclerView;
    HomeListener listener;

    static ArrayList<Post> feeds = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ImageView iv_camera = view.findViewById(R.id.iv_camera);
        iv_camera.setOnClickListener(view1 -> listener.scrollToUpload());

        loadMyFeeds();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && feeds.size() > 0) {
            Logger.d(TAG, "Home Loaded");
            loadMyFeeds();
        }
    }

    private void refreshAdapter(ArrayList<Post> items) {
        HomeAdapter adapter = new HomeAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener) {
            listener = (HomeListener) context;
        } else {
            throw new RuntimeException("$context must implement HomeListener");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    private void loadMyFeeds() {
        showLoading(requireActivity());
        String uid = AuthManager.currentUser().getUid();
        DatabaseManager.loadFeeds(uid, new DBPostsHandler() {
            @Override
            public void onSuccess(ArrayList<Post> posts) {
                dismissLoading();
                feeds.clear();
                feeds.addAll(posts);
                refreshAdapter(posts);
            }

            @Override
            public void onError(Exception e) {
                dismissLoading();
            }
        });
    }

    public static void likeOrUnlikePost(Post post) {
        String uid = AuthManager.currentUser().getUid();
        DatabaseManager.likeFeedPost(uid, post);
    }

    public void showDeleteDialog(Post post) {
        Utils.dialogDouble(requireContext(), getString(R.string.str_delete_post), isChosen -> {
            if (isChosen) {
                deletePost(post);
            }
        });
    }

    public void deletePost(Post post) {
        DatabaseManager.deletePost(post, new DBPostHandler() {
            @Override
            public void onSuccess(Post post) {
                loadMyFeeds();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    /**
     * This interface is created for communication with UploadFragment
     */
    public interface HomeListener {
        void scrollToUpload();
    }

}