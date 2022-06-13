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

        refreshAdapter(loadPosts());

    }

    private ArrayList<Post> loadPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("https://images.unsplash.com/photo-1616588589676-62b3bd4ff6d2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1932&q=80"));
        posts.add(new Post("https://images.unsplash.com/photo-1618193139062-2c5bf4f935b7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fGdhbWluZ3xlbnwwfDF8MHx8&auto=format&fit=crop&w=800&q=60"));
        posts.add(new Post("https://images.unsplash.com/photo-1617744216376-2a5283a1671d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1160&q=80"));
        return posts;
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