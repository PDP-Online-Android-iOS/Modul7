package dev.ogabek.java.manager.handler;

import java.util.ArrayList;

import dev.ogabek.java.model.Post;

public interface DBPostsHandler {

    void onSuccess(ArrayList<Post> posts);
    void onError(Exception e);

}
