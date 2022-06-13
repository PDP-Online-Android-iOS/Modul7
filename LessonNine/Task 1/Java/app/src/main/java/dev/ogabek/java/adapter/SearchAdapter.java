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
import dev.ogabek.java.fragment.SearchFragment;
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
            ((PostViewHolder) holder).tv_fullname.setText(user.getFullname());
            ((PostViewHolder) holder).tv_email.setText(user.getEmail());

            Glide.with(fragment).load(user.getUserImg())
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(((PostViewHolder) holder).iv_profile);

            TextView tv_follow = ((PostViewHolder) holder).tv_follow;

            tv_follow.setOnClickListener(view -> {
                if (!user.isFollowed()) {
                    tv_follow.setText(fragment.getString(R.string.str_following));
                } else {
                    tv_follow.setText(fragment.getString(R.string.str_follow));
                }
                fragment.onFollowOrUnfollow(user);
            });

            if (user.isFollowed()) {
                tv_follow.setText(fragment.getString(R.string.str_following));
            } else {
                tv_follow.setText(fragment.getString(R.string.str_follow));
            }

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
