package dev.ogabek.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import dev.ogabek.java.R;
import dev.ogabek.java.fragment.HomeFragment;
import dev.ogabek.java.manager.AuthManager;
import dev.ogabek.java.model.Post;

public class HomeAdapter extends BaseAdapter {

    HomeFragment fragment;
    ArrayList<Post> items;

    public HomeAdapter(HomeFragment fragment, ArrayList<Post> items) {
        this.fragment = fragment;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_home, parent, false);
        return new PostViewHolder(view);
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView iv_profile;
        ShapeableImageView iv_post;
        TextView tv_fullName;
        TextView tv_time;
        TextView tv_caption;
        ImageView iv_more;
        ImageView iv_like;
        ImageView iv_share;

        public PostViewHolder(View view) {
            super(view);

            iv_profile = view.findViewById(R.id.iv_profile);
            iv_post = view.findViewById(R.id.iv_post);
            tv_fullName = view.findViewById(R.id.tv_fullname);
            tv_time = view.findViewById(R.id.tv_time);
            tv_caption = view.findViewById(R.id.tv_caption);
            iv_more = view.findViewById(R.id.iv_more);
            iv_like = view.findViewById(R.id.iv_like);
            iv_share = view.findViewById(R.id.iv_share);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post post = items.get(position);

        if (holder instanceof PostViewHolder) {
            ImageView iv_post = ((PostViewHolder) holder).iv_post;
            TextView tv_fullname = ((PostViewHolder) holder).tv_fullName;
            ImageView iv_profile = ((PostViewHolder) holder).iv_profile;
            TextView tv_caption = ((PostViewHolder) holder).tv_caption;
            TextView tv_time = ((PostViewHolder) holder).tv_time;

            tv_fullname.setText(post.getFullname());
            tv_caption.setText(post.getCaption());
            tv_time.setText(post.getCurrentDate());
            Glide.with(fragment)
                    .load(post.getUserImg())
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(iv_profile);
//            Toast.makeText(fragment.getContext(), post.getPostImg(), Toast.LENGTH_SHORT).show();
            Glide.with(fragment).load(post.getPostImg()).into(iv_post);

            ((PostViewHolder) holder).iv_like.setOnClickListener(view -> {
                if (post.isLiked()) {
                    post.setLiked(false);
                    ((PostViewHolder) holder).iv_like.setImageResource(R.drawable.ic_favorite);
                } else {
                    post.setLiked(true);
                    ((PostViewHolder) holder).iv_like.setImageResource(R.drawable.ic_favorited);
                }
                fragment.likeOrUnlikePost(post);
            });

            if (post.isLiked()) {
                ((PostViewHolder) holder).iv_like.setImageResource(R.drawable.ic_favorited);
            } else {
                ((PostViewHolder) holder).iv_like.setImageResource(R.drawable.ic_favorite);
            }

            String uid = AuthManager.currentUser().getUid();
            if (uid.equals(post.getUid())) {
                ((PostViewHolder) holder).iv_more.setVisibility(View.VISIBLE);
            } else {
                ((PostViewHolder) holder).iv_more.setVisibility(View.GONE);
            }

            ((PostViewHolder) holder).iv_more.setOnClickListener(view -> {
                fragment.showDeleteDialog(post);
            });

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
