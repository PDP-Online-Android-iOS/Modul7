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
import dev.ogabek.java.manager.handler.DBPostsHandler;
import dev.ogabek.java.model.Post;

public class HomeFragment extends BaseFragment {

    RecyclerView recyclerView;
    HomeListener listener;

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

    private void loadMyFeeds(){
        showLoading(requireActivity());
        String uid = AuthManager.currentUser().getUid();
        DatabaseManager.loadFeeds(uid, new DBPostsHandler() {
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

    /**
     * This interface is created for communication with UploadFragment
     */
    public interface HomeListener {
        void scrollToUpload();
    }

}