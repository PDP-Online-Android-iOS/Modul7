package dev.ogabek.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import dev.ogabek.java.R;
import dev.ogabek.java.fragment.ProfileFragment;
import dev.ogabek.java.model.Post;
import dev.ogabek.java.utils.Utils;

public class ProfileAdapter extends BaseAdapter {

    ProfileFragment fragment;
    ArrayList<Post> items;

    public ProfileAdapter(ProfileFragment fragment, ArrayList<Post> items) {
        this.fragment = fragment;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_profile, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post post = items.get(position);
        if (holder instanceof PostViewHolder) {
            ImageView iv_post = ((PostViewHolder) holder).iv_post;
            TextView tv_caption = ((PostViewHolder) holder).tv_caption;
            tv_caption.setText(post.getCaption());
            setViewHeight(iv_post);
            Glide.with(fragment).load(post.getPostImg()).into(iv_post);
        }
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView iv_post;
        TextView tv_caption;

        public PostViewHolder(View view) {
            super(view);

            iv_post = view.findViewById(R.id.iv_post);
            tv_caption = view.findViewById(R.id.tv_caption);

        }
    }

    /**
     * Set ShapeableImageView height as screen width
     */

    private void setViewHeight(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = Utils.screenSize(fragment.requireActivity().getApplication()).getWidth() / 2;
        view.setLayoutParams(params);
    }

}
