package dev.ogabek.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import dev.ogabek.java.R;
import dev.ogabek.java.fragment.ProfileFragment;
import dev.ogabek.java.fragment.SearchFragment;
import dev.ogabek.java.model.Post;
import dev.ogabek.java.model.User;

public class SearchAdapter extends BaseAdapter {

    SearchFragment fragment;
    ArrayList<User> items;

    public SearchAdapter(SearchFragment fragment, ArrayList<User> items) {
        this.fragment = fragment;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_search, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = items.get(position);
        if (holder instanceof PostViewHolder) {
            ((PostViewHolder) holder).tv_email.setText(user.getEmail());
            ((PostViewHolder) holder).tv_fullname.setText(user.getFullname());
        }
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView iv_profile;
        TextView tv_fullname;
        TextView tv_email;
        TextView tv_follow;

        public PostViewHolder(View view) {
            super(view);

            iv_profile = view.findViewById(R.id.iv_profile);
            tv_fullname = view.findViewById(R.id.tv_fullname);
            tv_email = view.findViewById(R.id.tv_email);
            tv_follow = view.findViewById(R.id.tv_follow);

        }
    }

}
