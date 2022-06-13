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

public class FavouriteFragment extends BaseFragment {

    private static String TAG = FavouriteFragment.class.toString();
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
        items.add(new Post("https://images.unsplash.com/photo-1523345863760-5b7f3472d14f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80"));
        items.add(new Post("https://images.unsplash.com/photo-1624862300786-fcdbd20ba0c3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=886&q=80"));
        items.add(new Post("https://images.unsplash.com/photo-1520186994231-6ea0019d8d51?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=543&q=80"));
        return items;
    }

}