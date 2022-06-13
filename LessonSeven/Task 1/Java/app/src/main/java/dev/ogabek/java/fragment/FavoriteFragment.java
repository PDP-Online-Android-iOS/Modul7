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
        refreshAdapter(loadPosts());
    }

    private void refreshAdapter(ArrayList<Post> items) {
        FavoriteAdapter adapter = new FavoriteAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Post> loadPosts() {
        ArrayList<Post> items = new ArrayList<>();
        items.add(new Post("Here we go","https://images.unsplash.com/photo-1557053815-9f79f70c7980?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1052&q=80"));
        items.add(new Post("Here we go","https://images.unsplash.com/photo-1529156069898-49953e39b3ac?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1932&q=80"));
        items.add(new Post("Here we go","https://images.unsplash.com/photo-1517841905240-472988babdf9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"));
        return items;
    }

}