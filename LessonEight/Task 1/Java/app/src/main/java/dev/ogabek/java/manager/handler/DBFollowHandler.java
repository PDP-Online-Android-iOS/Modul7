package dev.ogabek.java.manager.handler;

public interface DBFollowHandler {

    void onSuccess(boolean isFollowed);
    void onError(Exception e);

}
