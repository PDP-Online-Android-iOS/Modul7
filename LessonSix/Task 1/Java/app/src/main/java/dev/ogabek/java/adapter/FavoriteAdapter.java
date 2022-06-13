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
import dev.ogabek.java.fragment.FavouriteFragment;
import dev.ogabek.java.model.Post;

public class FavoriteAdapter extends BaseAdapter{

    FavouriteFragment fragment;
    ArrayList<Post> items;

    public FavoriteAdapter(FavouriteFragment fragment, ArrayList<Post> items) {
        this.fragment = fragment;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
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
            Glide.with(fragment).load(post.getImage()).into(((PostViewHolder) holder).iv_post);
        }
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {

         ShapeableImageView iv_profile;
         ShapeableImageView iv_post;
         TextView tv_fullname;
         TextView tv_time;
         TextView tv_caption;
         ImageView iv_more;
         ImageView iv_like;
         ImageView iv_share;

        public PostViewHolder(View view) {
            super(view);

            iv_profile = view.findViewById(R.id.iv_profile);
            iv_post = view.findViewById(R.id.iv_post);
            tv_fullname = view.findViewById(R.id.tv_fullname);
            tv_time = view.findViewById(R.id.tv_time);
            tv_caption = view.findViewById(R.id.tv_caption);
            iv_more = view.findViewById(R.id.iv_more);
            iv_like = view.findViewById(R.id.iv_like);
            iv_share = view.findViewById(R.id.iv_share);

        }
    }
}
