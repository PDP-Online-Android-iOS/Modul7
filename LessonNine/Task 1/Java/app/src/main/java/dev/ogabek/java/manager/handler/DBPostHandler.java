package dev.ogabek.java.manager.handler;

import dev.ogabek.java.model.Post;

public interface DBPostHandler {

    void onSuccess(Post post);
    void onError(Exception e);

}
